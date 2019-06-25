package com.hd.framework.annotation;

import java.lang.annotation.*;

/**
 * @creator 18051027
 * @create Date 2019/6/25
 * @description
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HadiAutowired {
    boolean required() default true;
}
