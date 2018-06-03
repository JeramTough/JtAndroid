package com.jeramtough.jtandroid.ioc;


import com.jeramtough.jtandroid.ioc.bean.JtField;

/**
 * @author 11718
 * on 2017  December 05 Tuesday 22:40.
 */

public interface BeansContainer {

    <T> T getBean(Class<T> requiredBeanClass);

    Object getBean(JtField jtField);

    boolean isContainedBean(Class c);

    void replaceBean(Object object);
}
