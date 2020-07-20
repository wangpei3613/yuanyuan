package com.sensebling.common.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
/**
 * 多数据支持，注解属于哪个数据库 按顺序  当前0为db2 1为mysql
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface SenTable {
	int sessionFactoryOrder() default 0;
}
