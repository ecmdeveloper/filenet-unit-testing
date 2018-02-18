/**
 * 
 */
package com.ecmdeveloper.junit.ce.test;

import static com.ecmdeveloper.ceunit.ContentEngineAssert.assertCurrentUser;

import org.junit.Rule;
import org.junit.Test;

import com.ecmdeveloper.ceunit.TestConnection;
import com.ecmdeveloper.ceunit.annotations.Credentials;

/**
 * @author ricardo.belfor
 *
 */
public class MultipleUsersTest {

	private static String url = Configuration.get("TestConfiguration.url");

	@Rule
	public TestConnection testConnection = getConnection();
	
	@Credentials(username="p8admin", password="Welkom01" )
	@Test
	public void testConnectionWithCredentials() {
		assertCurrentUser( "Correct user", testConnection.getConnection(), "p8admin" );
	}
	
	@Credentials(username="cchicken", password="password" )
	@Test
	public void testConnectionWithOtherCredentials() {
		assertCurrentUser( "Correct user", testConnection.getConnection(), "cchicken" );
	}
	
	private TestConnection getConnection() {
		return new TestConnection(this, url );	
	}
}
