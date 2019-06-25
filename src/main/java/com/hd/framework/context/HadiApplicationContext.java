package com.hd.framework.context;

import com.hd.framework.annotation.HadiAutowired;
import com.hd.framework.annotation.HadiController;
import com.hd.framework.annotation.HadiService;
import com.hd.framework.beans.HadiBeanFactory;
import com.hd.framework.beans.config.HadiBeanDefinition;
import com.hd.framework.beans.support.HadiBeanDefinitionReader;
import com.hd.framework.beans.support.HadiBeanWrapper;
import com.hd.framework.beans.support.HadiDefaultListableBeanFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Administrator IOC->DI->MVC->AOP
 * @creator 18051027
 * @create Date 2019/6/25
 * @description
 */
public class HadiApplicationContext extends HadiDefaultListableBeanFactory implements HadiBeanFactory {

    private String[] configLocations;
    private HadiBeanDefinitionReader reader;
    /**
     * 单例IOC
     * */
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>();

    private Map<String, HadiBeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<String, HadiBeanWrapper>();

    public HadiApplicationContext(String ...configLocations){
        this.configLocations = configLocations;
    }

    @Override
    public void refresh() throws Exception {

        //1 定位配置文件，spring 使用策略模式去定位
         reader = new HadiBeanDefinitionReader(this.configLocations);

        //2 加载配置文件，扫描类并封装成 BeanDefinition
        List<HadiBeanDefinition> beanDefinitions = reader.loadBeanDefinitions();


        //3 注册 将配置信息放到 伪IOC容器
        doRegisterBeanDefinition(beanDefinitions);

        //4 将非懒加载的类 提前初始化
        doAutoWrited();
    }

    private void doAutoWrited() {
        for (Map.Entry<String, HadiBeanDefinition> beanDefinitionEntry : super.beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            if (!beanDefinitionEntry.getValue().isLazyInit()){
                try {
                    getBean(beanName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void doRegisterBeanDefinition(List<HadiBeanDefinition> beanDefinitions) {
        for (HadiBeanDefinition beanDefinition : beanDefinitions) {
            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
        }

    }

    /**
     * 分两步是为了解决循环依赖的问题,真正的DI
     */
    public Object getBean(String beanName) throws Exception {
        //1.初始化
        HadiBeanWrapper beanWrapper = instantiateBean(beanName, new HadiBeanDefinition());

        //2. 拿到beanWrapper后 保存IOC容器中
     /*   if (this.factoryBeanInstanceCache.containsKey(beanName)){
            throw new Exception("The "+ beanName +" is exists!");
        }*/
        this.factoryBeanInstanceCache.put(beanName, beanWrapper);
        //3.注入
        populateBean(beanName, new HadiBeanDefinition(), beanWrapper);
        return  this.factoryBeanInstanceCache.get(beanName).getWrapperdInstance();
    }

    private void populateBean(String beanName, HadiBeanDefinition beanDefinition, HadiBeanWrapper beanWrapper) {

        Object instance = beanWrapper.getWrapperdInstance();

        //只处理加了注解的类

        Class<?> clazz = beanWrapper.getWrappedClass();

        if (clazz.isAnnotationPresent(HadiController.class)|| clazz.isAnnotationPresent(HadiService.class)){
            return;
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(HadiAutowired.class)){
                continue;
            }
            HadiAutowired autowired = field.getAnnotation(HadiAutowired.class);
            String autowireBeanName = beanName;
            boolean required = autowired.required();
            if (!required){
                autowireBeanName = field.getType().getName();
            }
            field.setAccessible(true);
            try {
                field.set(instance, this.factoryBeanInstanceCache.get(autowireBeanName).getWrappedClass());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    private HadiBeanWrapper instantiateBean(String beanName, HadiBeanDefinition beanDefinition) {

        //1 拿到类名
        String beanClassName = beanDefinition.getBeanClassName();
        Object instance = null;
        try {
            //2 反射实例化
            if (this.singletonObjects.containsKey(beanClassName)){
                instance = this.singletonObjects.get(beanClassName);
            }else {
                Class<?> clazz = Class.forName(beanClassName);
                instance = clazz.newInstance();
                this.singletonObjects.put(beanDefinition.getFactoryBeanName(), instance);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        //3 把这个对象封装到beanWrapper
        HadiBeanWrapper beanWrapper = new HadiBeanWrapper(instance);

        //4 将beanWrapper 保存在IOC容器里面
        return beanWrapper;
    }

}
