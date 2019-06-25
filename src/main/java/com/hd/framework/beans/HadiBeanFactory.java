package com.hd.framework.beans;

/**
 * @author Administrator
 * @creator 18051027
 * @create Date 2019/6/25
 * @description
 */
public interface HadiBeanFactory {
    /**
     * getBean
     * 单例工厂顶层设计
     * */
    Object getBean(String beanName) throws Exception;
}
