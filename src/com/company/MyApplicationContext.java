package com.company;

import com.company.annotation.Component;
import com.company.factory.impl.BeanFactoryImpl;

import java.io.File;

public class MyApplicationContext  extends BeanFactoryImpl {

    private static final String PKG_PATH = Thread.currentThread().getContextClassLoader().getResource("").getPath();


    public void init(){
        try {
            scanBasePackage(PKG_PATH);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否含有Component注解
     * @param path
     * @throws ClassNotFoundException
     */
    private void hasComponentAnnotation(String path) throws ClassNotFoundException {
        String className = path.substring(PKG_PATH.length()-1,path.length()-6).replaceAll("\\\\",".");
        Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
        if (clazz.isAnnotationPresent(Component.class)){
            registerBeanDefinition(clazz);
        }
    }

    /**
     * 扫描项目包
     * @param path
     * @throws ClassNotFoundException
     */
    private void scanBasePackage(String path) throws ClassNotFoundException {
        File file = new File(path);
        File[] files = file.listFiles();
        for (File f:files) {
            if (f.isDirectory()){
                scanBasePackage(path+f.getName()+"/");
            }else{
                hasComponentAnnotation(f.getPath());
            }
        }
    }
}
