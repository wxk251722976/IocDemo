package com.company.factory;

/**
 * bean工厂
 */
public interface BeanFactory {

    /**
     * 根据bean名称获取bean
     * @param beanName
     * @return
     */
    Object getBean(String beanName) throws Exception;

}
