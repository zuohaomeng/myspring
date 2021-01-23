package com.meng.zspring.framework.annotation;

import java.lang.annotation.*;

/**
 * @author ZuoHao
 * @date 2021/1/23
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZController {
    String value() default "";
}
