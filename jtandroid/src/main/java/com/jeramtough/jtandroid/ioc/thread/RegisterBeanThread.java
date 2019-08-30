package com.jeramtough.jtandroid.ioc.thread;

import java.util.concurrent.Callable;

/**
 * Created on 2019-08-29 20:11
 * by @author JeramTough
 */
public abstract class RegisterBeanThread implements Callable<Class> {

    private Class beanClass;

    public RegisterBeanThread(Class beanClass) {
        this.beanClass = beanClass;
    }

    public Class getBeanClass() {
        return beanClass;
    }


}