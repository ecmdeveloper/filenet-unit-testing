package com.ecmdeveloper.ceunit.jupiter.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.ecmdeveloper.ceunit.jupiter.ContentEngineExtension;
import com.ecmdeveloper.ceunit.jupiter.annotations.ContentEngineContext;
import com.ecmdeveloper.ceunit.jupiter.annotations.TestFolder;
import com.filenet.api.core.Folder;
import com.filenet.api.core.ObjectStore;

/**
 * @author Ricardo Belfor
 *
 */
@ContentEngineContext(connection = MyObjectStoreConnection.class)
@ExtendWith(ContentEngineExtension.class)
public class ContentEngineExtensionTest {
	
	static Folder staticFolder;
	
	@BeforeAll
	static void initialize_static_folder(@TestFolder(parentPath = "/CEUnit Jupiter", name="First Folder") Folder folder) {
		staticFolder = folder;
	}
	
	@Test
	void verify_static_folder(ObjectStore objectStore) {
		assertEquals("/CEUnit Jupiter/First Folder", staticFolder.get_PathName() );
	}
	
	@Test
	void inject_objectstore(ObjectStore objectStore) throws Exception {
		assertEquals("OS", objectStore.get_SymbolicName());
	}
	
	@Test
	void inject_simple_folder(@TestFolder(parentPath = "/CEUnit Jupiter", name = "Injected Folder") Folder folder) throws Exception {
		assertEquals("/CEUnit Jupiter/Injected Folder", folder.get_PathName() );
	}
	
	@Test
	void inject_folder_with_classname(@TestFolder(parentPath = "/CEUnit Jupiter/TestFolderClass1", 
			name = "Injected Folder", className = "TestFolderClass1") Folder folder) throws Exception {
		assertEquals("/CEUnit Jupiter/TestFolderClass1/Injected Folder", folder.get_PathName() );
		assertEquals("TestFolderClass1", folder.getClassName() );
	}
	
	@Test 
	void inject_folder_with_configuration( @TestFolder(name = "Configured Name Not Used", configuration = MyTestFolderConfiguration.class) Folder folder) {
		assertEquals("/CEUnit Jupiter/FolderConfiguration/Configured Folder Name", folder.get_PathName() );
		assertEquals("TestFolderClass1", folder.getClassName() );
	}
}