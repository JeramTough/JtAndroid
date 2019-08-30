package com.jeramtough.jtandroid.ioc.container;


import android.support.annotation.NonNull;

import java.util.concurrent.Future;

/**
 * 负责管理Bean对象
 *
 * @author 11718
 * on 2017  December 05 Tuesday 22:40.
 */

public interface BeansContainer {

    /**
     * get bean instance
     *
     * @param beanClass the class of bean
     * @param <T>       `
     * @return return null if don't exist otherwise return bean instance.
     */
    <T> T getBean(Class<T> beanClass);


    boolean isContainedBean(Class c);

    /**
     * replace Bean
     *
     * @param bean bean can be plain java object
     */
    void replaceBean(@NonNull Object bean);

    /**
     * register bean instance into the IocContainer.
     *
     * @param bean bean can be plain java object
     */
    void registerBean(@NonNull Object bean);

    /**
     * register bean instance into the IocContainer.
     *
     * @param aliasName custom name of bean instance
     * @param bean      bean can be plain java object
     */
    void registerBean(String aliasName, @NonNull Object bean);


    /**
     * register and instance bean into the IocContainer by class of bean
     *
     * @param beanClass beanClass can't be a class of interface
     */
    void registerBean(Class beanClass);


    Future<Class> registerBeanAsync(Class beanClass);

}
