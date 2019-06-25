package com.hd.framework.beans.support;

/**
 * @creator 18051027
 * @create Date 2019/6/25
 * @description
 */
public class HadiBeanWrapper {
    private Object wrappedInstance;
    private Class<?> wrappedClass;

    public HadiBeanWrapper(Object instance){
        this.wrappedInstance = instance;
    }

    public Object getWrapperdInstance(){
        return this.wrappedInstance;
    }

    public Class<?> getWrappedClass(){
        return this.wrappedClass;
    }

}



