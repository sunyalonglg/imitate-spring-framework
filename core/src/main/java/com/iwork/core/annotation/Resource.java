package com.iwork.core.annotation;

import java.lang.annotation.*;

/**
 * 和Spring的Resource一样默认按照类型注入,但是还可以按照bean的名称注入
 *
 * @version 1.0, 2018-7-18 16:56:22
 * @author sunyalong
 */
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Resource {

    /** 填写bean的名称 */
    public String value() default "";
}
