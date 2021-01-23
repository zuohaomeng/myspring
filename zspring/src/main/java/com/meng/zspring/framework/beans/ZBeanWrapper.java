package com.meng.zspring.framework.beans;

/**
 * 真正实例的包装类
 *
 * @author ZuoHao
 * @date 2021/1/23
 */
public class ZBeanWrapper {
    /**
     * Bean实例
     */
    private Object wrappedInstance;
    /**
     * 实例类型
     */
    private Class<?> wrappedClass;

    public ZBeanWrapper(Object wrappedInstance) {
        this.wrappedInstance = wrappedInstance;
        this.wrappedClass = wrappedInstance.getClass();
    }

    public Object getWrappedInstance() {
        return wrappedInstance;
    }

    // 返回代理以后的Class
    // 可能会是这个 $Proxy0
    public Class<?> getWrappedClass() {
        return this.wrappedClass;
    }
}
