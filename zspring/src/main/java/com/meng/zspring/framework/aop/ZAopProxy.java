package com.meng.zspring.framework.aop;

/**
 * 代理类接口
 * @author ZuoHao
 * @date 2021/1/24
 */
public interface ZAopProxy {
    Object getProxy();
    Object getProxy(ClassLoader classLoader);
}
