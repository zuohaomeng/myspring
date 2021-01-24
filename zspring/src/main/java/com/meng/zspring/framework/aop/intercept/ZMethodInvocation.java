package com.meng.zspring.framework.aop.intercept;

import com.meng.zspring.framework.aop.aspect.ZJoinPoint;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JDKInvocation代理类
 * 代表一个方法的所有通知，然后进行链路调用
 * @author ZuoHao
 * @date 2021/1/24
 */
public class ZMethodInvocation implements ZJoinPoint {
    private Object proxy;
    private Method method;
    private Object target;
    private Object [] arguments;
    private List<Object> interceptorsAndDynamicMethodMatchers;
    private Class<?> targetClass;

    private Map<String, Object> userAttributes;

    /**
     * 定义一个索引，从-1开始来记录当前拦截器执行的位置
     */
    private int currentInterceptorIndex = -1;

    /**
     *
     * @param proxy 代理类
     * @param target 目标类
     * @param method 代理方法
     * @param arguments 参数
     * @param targetClass 目标类类型
     * @param interceptorsAndDynamicMethodMatchers   通知调用链
     */
    public ZMethodInvocation(
            Object proxy, Object target, Method method, Object[] arguments,
            Class<?> targetClass, List<Object> interceptorsAndDynamicMethodMatchers) {

        this.proxy = proxy;
        this.target = target;
        this.targetClass = targetClass;
        this.method = method;
        this.arguments = arguments;
        this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
    }

    public Object proceed() throws Throwable {
        //如果Interceptor执行完了，则执行joinPoint（被代理方法）
        if (this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
            return this.method.invoke(this.target,this.arguments);
        }

        Object interceptorOrInterceptionAdvice =
                this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex);
        //如果要动态匹配joinPoint
        if (interceptorOrInterceptionAdvice instanceof ZMethodInterceptor) {
            ZMethodInterceptor mi =
                    (ZMethodInterceptor) interceptorOrInterceptionAdvice;
            return mi.invoke(this);
        } else {
            //动态匹配失败时,略过当前Intercetpor,调用下一个Interceptor
            return proceed();
        }
    }

    @Override
    public Object getThis() {
        return this.target;
    }

    @Override
    public Object[] getArguments() {
        return this.arguments;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    /**
     * 设置属性，在同一个方法前后使用
     * 如：在Before中设置，在AfterReturn中使用
     * 实现参数的传递，与业务逻辑解耦
     * @param key
     * @param value
     */
    @Override
    public void setUserAttribute(String key, Object value) {
        if (value != null) {
            if (this.userAttributes == null) {
                this.userAttributes = new HashMap<String,Object>();
            }
            this.userAttributes.put(key, value);
        }
        else {
            if (this.userAttributes != null) {
                this.userAttributes.remove(key);
            }
        }
    }


    @Override
    public Object getUserAttribute(String key) {
        return (this.userAttributes != null ? this.userAttributes.get(key) : null);
    }
}
