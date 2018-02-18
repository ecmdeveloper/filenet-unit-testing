/**
 * 
 */
package com.ecmdeveloper.junit.ce.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

import com.filenet.api.core.EngineObject;

/**
 * @author ricardo.belfor
 *
 */
public class HasPropertyWithValue extends TypeSafeMatcher<EngineObject>{

	private final String propertyName;
	private final Matcher<?> valueMatcher;
	
	protected HasPropertyWithValue(String propertyName, Matcher<?> valueMatcher ) {
		this.propertyName = propertyName;
		this.valueMatcher = valueMatcher;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("an object with a property named '")
				.appendText(propertyName).appendText("'  ").appendDescriptionOf(valueMatcher);
	}

	@Override
	public boolean matchesSafely(EngineObject engineObject) {
		if ( !engineObject.getProperties().isPropertyPresent(propertyName) ) {
			return false;
		}
		return valueMatcher.matches( engineObject.getProperties().getObjectValue(propertyName) );
	}
	
	@Factory
	public static Matcher<EngineObject> hasPropertyWithValue(String propertyName, Matcher<?> valueMatcher ) {
		return new HasPropertyWithValue(propertyName, valueMatcher );
	}
}
