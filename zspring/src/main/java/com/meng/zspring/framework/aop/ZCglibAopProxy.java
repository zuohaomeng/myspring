package com.meng.zspring.framework.aop;

import com.meng.zspring.framework.aop.support.ZAdvisedSupport;

/**
 * @author ZuoHao
 * @date 2021/1/24
 */
public class ZCglibAopProxy implements ZAopProxy{
    public ZCglibAopProxy(ZAdvisedSupport config) {
    }

    @Override
    public Object getProxy() {
        return null;
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return null;
    }
}
