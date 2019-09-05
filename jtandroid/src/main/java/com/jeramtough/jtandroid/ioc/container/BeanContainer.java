package com.jeramtough.jtandroid.ioc.container;


import android.support.annotation.NonNull;

import com.jeramtough.jtandroid.ioc.annotation.JtBeanPattern;

import java.util.concurrent.Future;

/**
 * 负责管理Bean对象
 *
 * @author 11718
 * on 2017  December 05 Tuesday 22:40.
 */

public interface BeanContainer {

    /**
     * get bean instance
     *
     * @param beanClass the class of bean
     * @param <T>       `
     * @return return null if don't exist otherwise return bean instance.
     */
    <T> T getBean(Class<T> beanClass);


    /**
     * Is contained instance of singleton or prototype bean
     *
     * @param beanClass the class of bean
     * @return return false if don't exist
     */
    boolean isContainedBean(Class beanClass);


    /**
     * Is contained instance of singleton bean
     *
     * @param beanClass the class of bean
     * @return return false if don't exist
     */
    boolean isContainedSingletonBean(Class beanClass);


    /**
     * Is prototype instance of singleton bean
     *
     * @param beanClass the class of bean
     * @return return false if don't exist
     */
    boolean isContainedPrototypeBean(Class beanClass);

    /**
     * replace Bean
     *
     * @param bean bean can be plain java object but must be a single mode bean.
     */
    void replaceBean(@NonNull Object bean);

    /**
     * register bean instance into the IocContainer.
     *
     * @param bean          bean can be plain java object or instance of interface
     * @param jtBeanPattern {@link JtBeanPattern}
     */
    void registerBean(@NonNull Object bean, JtBeanPattern jtBeanPattern);


    /**
     * register and instance JtBean by class of bean, than put it into the IocContainer
     *
     * @param beanClass beanClass can't be a class of interface and must be a class of
     *                  JtBean{@link com.jeramtough.jtandroid.ioc.annotation.JtComponent}
     *                  {@link com.jeramtough.jtandroid.ioc.annotation.JtServiceImpl}
     * @link com.jeramtough.jtandroid.ioc.annotation.JtController}
     */
    void registerBean(Class beanClass);

    /**
     * Async register bean
     *
     * @see #registerBean(Object, JtBeanPattern)
     */
    Future<Class> registerBeanAsync(Class beanClass);


}
