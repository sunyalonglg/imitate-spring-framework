package com.iwork.core.annotation;

import java.lang.annotation.*;

/**
 * 和spring的注解一样,表示注入一个同等类型的属性
 *
 * @version 1.0, 2018-7-18 16:56:22
 * @author sunyalong
 */
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowwired {

    /** 默认为空,不需要填写值 */
    public String value() default "";
}
