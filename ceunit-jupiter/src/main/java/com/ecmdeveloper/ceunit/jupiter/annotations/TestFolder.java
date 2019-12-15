package com.ecmdeveloper.ceunit.jupiter.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ecmdeveloper.ceunit.jupiter.TestFolderConfiguration;
import com.ecmdeveloper.ceunit.jupiter.internal.EmptyFolderConfiguration;
import com.filenet.api.constants.ClassNames;

/**
 * @author Ricardo Belfor
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface TestFolder {
	String name() default "";
	String parentPath() default "";
	String className() default ClassNames.FOLDER;
	Class<? extends TestFolderConfiguration> configuration() default EmptyFolderConfiguration.class;
}
