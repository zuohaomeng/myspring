package com.meng.zspring.framework.beans.config;

/**
 * 保存Bean的注册信息
 * @author ZuoHao
 * @date 2021/1/23
 */
public class ZBeanDefinition  {
    /**
     * bean的类名
     */
    private String beanClassName;
    /**
     * 初始化
     */
    private boolean lazyInit = false;
    /**
     * 工厂创建Bean方法
     */
    private String factoryBeanName;

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public String getFactoryBeanName() {
        return factoryBeanName;
    }

    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }
}
