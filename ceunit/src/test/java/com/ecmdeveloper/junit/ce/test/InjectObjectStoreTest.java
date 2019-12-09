/**
 * 
 */
package com.ecmdeveloper.junit.ce.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Rule;
import org.junit.Test;

import com.ecmdeveloper.ceunit.TestConnection;
import com.ecmdeveloper.ceunit.annotations.TestObjectStore;
import com.filenet.api.core.ObjectStore;

/**
 * @author ricardo.belfor
 *
 */
public class InjectObjectStoreTest {

	private static String username = Configuration.get("TestConfiguration.username");
	private static String password = Configuration.get("TestConfiguration.password");
	private static String url = Configuration.get("TestConfiguration.url");
	
	@Rule
	public TestConnection testConnection = getConnection();

	@TestObjectStore(name="OS")
	private ObjectStore objectStore;
	
	private TestConnection getConnection() {
		return new TestConnection(this, username, password, url );	
	}
	
	@Test
	public void testObjectStoreInjected() throws Exception {
		assertNotNull("Is object store set?", objectStore);
		assertEquals("OS", objectStore.get_Name() );
	}
}
