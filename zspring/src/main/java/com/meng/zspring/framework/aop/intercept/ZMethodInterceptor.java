package com.meng.zspring.framework.aop.intercept;

/**
 * Aop通知方法调用接口
 * @author ZuoHao
 * @date 2021/1/24
 */
public interface ZMethodInterceptor {
    /**
     * 调用
     */
    Object invoke(ZMethodInvocation invocation) throws Throwable;

}
