/**
 * 
 */
package com.ecmdeveloper.ceunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.anyOf;
import com.filenet.api.constants.PropertyNames;
import com.filenet.api.core.Connection;
import com.filenet.api.core.EngineObject;
import com.filenet.api.core.Factory;
import com.filenet.api.property.Properties;
import com.filenet.api.property.PropertyFilter;
import com.filenet.api.security.User;

/**
 * A set of assertion methods for writing Content Engine asserts. These methods can be used directly:
 * <code>Assert.assertEquals(...)</code>, however, they read better if they
 * are referenced through static import:<br/>
 *
 * <pre>
 * import static com.ecmdeveloper.junit.ce.Assert.*;
 * ...
 * assertEquals(...);
 * </pre>
 *
 * @author ricardo.belfor
 *
 */
public class ContentEngineAssert {

	/** Protected constructor because the class only contains static methods.  */
	protected ContentEngineAssert() {}
	

	/**
	 * Asserts that the short name matches the short name of the user coupled
	 * to the content engine connection.
	 * 
	 * @param connection
	 *            the content engine connection
	 * @param expectedShortname
	 *            the expected short name of the user
	 */
	public static void assertCurrentUser(Connection connection, String expectedShortname ) {
		assertCurrentUser(null, connection, expectedShortname); 
	}


	/**
	 * Asserts that the short name matches the short name of the user coupled
	 * to the content engine connection. If it isn't it throws an AssertionError with the given
	 * message.
	 * 
	 * @param message
	 *            the identifying message for the AssertionError.
	 * @param connection
	 *            the content engine connection
	 * @param expectedShortname
	 *            the expected short name of the user
	 */
	public static void assertCurrentUser(String message, Connection connection, String expectedShortname) {
		final PropertyFilter propertyFilter = getSinglePropertyFilter(PropertyNames.SHORT_NAME);
		final User user = Factory.User.fetchCurrent(connection, propertyFilter);
		assertEquals( message, expectedShortname, user.get_ShortName().toLowerCase() );
	}


	private static PropertyFilter getSinglePropertyFilter(String propertyName) {
		final PropertyFilter propertyFilter = new PropertyFilter();
		propertyFilter.addIncludeProperty(0, null, null, propertyName, null );
		return propertyFilter;
	}
	
	public static void assertPropertyValue(EngineObject engineObject, String propertyName, Object expectedValue ) {
		assertPropertyValue(null, engineObject, propertyName, expectedValue);
	}


	public static void assertPropertyValue(String message, EngineObject engineObject, String propertyName, Object expectedValue ) {
		Properties properties = engineObject.getProperties();
		if ( !properties.isPropertyPresent(propertyName) ) {
			fail("The property '" + propertyName + " is not present in the property cache" );
		}
		
		assertEquals(expectedValue, properties.getObjectValue(propertyName) );
	}
}
