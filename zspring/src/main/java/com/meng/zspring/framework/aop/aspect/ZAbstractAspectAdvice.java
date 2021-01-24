package com.meng.zspring.framework.aop.aspect;

import java.lang.reflect.Method;

/**
 * 抽象通知方法类
 * @author ZuoHao
 * @date 2021/1/24
 */
public class ZAbstractAspectAdvice implements ZAdvice {
    /**
     * 代理方法，如Before，after等切面方法
     */
    private Method aspectMethod;
    /**
     * 代理类
     */
    private Object aspectTarget;

    public ZAbstractAspectAdvice(Method aspectMethod, Object aspectTarget) {
        this.aspectMethod = aspectMethod;
        this.aspectTarget = aspectTarget;
    }

    /**
     * 调用通知方法
     * @throws Throwable
     */
    public Object invokeAdviceMethod(ZJoinPoint joinPoint, Object returnValue, Throwable tx) throws Throwable {
        //获取方法参数类型
        Class<?>[] paramTypes = this.aspectMethod.getParameterTypes();
        if (paramTypes.length == 0) {
            return this.aspectMethod.invoke(aspectTarget);
        } else {
            Object[] args = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                //设置参数
                if (paramTypes[i] == ZJoinPoint.class) {
                    args[i] = joinPoint;
                } else if (paramTypes[i] == Throwable.class) {
                    args[i] = tx;
                } else if (paramTypes[i] == Object.class) {
                    args[i] = returnValue;
                }
            }
            //进行调用
            return this.aspectMethod.invoke(aspectTarget, args);
        }
    }
}
