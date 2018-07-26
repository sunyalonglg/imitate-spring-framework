package com.iwork.core.annotation;

import java.lang.annotation.*;

/**
 * 请求url的注解
 *
 * @version 1.0, 2018-7-18 16:56:22
 * @author sunyalong
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ID {

    public String value() default "";
}
