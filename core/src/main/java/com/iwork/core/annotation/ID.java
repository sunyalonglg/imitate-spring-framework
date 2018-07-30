package com.iwork.core.annotation;

import java.lang.annotation.*;

/**
 * 映射在属性主键上的注解,这个注解主要是在dao继承BaseDao,自动生成的sql findByPrimaryKey 方法和findByPage 方法上使用
 *
 * @version 1.0, 2018-7-18 16:56:22
 * @author sunyalong
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ID {

    /** 无需填写任何值,需要和数据库的主键一样 */
    public String value() default "";
}
