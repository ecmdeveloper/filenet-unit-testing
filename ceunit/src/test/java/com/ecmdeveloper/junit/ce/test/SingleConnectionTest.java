/**
 * 
 */
package com.ecmdeveloper.junit.ce.test;

import static com.ecmdeveloper.ceunit.ContentEngineAssert.assertCurrentUser;

import org.junit.Rule;
import org.junit.Test;

import com.ecmdeveloper.ceunit.TestConnection;

/**
 * @author ricardo.belfor
 *
 */
public class SingleConnectionTest {

	private static String username = Configuration.get("TestConfiguration.username");
	private static String password = Configuration.get("TestConfiguration.password");
	private static String url = Configuration.get("TestConfiguration.url");

	@Rule
	public TestConnection testConnection = new TestConnection(this, username, password, url );

	@Test
	public void testConnection() throws Exception {
		assertCurrentUser( "Correct user", testConnection.getConnection(), testConnection.getUsername() );
	}
	
}
