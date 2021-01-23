package com.meng.zspring.framework.context;

import com.meng.zspring.framework.annotation.ZAutowired;
import com.meng.zspring.framework.annotation.ZController;
import com.meng.zspring.framework.annotation.ZService;
import com.meng.zspring.framework.beans.ZBeanFactory;
import com.meng.zspring.framework.beans.ZBeanWrapper;
import com.meng.zspring.framework.beans.config.ZBeanDefinition;
import com.meng.zspring.framework.beans.config.ZBeanPostProcessor;
import com.meng.zspring.framework.beans.suport.ZBeanDefinitionReader;
import com.meng.zspring.framework.beans.suport.ZDefaultListableBeanFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ZuoHao
 * @date 2021/1/23
 */
public class ZApplicationContext extends ZDefaultListableBeanFactory implements ZBeanFactory {
    private String[] configLocations;
    private ZBeanDefinitionReader reader;

    /**
     * 单例的IOC容器缓存
     */
    private Map<String,Object> singletonObjects = new ConcurrentHashMap<String, Object>();
    /**
     * 通用的IOC容器
     */
    private Map<String, ZBeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<String, ZBeanWrapper>();

    public ZApplicationContext(String... configLocations) {
        this.configLocations = configLocations;
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载IOC容器
     */
    @Override
    public void refresh() throws Exception {
        //1。定位配置文件
        reader = new ZBeanDefinitionReader(this.configLocations);

        //2。加载配置文件，封装为BeanDefinition
        List<ZBeanDefinition> beanDefinitions = reader.loadBeanDefinitions();

        //3。将配置信息放到IOC容器
        doRegisterBeanDefinition(beanDefinitions);


        //4。把不是延时加载的类进行实例化
        doAutowrited();
    }


    private void doRegisterBeanDefinition(List<ZBeanDefinition> beanDefinitions) throws Exception {
        for (ZBeanDefinition beanDefinition : beanDefinitions) {
            if (super.beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())) {
                throw new Exception("The “" + beanDefinition.getFactoryBeanName() + "” is exists!!");
            }
            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
        }
        //到这里为止，容器初始化完毕

    }

    private void doAutowrited() {
        for (Map.Entry<String, ZBeanDefinition> beanDefinitionEntry : super.beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            if (!beanDefinitionEntry.getValue().isLazyInit()) {
                try {
                    getBean(beanName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public Object getBean(Class<?> beanClass) throws Exception {
        return getBean(beanClass.getName());
    }
    @Override
    public Object getBean(String beanName) {
        ZBeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        Object instance = null;
        instance = instantiateBean(beanName,beanDefinition);
        //这个逻辑还不严谨，自己可以去参考Spring源码
        //工厂模式 + 策略模式
        ZBeanPostProcessor postProcessor = new ZBeanPostProcessor();

        postProcessor.postProcessBeforeInitialization(instance,beanName);
        //3、把这个对象封装到BeanWrapper中
        ZBeanWrapper beanWrapper = new ZBeanWrapper(instance);


        //singletonObjects

        //factoryBeanInstanceCache

        //4、把BeanWrapper存到IOC容器里面
//        //1、初始化

//        //class A{ B b;}
//        //class B{ A a;}
//        //先有鸡还是先有蛋的问题，一个方法是搞不定的，要分两次

        //2、拿到BeanWraoper之后，把BeanWrapper保存到IOC容器中去
        this.factoryBeanInstanceCache.put(beanName,beanWrapper);

        postProcessor.postProcessAfterInitialization(instance,beanName);

        //3、注入
        populateBean(beanName,new ZBeanDefinition(),beanWrapper);


        return this.factoryBeanInstanceCache.get(beanName).getWrappedInstance();
    }

    private void populateBean(String beanName, ZBeanDefinition zBeanDefinition, ZBeanWrapper beanWrapper) {
        Object instance = beanWrapper.getWrappedInstance();

//        gpBeanDefinition.getBeanClassName();

        Class<?> clazz = beanWrapper.getWrappedClass();
        //判断只有加了注解的类，才执行依赖注入
        if(!(clazz.isAnnotationPresent(ZController.class) || clazz.isAnnotationPresent(ZService.class))){
            return;
        }

        //获得所有的fields
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if(!field.isAnnotationPresent(ZAutowired.class)){ continue;}

            ZAutowired autowired = field.getAnnotation(ZAutowired.class);

            String autowiredBeanName =  autowired.value().trim();
            if("".equals(autowiredBeanName)){
                autowiredBeanName = field.getType().getName();
            }

            //强制访问
            field.setAccessible(true);

            try {
                //为什么会为NULL，先留个坑
                if(this.factoryBeanInstanceCache.get(autowiredBeanName) == null){ continue; }
//                if(instance == null){
//                    continue;
//                }
                field.set(instance,this.factoryBeanInstanceCache.get(autowiredBeanName).getWrappedInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

    private Object instantiateBean(String beanName, ZBeanDefinition gpBeanDefinition) {
        //1、拿到要实例化的对象的类名
        String className = gpBeanDefinition.getBeanClassName();

        //2、反射实例化，得到一个对象
        Object instance = null;
        try {
//            gpBeanDefinition.getFactoryBeanName()
            //假设默认就是单例,细节暂且不考虑，先把主线拉通
            if(this.singletonObjects.containsKey(className)){
                instance = this.singletonObjects.get(className);
            }else {
                Class<?> clazz = Class.forName(className);
                instance = clazz.newInstance();
                this.singletonObjects.put(className,instance);
                this.singletonObjects.put(gpBeanDefinition.getFactoryBeanName(),instance);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return instance;
    }
    public Set<String> getBeanDefinitionNames(){
        return beanDefinitionMap.keySet();
    }

    public Properties getConfig() {
        return reader.getConfig();
    }
}
