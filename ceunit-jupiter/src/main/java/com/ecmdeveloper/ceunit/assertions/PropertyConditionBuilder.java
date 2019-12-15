package com.ecmdeveloper.ceunit.assertions;

import java.util.function.Function;

import org.assertj.core.api.Condition;

import com.filenet.api.constants.PropertyState;
import com.filenet.api.core.EngineObject;
import com.filenet.api.property.Property;
import com.filenet.api.util.Id;

/**
 * @author Ricardo Belfor
 *
 */
public class PropertyConditionBuilder {

	private final String name;
	
	private PropertyConditionBuilder(String name) {
		this.name = name;
		
	}
	public static PropertyConditionBuilder property(String name) {
		return new PropertyConditionBuilder(name);
	}
	
	public Condition<EngineObject> withValue(final String expectedValue) {
		Function<EngineObject,String> extractor = engineObject -> engineObject.getProperties().getStringValue(this.name);
		return withValue(expectedValue, extractor);
	}

	public Condition<EngineObject> withValue(final Boolean expectedValue) {
		Function<EngineObject,Boolean> extractor = engineObject -> engineObject.getProperties().getBooleanValue(this.name);
		return withValue(expectedValue, extractor);
	}

	public Condition<EngineObject> withValue(final Integer expectedValue) {
		Function<EngineObject,Integer> extractor = engineObject -> engineObject.getProperties().getInteger32Value(this.name);
		return withValue(expectedValue, extractor);
	}

	public Condition<EngineObject> withValue(final Double expectedValue) {
		Function<EngineObject,Double> extractor = engineObject -> engineObject.getProperties().getFloat64Value(this.name);
		return withValue(expectedValue, extractor);
	}
	
	public Condition<EngineObject> withValue(final Id expectedValue) {
		Function<EngineObject,Id> extractor = engineObject -> engineObject.getProperties().getIdValue(this.name);
		return withValue(expectedValue, extractor);
	}
	
	public Condition<EngineObject> withEmptyValue() {
		final String propertyName = this.name;
		return new Condition<EngineObject>() {

			@Override
			public boolean matches(EngineObject engineObject) {
				
				if ( !engineObject.getProperties().isPropertyPresent(propertyName) ) {
					describedAs("property [%s] with empty value but was missing", propertyName );
					return false;
				}
				
				Property property = engineObject.getProperties().get(propertyName);
				return property.getState().equals(PropertyState.NO_VALUE);
			}
		};
		
	}
	
	private <T> Condition<EngineObject> withValue(final T expectedValue, Function<EngineObject, T> extractor) {
		
		final String propertyName = this.name;
		return new Condition<EngineObject>() {

			@Override
			public boolean matches(EngineObject engineObject) {
				
				if ( !engineObject.getProperties().isPropertyPresent(propertyName) ) {
					describedAs("property [%s] with value <%s> but was missing", propertyName, expectedValue );
					return false;
				}
				
				T actualValue = extractor.apply(engineObject);
				if ( !expectedValue.equals(actualValue) ) {
					describedAs("property [%s] with value <%s> but was <%s>", propertyName, expectedValue, actualValue );
					return false;
				}
				return true;
			}
		};
	}
}
