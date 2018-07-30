package com.iwork.core.annotation;

import java.lang.annotation.*;

/**
 * 使用在service层注解,表示是否开启事物,本框架默认是开启事物的.
 *
 * @version 1.0, 2018-7-18 16:56:22
 * @author sunyalong
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Transactional {

    /** 默认为false,false为开启事物,true为无事物下运行本语句 */
    public boolean readOnly() default false;
}
