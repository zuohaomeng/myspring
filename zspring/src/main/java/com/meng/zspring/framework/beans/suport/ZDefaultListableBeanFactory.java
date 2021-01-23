package com.meng.zspring.framework.beans.suport;

import com.meng.zspring.framework.beans.ZBeanFactory;
import com.meng.zspring.framework.beans.config.ZBeanDefinition;
import com.meng.zspring.framework.context.support.ZAbstractApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZuoHao
 * @date 2021/1/23
 */
public class ZDefaultListableBeanFactory extends ZAbstractApplicationContext {
    /**
     * beanDefinition容器
     */
    protected final Map<String,ZBeanDefinition> beanDefinitionMap = new HashMap<>(256);

}
