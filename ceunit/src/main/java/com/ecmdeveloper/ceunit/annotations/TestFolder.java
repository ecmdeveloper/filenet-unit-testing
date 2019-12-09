/**
 * 
 */
package com.ecmdeveloper.ceunit.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ecmdeveloper.ceunit.UniqueNameProvider;
import com.filenet.api.constants.ClassNames;

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
	String className() default ClassNames.FOLDER;
	@SuppressWarnings("rawtypes")
	Class nameProviderClass() default UniqueNameProvider.class; 
}
