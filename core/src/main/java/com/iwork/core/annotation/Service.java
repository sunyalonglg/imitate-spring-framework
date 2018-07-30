package com.iwork.core.annotation;

import java.lang.annotation.*;

/**
 * 和spring的service注解一样表示这个是一个service业务类
 *
 * @version 1.0, 2018-7-18 16:56:22
 * @author sunyalong
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {

    /** 可以指定bean的名称,默认是类名小驼峰 */
    public String value() default "";
}
