/**
 * 
 */
package com.ecmdeveloper.ceunit.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ecmdeveloper.ceunit.UniqueNameProvider;

/**
 * @author ricardo.belfor
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface TestFolder {
	String name() default "";
	String parentPath() default "";
	String objectStoreName();
	Class nameProviderClass() default UniqueNameProvider.class; 
}
