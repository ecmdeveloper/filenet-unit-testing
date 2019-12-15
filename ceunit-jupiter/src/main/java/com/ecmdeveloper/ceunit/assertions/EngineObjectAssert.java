package com.ecmdeveloper.ceunit.assertions;

import java.util.Comparator;
import java.util.Objects;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Condition;

import com.filenet.api.core.EngineObject;

/**
 * @author Ricardo Belfor
 *
 */
public class EngineObjectAssert extends AbstractAssert<EngineObjectAssert, EngineObject> {

	private String propertyName;

	public EngineObjectAssert(EngineObject actual) {
		super(actual, EngineObjectAssert.class);
	}

	public static EngineObjectAssert assertThat(EngineObject actual) {
		return new EngineObjectAssert(actual);
	}

	public EngineObjectAssert hasProperty(String propertyName) {

		if (!actual.getProperties().isPropertyPresent(propertyName) ) {
			 failWithMessage("Property <%s> is not present in object", propertyName );
		}
		
		return this; 
	}
	
	public EngineObjectAssert withProperty(String propertyName) {
		this.propertyName = propertyName;
		return this;
	}
	
//	@Override
//	public EngineObjectAssert isEqualTo(Object value) {
//		Object propertyValue = getPropertyValue();
//		if ( !Objects.equals(propertyValue, value)) {
//		   failWithMessage("Expected property %s to be <%s> but was <%s>", propertyName, value, propertyValue);
//		}
//		return this;
//	}
//
//	@Override
//	public void isNull() {
//		ensureDescription();
//		objects.assertNull(info, getPropertyValue() );
//	}
//
//	@Override
//	public EngineObjectAssert isNotNull() {
//		ensureDescription();
//		objects.assertNotNull(info, getPropertyValue());
//		return this;
//	}
//
//	private void ensureDescription() {
//		if ( !info.hasDescription() ) {
//			info.description("property name <%s>", propertyName);
//		}
//	}
//
//	private Object getPropertyValue() {
//		return actual.getProperties().getObjectValue(propertyName);
//	}
}
