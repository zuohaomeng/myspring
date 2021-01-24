package com.meng.zspring.framework.aop;

import com.meng.zspring.framework.aop.intercept.ZMethodInvocation;
import com.meng.zspring.framework.aop.support.ZAdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 *
 * JDK动态代理类
 * @author ZuoHao
 * @date 2021/1/24
 */
public class ZJdkDynamicAopProxy implements ZAopProxy, InvocationHandler {
    private ZAdvisedSupport advised;

    public ZJdkDynamicAopProxy(ZAdvisedSupport config){
        this.advised = config;
    }

    @Override
    public Object getProxy() {
        return getProxy(this.advised.getTargetClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return Proxy.newProxyInstance(classLoader,this.advised.getTargetClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<Object> interceptorsAndDynamicMethodMatchers = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method,this.advised.getTargetClass());
        ZMethodInvocation invocation = new ZMethodInvocation(proxy,this.advised.getTarget(),method,args,this.advised.getTargetClass(),interceptorsAndDynamicMethodMatchers);
        return invocation.proceed();
    }
}
