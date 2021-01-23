package com.meng.zspring.framework.context;

import com.meng.zspring.framework.beans.ZBeanFactory;
import com.meng.zspring.framework.context.support.ZAbstractApplicationContext;

/**
 * @author ZuoHao
 * @date 2021/1/23
 */
public class ZApplicationContext extends ZAbstractApplicationContext implements ZBeanFactory {
    /**
     * 加载IOC容器
     * @throws Exception
     */
    @Override
    public void refresh(){





    }



    @Override
    public Object getBean(String beanName) {
        return null;
    }
}
