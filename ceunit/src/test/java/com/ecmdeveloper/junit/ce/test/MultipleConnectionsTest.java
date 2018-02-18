/**
 * 
 */
package com.ecmdeveloper.junit.ce.test;

import static com.ecmdeveloper.ceunit.ContentEngineAssert.assertCurrentUser;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.ecmdeveloper.ceunit.TestConnection;

/**
 * @author ricardo.belfor
 *
 */
public class MultipleConnectionsTest {

	private static String username = Configuration.get("TestConfiguration.username");
	private static String password = Configuration.get("TestConfiguration.password");
	private static String url = Configuration.get("TestConfiguration.url");

	private static String username2 = Configuration.get("TestConfiguration.username2"); 
	private static String password2 = Configuration.get("TestConfiguration.password2"); 
	
	@ClassRule
	public static TestConnection testConnection = new TestConnection(null, username, password, url );
	
	@Rule
	public TestConnection runConnection = new TestConnection(this, username2, password2, url );
	
	@BeforeClass
	public static void beforeClass() {
		assertCurrentUser( "Correct user", testConnection.getConnection(), username );
	}
	
	@AfterClass
	public static void afterClass() {
		assertCurrentUser( "Correct user", testConnection.getConnection(), username );
	}

	@Before
	public void before() {
		assertCurrentUser( "Correct user", runConnection.getConnection(), username2 );
	}
	
	@After
	public void after() {
		assertCurrentUser( "Correct user", runConnection.getConnection(), username2 );
	}
	
	@Test
	public void test() {
		assertCurrentUser( "Correct user", runConnection.getConnection(), username2 );
	}
}
