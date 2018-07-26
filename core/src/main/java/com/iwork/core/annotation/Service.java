package com.iwork.core.annotation;

import java.lang.annotation.*;

/**
 * service註解標註這個是一個service類
 *
 * @version 1.0, 2018-7-18 16:56:22
 * @author sunyalong
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {

    public String value() default "";
}
