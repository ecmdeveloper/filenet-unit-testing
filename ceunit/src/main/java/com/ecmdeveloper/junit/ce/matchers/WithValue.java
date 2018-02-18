/**
 * 
 */
package com.ecmdeveloper.junit.ce.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import com.filenet.api.core.EngineObject;

/**
 * @author ricardo.belfor
 *
 */
public class WithValue extends BaseMatcher<Object> {

	private final Matcher<?> valueMatcher;
	private Object expectedValue;
	
	protected WithValue(Object expectedValue, Matcher<?> valueMatcher ) {
		this.expectedValue = expectedValue;
		this.valueMatcher = valueMatcher;
	}

	@Override
	public void describeTo(Description description) {
//		description.appendText("an object with a property named '")
//				.appendText(propertyName).appendText("'  ").appendDescriptionOf(valueMatcher);
	}


	@Override
	public boolean matches(Object value ) {
		return valueMatcher.matches( value );
	}
}
