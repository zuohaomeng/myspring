package com.meng.zspring.framework.beans.config;

/**
 * @author ZuoHao
 * @date 2021/1/23
 */
public class ZBeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }
}
