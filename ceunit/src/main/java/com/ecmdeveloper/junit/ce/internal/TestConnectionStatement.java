/**
 * 
 */
package com.ecmdeveloper.junit.ce.internal;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import com.ecmdeveloper.ceunit.TestConnection;
import com.ecmdeveloper.ceunit.annotations.Credentials;
import com.ecmdeveloper.ceunit.annotations.TestFolder;
import com.ecmdeveloper.ceunit.annotations.TestObjectStore;
import com.filenet.api.core.Folder;
import com.filenet.api.core.ObjectStore;

/**
 * @author ricardo.belfor
 *
 */
public class TestConnectionStatement extends Statement {

	private final Statement base;
	private final Description description;
	private TestConnection testConnection;
	
	public TestConnectionStatement(Statement base, Description description, TestConnection testConnection ) {
		this.base = base;
		this.description = description;
		this.testConnection = testConnection;
	}


	/* (non-Javadoc)
	 * @see org.junit.runners.model.Statement#evaluate()
	 */
	@Override
	public void evaluate() throws Throwable {
		
		boolean connected = false;
		
		try {
			connected = connect();
			System.out.println( "Connected user '" + testConnection.getUsername() + "' for test " + getDescription() );
			injectObjectStore();
			injectFolders();
			base.evaluate();
		} finally {
			if ( connected ) {
				System.out.println( "Disconnecting user '" + testConnection.getUsername() + "' for test " + getDescription() );
				disconnect();
			}
		}
	}


	private void injectObjectStore() throws IllegalArgumentException, IllegalAccessException {
		Field field = getObjectStoreField();

		if ( field != null) {
			TestObjectStore testObjectStore = field.getAnnotation(TestObjectStore.class);
			field.setAccessible(true);
			field.set(testConnection.getTarget(), testConnection.getObjectStore(testObjectStore.name() ) );
		}
	}

	private void injectFolders() throws Exception {
		for ( Field folderField : getFolderFields() ) {
			injectFolder(folderField);
		}
	}

	private void injectFolder(Field folderField) throws Exception {
		TestFolder testFolder = folderField.getAnnotation(TestFolder.class);
		TestFolderPathCreator pathCreator = new TestFolderPathCreator(testFolder, description, folderField.getName() );
		Folder folder = getTestFolder(testFolder.objectStoreName(), pathCreator.getFolderPath() );

		folderField.setAccessible(true);
		folderField.set( testConnection.getTarget(), folder );
	}




	private Folder getTestFolder(String objectStoreName, String path) {
		ObjectStore objectStore = testConnection.getObjectStore( objectStoreName );
		TestFolderCreator testFolderCreator = new TestFolderCreator(objectStore);
		Folder folder = testFolderCreator.create( path );
		return folder;
	}


	private Field getObjectStoreField() {
		Field objectStoreField = null;
		for ( Field field : description.getTestClass().getDeclaredFields() ) {
			boolean staticField = Modifier.isStatic( field.getModifiers() );
			if ( description.isTest() ^ staticField ) {
				if ( field.isAnnotationPresent(TestObjectStore.class) ) {
					checkFieldType(field);
					if ( objectStoreField == null ) {
						objectStoreField = field;
					} else {
						throw new IllegalStateException("There are more than one " + (staticField ? "" : "non-") + "static fields annotated as an object stores" );
					}
				}
			}
		}
		return objectStoreField;
	}

	private Set<Field> getFolderFields() {
		Set<Field> folderFields = new HashSet<Field>();
		for ( Field field : description.getTestClass().getDeclaredFields() ) {
			boolean staticField = Modifier.isStatic( field.getModifiers() );
			if ( description.isTest() ^ staticField ) {
				if ( field.isAnnotationPresent(TestFolder.class) ) {
					folderFields.add(field);
				}
			}
		}
		return folderFields;
	}
	

	private void checkFieldType(Field field) {
		if ( !isObjectStoreField(field) ) {
			throw new IllegalStateException("The field '" + field.getName() + "' is annotated as an object store but is not an object store" ); 
		}
	}


	private boolean isObjectStoreField(Field field) {
		return field.getType().isAssignableFrom( ObjectStore.class );
	}


	private void disconnect() {
		testConnection.popSubject();
	}

	private boolean connect() {
		String username = null;
		String password = null;

		Credentials credentials = description.getAnnotation(Credentials.class);
		
		if ( credentials != null ) {
			// TODO: add role support
			username = credentials.username();
			password = credentials.password();
		} 
		
		testConnection.createConnection(username, password);
		testConnection.pushSubject(username);
		
		return true;
	}


	private String getDescription() {
		return description.getMethodName() == null ? description.getClassName() : description.getMethodName();
	}
}
