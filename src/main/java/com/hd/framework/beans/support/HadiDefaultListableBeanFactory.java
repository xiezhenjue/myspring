package com.hd.framework.beans.support;

import com.hd.framework.beans.config.HadiBeanDefinition;
import com.hd.framework.context.support.HadiAbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Administrator
 * @creator 18051027
 * @create Date 2019/6/25
 * @description
 */
public class HadiDefaultListableBeanFactory extends HadiAbstractApplicationContext {

    /**
     * 注册容器
     * */
    public final Map<String, HadiBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, HadiBeanDefinition>();



}
