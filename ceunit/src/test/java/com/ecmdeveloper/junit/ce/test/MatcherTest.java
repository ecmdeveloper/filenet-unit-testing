/**
 * 
 */
package com.ecmdeveloper.junit.ce.test;

 import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;

import com.ecmdeveloper.ceunit.TestConnection;
import com.ecmdeveloper.ceunit.annotations.TestFolder;
import com.filenet.api.constants.PropertyNames;
import com.filenet.api.core.Folder;
import static com.ecmdeveloper.junit.ce.matchers.HasProperty.hasProperty;
import static com.ecmdeveloper.junit.ce.matchers.HasPropertyWithValue.hasPropertyWithValue;

import static org.hamcrest.CoreMatchers.*;

/**
 * @author ricardo.belfor
 *
 */
public class MatcherTest {

	private static final String TEST_FOLDER_PATH = "/MyTestFolder/{c}";

	private static String username = Configuration.get("TestConfiguration.username");
	private static String password = Configuration.get("TestConfiguration.password");
	private static String url = Configuration.get("TestConfiguration.url");

	@Rule
	public TestConnection testConnection = new TestConnection(this, username, password, url );

	@TestFolder(objectStoreName="P8ConfigObjectStore", parentPath = TEST_FOLDER_PATH )
	Folder folder;
	
	@Test
	public void test() {
//		assertThat(folder, hasProperty( PropertyNames.CREATOR) );
//		assertThat(folder, not( hasProperty("bar") ) );
		assertThat(folder, hasProperty("bar" ) );
//		System.out.println( folder.get_Creator() );
//		assertThat(folder, hasPropertyWithValue(PropertyNames.CREATOR, is("P8Admin") ) );
//		describedAs(description, matcher, values)
	}

}
