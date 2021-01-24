package com.meng.zspring.framework.aop.aspect;

import com.meng.zspring.framework.aop.intercept.ZMethodInterceptor;
import com.meng.zspring.framework.aop.intercept.ZMethodInvocation;

import java.lang.reflect.Method;

/**
 * 方法返回后参数调用
 * @author ZuoHao
 * @date 2021/1/24
 */
public class ZAfterReturningAdviceInterceptor  extends ZAbstractAspectAdvice implements ZAdvice, ZMethodInterceptor {

    private ZJoinPoint joinPoint;

    public ZAfterReturningAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(ZMethodInvocation mi) throws Throwable {
        //调用链路使用
        Object retVal = mi.proceed();
        this.joinPoint = mi;
        this.afterReturning(retVal,mi.getMethod(),mi.getArguments(),mi.getThis());
        return retVal;
    }

    private void afterReturning(Object retVal, Method method, Object[] arguments, Object aThis) throws Throwable {
        super.invokeAdviceMethod(this.joinPoint,retVal,null);
    }
}
