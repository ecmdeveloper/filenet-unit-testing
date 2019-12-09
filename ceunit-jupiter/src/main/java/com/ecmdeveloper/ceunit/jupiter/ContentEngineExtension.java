package com.ecmdeveloper.ceunit.jupiter;

import java.util.Optional;
import java.util.function.Function;

import javax.security.auth.Subject;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.util.ReflectionUtils;

import com.ecmdeveloper.ceunit.jupiter.annotations.ContentEngineContext;
import com.ecmdeveloper.ceunit.jupiter.annotations.TestFolder;
import com.ecmdeveloper.ceunit.jupiter.internal.EmptyFolderConfiguration;
import com.ecmdeveloper.ceunit.jupiter.internal.TestFolderCreator;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.EntireNetwork;
import com.filenet.api.core.Factory;
import com.filenet.api.core.Folder;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.UserContext;

public class ContentEngineExtension
		implements ParameterResolver, BeforeAllCallback, BeforeTestExecutionCallback, AfterTestExecutionCallback {

	@Override
	public void beforeAll(ExtensionContext extensionContext) throws Exception {
		Optional<Class<?>> testClass = extensionContext.getTestClass();
		if (testClass.isPresent()) {
			ContentEngineContext contentEngineContext = testClass.get().getAnnotation(ContentEngineContext.class);
			if (contentEngineContext.connection() != null) {
				ContentEngineConfiguration configuration = ReflectionUtils
						.newInstance(contentEngineContext.connection());
				getStore(extensionContext).put("configuration", configuration);
				Connection connection = createConnection(configuration);
				getStore(extensionContext).put("connection", connection);
			}
		}
	}

	@Override
	public void beforeTestExecution(ExtensionContext context) throws Exception {
		ContentEngineConfiguration configuration = getStore(context.getParent().get()).get("configuration",
				ContentEngineConfiguration.class);
		Connection connection = createConnection(configuration);
		getStore(context).put("connection", connection);
	}

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		Class<?> type = parameterContext.getParameter().getType();
		return type == ObjectStore.class || type == Folder.class;
	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext methodContext)
			throws ParameterResolutionException {

		ObjectStore objectStore = getStore(methodContext).get("objectStore", ObjectStore.class);
		if (objectStore == null) {
			objectStore = createObjectStore(methodContext);
			getStore(methodContext).put("objectStore", objectStore);
		}

		Class<?> type = parameterContext.getParameter().getType();
		if (type == ObjectStore.class) {
			return objectStore;
		} else if (type == Folder.class) {
			return createFolder(parameterContext, objectStore);
		}
		return null;
	}

	private Folder createFolder(ParameterContext parameterContext, ObjectStore objectStore)
			throws ParameterResolutionException {
		Optional<TestFolder> optional = parameterContext.findAnnotation(TestFolder.class);

		TestFolder testFolder = optional
				.orElseThrow(() -> new ParameterResolutionException("TestFolder annotation not found"));

		Optional<TestFolderConfiguration> configuration = resolveFolderConfiguration(testFolder);

		String parentPath = resolveTestFolderValue("parent path", testFolder, configuration,
				TestFolderConfiguration::getParentPath, TestFolder::parentPath);
	
		String name = resolveTestFolderValue("name", testFolder, configuration,
				TestFolderConfiguration::getName, TestFolder::name);

		String className = resolveTestFolderValue("class name", testFolder, configuration,
				TestFolderConfiguration::getClassName, TestFolder::className);
	
		TestFolderCreator testFolderCreator = new TestFolderCreator(objectStore);
		return testFolderCreator.create(className, parentPath + "/" + name);
	}

	private Optional<TestFolderConfiguration> resolveFolderConfiguration(TestFolder testFolder) {
		Optional<TestFolderConfiguration> configuration;
		if (!testFolder.configuration().equals(EmptyFolderConfiguration.class)) {
			configuration = Optional.of(ReflectionUtils.newInstance(testFolder.configuration()));
		} else {
			configuration = Optional.empty();
		}
		return configuration;
	}


	/**
	 * Resolves the test folder configuration value. If it is provide by the configuration class then
	 * that value is used. Otherwise, the value provided by the
	 * <code>TestFolder</code> annotation is used. If the value is still empty then
	 * an exception is thrown.
	 * 
	 * @param name the name of the value
	 * @param testFolder    the TestFolder annotation
	 * @param configuration the optional TestFolderConfiguration
	 * @param configuredValue function providing the value from the configuration class.
	 * @param annotationValue function providing the value from the annotation.
	 * @return the value
	 */
	private String resolveTestFolderValue(String name, TestFolder testFolder, Optional<TestFolderConfiguration> configuration,
			Function<TestFolderConfiguration, Optional<String>> configuredValue, Function<TestFolder, String> annotationValue ) {
		String value = "";
		if (configuration.isPresent()) {
			Optional<String> nameOptional = configuredValue.apply(configuration.get() );
			if (nameOptional.isPresent()) {
				value = nameOptional.get();
			}
		}

		if (value.isEmpty()) {
			value = annotationValue.apply(testFolder);
		}

		if (value.isEmpty()) {
			throw new ParameterResolutionException("The '" + name + "' value cannot be resolved");
		}
		return value;
	}

	private ObjectStore createObjectStore(ExtensionContext extensionContext) {
		
		ContentEngineConfiguration configuration = getStore(extensionContext).get("configuration", ContentEngineConfiguration.class);
		if ( configuration == null) {
			configuration = getStore(extensionContext.getParent().get()).get("configuration",
					ContentEngineConfiguration.class);
		}
		Connection connection = getStore(extensionContext).get("connection", Connection.class);

		EntireNetwork entireNetwork = Factory.EntireNetwork.fetchInstance(connection, null);
		Domain domain = entireNetwork.get_LocalDomain();
		return Factory.ObjectStore.fetchInstance(domain, configuration.objectStoreName(), null);
	}

	@Override
	public void afterTestExecution(ExtensionContext context) throws Exception {
		// TODO pop subject

	}

	private Connection createConnection(ContentEngineConfiguration connection) {

		Connection con = Factory.Connection.getConnection(connection.url());

		Subject subject = UserContext.createSubject(con, connection.username(), connection.password(), null);
		UserContext uc = UserContext.get();
		uc.pushSubject(subject);

		return con;
	}

	private Store getStore(ExtensionContext context) {
		return context.getStore(Namespace.create(getClass(), context));
	}
}
