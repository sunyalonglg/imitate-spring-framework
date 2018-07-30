package com.iwork.core.annotation;

import java.lang.annotation.*;

/**
 * 和Spring一样表示,请求url的注解
 *
 * @version 1.0, 2018-7-18 16:56:22
 * @author sunyalong
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {

    /** 请求路径 */
    public String value() default "";
}
