package com.company.factory.impl;

import com.company.factory.BeanFactory;
import com.company.model.BeanDefinition;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

public class BeanFactoryImpl implements BeanFactory {

    //存放完整的bean
    private final ConcurrentHashMap<String,Object> beanMap = new ConcurrentHashMap<>();

    //存放bean定义
    private final ConcurrentHashMap<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>();


    @Override
    public Object getBean(String beanName) throws Exception {
        Object bean = beanMap.get(beanName);
        if (bean != null){
            return bean;
        }
        BeanDefinition beanDefinition = beanDefinitions.get(beanName);
        if (beanDefinition == null)
            throw new RuntimeException("bean不存在"+beanName);
        bean = createBean(beanDefinition);
        if (bean != null){
            //注入bean属性
            populatebean(bean);
            beanMap.put(beanName,bean);
        }
        return bean;
    }

    /**
     * 创建bean
     * @param beanDefinition
     * @return
     * @throws Exception
     */
    private Object createBean(BeanDefinition beanDefinition) throws Exception {
        Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(beanDefinition.getClassName());
        return clazz.newInstance();
    }

    /**
     * 注入属性
     * @param bean
     */
    private void populatebean(Object bean) throws Exception {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field:fields) {
            String beanName = field.getName();
            Object object = getBean(beanName);
            field.setAccessible(true);
            field.set(bean,object);
        }
    }


    /**
     * 注册bean定义信息
     * @param clazz
     */
    public void registerBeanDefinition(Class<?> clazz){
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setClassName(clazz.getName());
        beanDefinitions.put(toLowerFirst(clazz.getSimpleName()),beanDefinition);
    }

    /**
     * 首字母小写
     * @param str
     * @return
     */
    private String toLowerFirst(String str){
        char[] ch = str.toCharArray();
        ch[0] += 32;
        return String.valueOf(ch);
    }
}
