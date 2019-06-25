package com.hd.framework.context;

/**
 * @creator 18051027
 * @create Date 2019/6/25
 * @description 解耦获取ioc容器的顶层设计，通过监听器扫描 实现这个接口的子类
 * 将自动调用setApplicationContext将IOC容器注入到目标类中，观察者模式
 */
public interface HadiApplicationContextAware {

    void setApplicationContext(HadiApplicationContext hadiApplicationContext);
}
