package com.iwork.core.annotation;

import java.lang.annotation.*;

/**
 * 属性上的注解
 *
 * @version 1.0, 2018-7-18 16:56:22
 * @author sunyalong
 */
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Resource {

    public String value() default "";
}
