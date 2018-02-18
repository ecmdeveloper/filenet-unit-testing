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
public class HasProperty extends TypeSafeMatcher<EngineObject>{

	private final String propertyName;
	
	protected HasProperty(String propertyName) {
		this.propertyName = propertyName;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("an object with a property named '")
				.appendText(propertyName).appendText("'");
	}

	@Override
	public boolean matchesSafely(EngineObject engineObject) {
		return engineObject.getProperties().isPropertyPresent(propertyName);
	}
	
	@Factory
	public static Matcher<EngineObject> hasProperty(String propertyName) {
		return new HasProperty(propertyName);
	}
	
//	public Matcher<?> withValue(Matcher<?> valueMatcher ) {
//		
//		return new WithValue(expectedValue, valueMatcher);
//	}
}
