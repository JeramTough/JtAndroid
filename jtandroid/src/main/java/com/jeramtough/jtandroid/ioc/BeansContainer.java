package com.jeramtough.jtandroid.ioc;


import com.jeramtough.jtandroid.ioc.bean.JtField;
import com.jeramtough.jtandroid.ioc.exception.RegisterBeanException;

/**
 * @author 11718
 * on 2017  December 05 Tuesday 22:40.
 */

public interface BeansContainer {

    <T> T getBean(Class<T> requiredBeanClass);

    Object getBean(JtField jtField);

    boolean isContainedBean(Class c);

    /**
     * 替换Bean
     *
     * @param bean Bean
     */
    void replaceBean(Object bean);

    /**
     *
     * 将Bean注册到容器中
     *
     * @param bean Bean
     */
    void registerBean(Object bean) throws RegisterBeanException;
}
