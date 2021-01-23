package com.meng.zspring.framework.annotation;

import java.lang.annotation.*;

/**
 * @author ZuoHao
 * @date 2021/1/23
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZRequestMapping {
    String value() default "";
}
