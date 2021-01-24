package com.meng.zspring.framework.aop.aspect;

import com.meng.zspring.framework.aop.intercept.ZMethodInterceptor;
import com.meng.zspring.framework.aop.intercept.ZMethodInvocation;

import java.lang.reflect.Method;

/**
 * @author ZuoHao
 * @date 2021/1/24
 */
public class ZMethodBeforeAdviceInterceptor extends ZAbstractAspectAdvice implements ZAdvice, ZMethodInterceptor {


    private ZJoinPoint joinPoint;
    public ZMethodBeforeAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    private void before(Method method,Object[] args,Object target) throws Throwable{
        //传送了给织入参数
        //method.invoke(target);
        super.invokeAdviceMethod(this.joinPoint,null,null);

    }
    @Override
    public Object invoke(ZMethodInvocation mi) throws Throwable {
        //从被织入的代码中才能拿到，JoinPoint
        this.joinPoint = mi;
        before(mi.getMethod(), mi.getArguments(), mi.getThis());
        return mi.proceed();
    }
}