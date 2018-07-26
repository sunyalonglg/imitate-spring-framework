package com.iwork.core.annotation;

import java.lang.annotation.*;

/**
 *
 *
 * @version 1.0, 2018-7-18 16:56:22
 * @author sunyalong
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {

    public String value() default "";
}
