package com.iwork.core.annotation;

import java.lang.annotation.*;

/**
 * 表示这个方法只接手Json格式的数据.
 *
 * @version 1.0, 2018-7-18 16:56:22
 * @author sunyalong
 */
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestBody {


}
