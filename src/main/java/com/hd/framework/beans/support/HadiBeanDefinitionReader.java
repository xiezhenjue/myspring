package com.hd.framework.beans.support;

import com.hd.framework.beans.config.HadiBeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @creator 18051027
 * @create Date 2019/6/25
 * @description
 */
public class HadiBeanDefinitionReader {

    private List<String> registryBeanClasses = new ArrayList<String>();

    private final String SCANNER_PACKAGE = "scanPackage";
    private Properties properties = new Properties();
    public HadiBeanDefinitionReader(String ...locations){
        InputStream classPath = this.getClass().getClassLoader().getResourceAsStream(locations[0].replace("classPath", ""));
        try {
            properties.load(classPath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null!=classPath){
                try {
                    classPath.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        doScanner(properties.getProperty(SCANNER_PACKAGE));
    }

    private void doScanner(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File classPath = new File(url.getFile());
        for (File file : classPath.listFiles()){
            if (file.isDirectory()){
                doScanner(scanPackage + "." + file.getName());
            }else {
                if (file.getName().endsWith(".class")){
                    continue;
                }
                String className = scanPackage + "." + file.getName().replace(".class", "");
                registryBeanClasses.add(className);
            }
        }
    }

    public Properties getConfig(){
        return this.properties;
    }

    public List<HadiBeanDefinition> loadBeanDefinitions()throws Exception{

        List result = new ArrayList<HadiBeanDefinition>();
        for (String className : registryBeanClasses){
            HadiBeanDefinition beanDefinition = doCreateBeanDefinition(className);
            if (null == beanDefinition){
                continue;
            }
            result.add(beanDefinition);
        }
        return result;
    }

    private HadiBeanDefinition doCreateBeanDefinition(String className) {
        try {
            Class<?> beanClass = Class.forName(className);
            //可能是接口
            if (!beanClass.isInterface()){
                HadiBeanDefinition  beanDefinition = new HadiBeanDefinition();
                beanDefinition.setBeanClassName(className);
                beanDefinition.setFactoryBeanName(beanClass.getSimpleName());
                return beanDefinition;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
