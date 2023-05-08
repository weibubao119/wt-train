package com.dyys.hr.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Desc
 * Create by yangye
 * Date 2023/4/27 11:33
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
public @interface IsNeeded {
    /**
     * 是否需要从解析excel赋值
     * @return
     * true:需要 false:不需要
     * @see [类、类#方法、类#成员]
     */
    boolean isNeeded() default true;
}
