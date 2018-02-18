/**
 * 
 */
package com.ecmdeveloper.junit.ce.test;

import static com.ecmdeveloper.ceunit.ContentEngineAssert.assertPropertyValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.ecmdeveloper.ceunit.TestConnection;
import com.ecmdeveloper.ceunit.TimestampNameProvider;
import com.ecmdeveloper.ceunit.annotations.TestFolder;
import com.filenet.api.constants.PropertyNames;
import com.filenet.api.core.Folder;

/**
 * @author ricardo.belfor
 *
 */
public class InjectTestFolderTest {

	private static final String TEST_FOLDER_NAME = "MyTest";
	private static final String STATIC_TEST_FOLDER_NAME = "MyStaticTest";
	private static final String TEST_FOLDER_PATH = "/MyTestFolder/MyTestSubFolder2";

	private static String username = Configuration.get("TestConfiguration.username");
	private static String password = Configuration.get("TestConfiguration.password");
	private static String url = Configuration.get("TestConfiguration.url");

	@Rule
	public TestConnection testConnection = new TestConnection(this, username, password, url );

	@TestFolder(objectStoreName="P8ConfigObjectStore", parentPath = TEST_FOLDER_PATH, name=STATIC_TEST_FOLDER_NAME )
	Folder staticFolder;
	
	@TestFolder(objectStoreName="P8ConfigObjectStore", parentPath = TEST_FOLDER_PATH, name=TEST_FOLDER_NAME )
	Folder folder;
	
	@TestFolder(objectStoreName= "P8ConfigObjectStore", parentPath = TEST_FOLDER_PATH )
	Folder randomFolder;

	@TestFolder(objectStoreName= "P8ConfigObjectStore", parentPath = TEST_FOLDER_PATH, nameProviderClass = TimestampNameProvider.class )
	Folder timeStampFolder;
	
	@TestFolder(objectStoreName= "P8ConfigObjectStore", parentPath = "/MyTestFolder/{c}/{m}")
	Folder dynamicFolder;
	
	@Before
	public void before()
	{
		assertNotNull(staticFolder);
		assertPropertyValue("Correct folder path?", staticFolder,
				PropertyNames.PATH_NAME, TEST_FOLDER_PATH + "/"
						+ STATIC_TEST_FOLDER_NAME);
	}
	
	@Test
	public void testFolder() {
		assertNotNull(folder);
		assertPropertyValue("Correct folder path?", folder,
				PropertyNames.PATH_NAME, TEST_FOLDER_PATH + "/"
						+ TEST_FOLDER_NAME);
	}
	
	@Test
	public void testRandomFolder() {
		assertNotNull(randomFolder);
		String pathName = randomFolder.get_PathName();
		assertTrue( pathName.startsWith( TEST_FOLDER_PATH ) );
	}
	
	@Test
	public void testTimestampFolder() {
		assertNotNull(timeStampFolder );
		String pathName = timeStampFolder.get_PathName();
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd HHmm");
		assertTrue( pathName.startsWith( TEST_FOLDER_PATH + "/" + dateFormatter.format( new Date() ) ) );
		
	}
	
	@Test
	public void testDynamicFolder() {
		assertNotNull(dynamicFolder);
		String pathName = dynamicFolder.get_PathName();
		assertTrue( pathName.startsWith( "/MyTestFolder/InjectTestFolderTest/testDynamicFolder/" ) );
	}
}
