package com.ecmdeveloper.ceunit.assertions.test;

import static com.ecmdeveloper.ceunit.assertions.EngineObjectAssert.assertThat;
import static com.ecmdeveloper.ceunit.assertions.PropertyConditionBuilder.property;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.ecmdeveloper.ceunit.jupiter.ContentEngineExtension;
import com.ecmdeveloper.ceunit.jupiter.annotations.ContentEngineContext;
import com.ecmdeveloper.ceunit.jupiter.annotations.TestFolder;
import com.ecmdeveloper.ceunit.jupiter.test.MyObjectStoreConnection;
import com.filenet.api.constants.PropertyNames;
import com.filenet.api.core.Folder;


/**
 * @author Ricardo Belfor
 *
 */
@ContentEngineContext(connection = MyObjectStoreConnection.class)
@ExtendWith(ContentEngineExtension.class)
public class EngineObjectTest {

	@Test
	void match_engine_object_with_property(@TestFolder(parentPath = "/CEUnit Jupiter/TestFolderClass1", 
			name = "Injected Folder", className = "TestFolderClass1") Folder folder) throws Exception {

		assertThat(folder).hasProperty(PropertyNames.CREATOR);
		assertThat(folder).hasProperty( "Vla");
	}

	@Test
	void match_engine_object_with_property_value(@TestFolder(parentPath = "/CEUnit Jupiter/TestFolderClass1", 
			name = "Injected Folder", className = "TestFolderClass1") Folder folder) throws Exception {

		assertThat(folder).as(folder.get_FolderName())
			.has( property("Creator").withValue("P8Admin") )
			.has( property("IsHiddenContainer").withValue(false) )
			.has( property("LockToken").withEmptyValue() )
			.isNot( property("Vla").withValue("vla") );
	}
}
