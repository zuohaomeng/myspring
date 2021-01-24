package com.meng.zspring.framework.aop.aspect;

import java.lang.reflect.Method;

/**
 * @author ZuoHao
 * @date 2021/1/24
 */
public interface ZJoinPoint {
    Object getThis();

    Object[] getArguments();

    Method getMethod();

    void setUserAttribute(String key, Object value);

    Object getUserAttribute(String key);
}
