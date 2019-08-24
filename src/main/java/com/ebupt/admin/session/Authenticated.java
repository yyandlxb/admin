package com.ebupt.admin.session;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * d定义一个注解
 * @Target  该注解能用在哪些地方   METHOD:方法  TYPE:Class, interface PARAMETER:参数
 * @Retention   运行时保留再虚拟机
 */
@Target({ElementType.METHOD,ElementType.TYPE,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authenticated {
}
