package com.iwork.core.annotation;

import java.lang.annotation.*;

/**
 * 返回Json的注解
 *
 * @version 1.0, 2018-7-18 16:56:22
 * @author sunyalong
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseBody {

    public String value() default "";
}
