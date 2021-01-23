package com.meng.zspring.framework.beans;

/**
 * Bean工厂
 * @author ZuoHao
 * @date 2021/1/23
 */
public interface ZBeanFactory {
    /**
     * 根据beanName获取IOC容器中Bean
     */
    Object getBean(String beanName);
}
