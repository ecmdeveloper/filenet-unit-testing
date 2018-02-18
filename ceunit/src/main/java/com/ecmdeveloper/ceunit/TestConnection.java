/**
 * 
 */
package com.ecmdeveloper.ceunit;

import java.util.HashMap;
import java.util.Map;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import com.ecmdeveloper.junit.ce.internal.TestConnectionStatement;
import com.ecmdeveloper.junit.ce.internal.TestContext;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.EntireNetwork;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.UserContext;

/**
 * @author ricardo.belfor
 *
 */
public class TestConnection implements TestRule {

	private final String url;
	private final Object testClass;
	private final String defaultUsername;
	private final String defaultPassword;

	private TestContext currentTestContext;
	private Map<String, TestContext> usernameToTestContext = new HashMap<String, TestContext>();
	private Domain domain;
	
	public TestConnection(Object testClass, String username, String password, String url) {
		this.testClass = testClass;
		this.defaultUsername = username;
		this.defaultPassword = password;
		this.url = url;
	}

	public TestConnection(Object testClass, String url) {
		this(testClass, null, null, url );
	}
	
	@Override
	public Statement apply(Statement statement, Description description) {
		return new TestConnectionStatement(statement, description, this);
	}

	public Object getTarget() {
		return testClass;
	}
	
	public void createConnection(String username, String password) {
		
		username = getSafeUsername(username);
		password = getSafePassword(password);

		if ( !usernameToTestContext.containsKey(username) ) {
			TestContext testContext = new TestContext(username, password, url );
			usernameToTestContext.put(username, testContext);
		}
	}

	private String getSafeUsername(String username) {
		if ( username == null || username.isEmpty() ) {
			username = defaultUsername;
		}
		
		if ( username == null ) {
			throw new IllegalStateException( "No username is specified in any context" );
		}
		
		return username;
	}

	private String getSafePassword(String password) {
		if ( password == null || password.isEmpty() ) {
			password = defaultPassword;
		}

		if ( password == null ) {
			throw new IllegalStateException( "No password is specified in any context" );
		}
		
		return password;
	}

	public void pushSubject(String username) {
		username = getSafeUsername(username);
		currentTestContext = usernameToTestContext.get(username);
		if ( currentTestContext == null ) {
			throw new IllegalArgumentException("No test context for user '" + username + "'" );
		}

		UserContext uc = UserContext.get();
		uc.pushSubject( currentTestContext.getSubject() );
		
		EntireNetwork entireNetwork = Factory.EntireNetwork.fetchInstance(currentTestContext.getConnection(), null);
		domain = entireNetwork.get_LocalDomain();
	}

	public void popSubject() {
		UserContext uc = UserContext.get();
		uc.popSubject();
		currentTestContext = null;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getUsername() {
		checkCurrentTestContext();
		return currentTestContext.getUsername();
	}
	
	public String getPassword() {
		checkCurrentTestContext();
		return currentTestContext.getPassword();
	}

	public Connection getConnection() {
		checkCurrentTestContext();
		return currentTestContext.getConnection();
	}

	private void checkCurrentTestContext() {
		if ( currentTestContext == null) {
			throw new IllegalStateException("Current test context is not set");
		}
	}

	public ObjectStore getObjectStore(String name) {
		return Factory.ObjectStore.fetchInstance(domain, name, null);
	}
}
