package com.ecmdeveloper.ceunit.jupiter.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.ecmdeveloper.ceunit.jupiter.ContentEngineConfiguration;

@Retention(RUNTIME)
@Target(TYPE)
public @interface ContentEngineContext {
	Class<? extends ContentEngineConfiguration> connection();
}
