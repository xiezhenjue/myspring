package com.hd.framework.annotation;

import java.lang.annotation.*;

/**
 * @creator 18051027
 * @create Date 2019/6/25
 * @description
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HadiController {
    String value() default "";
}
