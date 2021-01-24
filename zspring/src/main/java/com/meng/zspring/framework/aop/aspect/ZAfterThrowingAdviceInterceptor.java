package com.meng.zspring.framework.aop.aspect;

import com.meng.zspring.framework.aop.intercept.ZMethodInterceptor;
import com.meng.zspring.framework.aop.intercept.ZMethodInvocation;

import java.lang.reflect.Method;

/**
 * @author ZuoHao
 * @date 2021/1/24
 */
public class ZAfterThrowingAdviceInterceptor extends ZAbstractAspectAdvice implements ZAdvice, ZMethodInterceptor {
    private String throwingName;

    public ZAfterThrowingAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(ZMethodInvocation mi) throws Throwable {
        try {
            return mi.proceed();
        }catch (Throwable e){
            invokeAdviceMethod(mi,null,e.getCause());
            throw e;
        }
    }

    public void setThrowName(String throwName){
        this.throwingName = throwName;
    }
}
