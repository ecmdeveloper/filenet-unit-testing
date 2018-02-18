/**
 * 
 */
package com.ecmdeveloper.junit.ce.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.ecmdeveloper.ceunit.TestConnection;
import com.ecmdeveloper.ceunit.annotations.TestObjectStore;
import com.filenet.api.core.ObjectStore;

/**
 * @author ricardo.belfor
 *
 */
public class InjectMultipleObjectStoresTest {

	private static String username = Configuration.get("TestConfiguration.username");
	private static String password = Configuration.get("TestConfiguration.password");
	private static String url = Configuration.get("TestConfiguration.url");

	private static String username2 = Configuration.get("TestConfiguration.username2"); 
	private static String password2 = Configuration.get("TestConfiguration.password2"); 
	
	@ClassRule
	public static TestConnection staticConnection = new TestConnection(null, username, password, url );
	
	@Rule
	public TestConnection testConnection = getConnection();

	@TestObjectStore(name="P8ConfigObjectStore")
	private static ObjectStore staticObjectStore;

	@TestObjectStore(name="P8ConfigObjectStore")
	private ObjectStore objectStore;
	
	@BeforeClass
	public static void beforeClass() {
		assertNotNull("Is object store set?", staticObjectStore);
		assertEquals("P8ConfigObjectStore", staticObjectStore.get_Name() );
	}
	
	private TestConnection getConnection() {
		return new TestConnection(this, username2, password2, url );	
	}
	
	@Test
	public void testObjectStoreInjected() throws Exception {
		assertNotNull("Is object store set?", objectStore);
		assertEquals("P8ConfigObjectStore", objectStore.get_Name() );
	}
}
