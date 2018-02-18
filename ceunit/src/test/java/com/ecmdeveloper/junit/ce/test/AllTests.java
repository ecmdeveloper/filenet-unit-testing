/**
 * 
 */
package com.ecmdeveloper.junit.ce.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author ricardo.belfor
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ InjectObjectStoreTest.class, MultipleConnectionsTest.class,
		MultipleUsersTest.class, SingleConnectionTest.class,
		InjectMultipleObjectStoresTest.class,
		MultipleObjectStoreAnnotationsTest.class,
		InjectTestFolderNoParentTest.class,
		InjectTestFolderTest.class})
public class AllTests {

}
