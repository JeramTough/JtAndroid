package com.jeramtough.jtandroid.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JtServiceImpl
{
	JtBeanPattern pattern() default JtBeanPattern.Singleton;
}
