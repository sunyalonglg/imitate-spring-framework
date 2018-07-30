package com.iwork.core.annotation;

import java.lang.annotation.*;

/**
 *  和spering的注解一样表示当前类是一个Controller
 *
 * @version 1.0, 2018-7-18 16:56:22
 * @author sunyalong
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {

    /** 表示请求的前缀 */
    public String value() default "";
}
