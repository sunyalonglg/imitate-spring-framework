package com.iwork.core.annotation;

import java.lang.annotation.*;

/**
 * 请求url的注解
 *
 * @version 1.0, 2018-7-18 16:56:22
 * @author sunyalong
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SQL {

    /** sql语句的值 */
    public String value() default "";

    /** 是否编译为,这条sql,如果此值设置为 false那么就会认为这是一条原生的sql语句框架直接执行 */
    public boolean isConpile() default true;

}
