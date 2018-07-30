package com.iwork.core.annotation;

import java.lang.annotation.*;

/**
 * 用來表示是dao的类
 *
 * @version 1.0, 2018-7-18 16:56:22
 * @author sunyalong
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Repostory {

    /** bean的名称 */
    public String value() default "";

    /** 数据库表的名称 */
    public String tableName();
}
