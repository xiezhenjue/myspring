package com.hd.framework.beans.config;

/**
 * @creator 18051027
 * @create Date 2019/6/25
 * @description
 */

public class HadiBeanDefinition {

    private String beanClassName;
    /**
     * 懒加载标识
     * */
    private boolean isLazyInit = false;
    /**
     * 类存在的地方
     * */
    private String factoryBeanName;

    private boolean isSingleton=true;

    public boolean isSingleton() {
        return isSingleton;
    }

    public void setSingleton(boolean singleton) {
        isSingleton = singleton;
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    public boolean isLazyInit() {
        return isLazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        isLazyInit = lazyInit;
    }

    public String getFactoryBeanName() {
        return factoryBeanName;
    }

    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }
}
