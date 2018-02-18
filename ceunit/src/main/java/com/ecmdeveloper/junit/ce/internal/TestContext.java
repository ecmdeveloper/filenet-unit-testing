/**
 * 
 */
package com.ecmdeveloper.junit.ce.internal;

import javax.security.auth.Subject;

import com.filenet.api.core.Connection;
import com.filenet.api.core.Factory;
import com.filenet.api.util.UserContext;

/**
 * @author ricardo.belfor
 *
 */
public class TestContext {

	private final String username;
	private final String password;
	private final String url;

	private Connection connection;
	private Subject subject;
	
	public TestContext(String username, String password, String url ) {
		this.username = username;
		this.password = password;
		this.url = url;
		
		initialize();
	}

	private void initialize() {
		connection = Factory.Connection.getConnection(url);
		subject = UserContext.createSubject(connection, username, getPassword(), null );
	}
	
	public Subject getSubject() {
		return subject;
	}

	public String getUsername() {
		return username;
	}

	public Connection getConnection() {
		return connection;
	}

	public String getPassword() {
		return password;
	}
}
