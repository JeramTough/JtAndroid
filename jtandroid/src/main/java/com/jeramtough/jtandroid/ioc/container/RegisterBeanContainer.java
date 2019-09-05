package com.jeramtough.jtandroid.ioc.container;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jeramtough.jtandroid.ioc.annotation.JtBeanPattern;
import com.jeramtough.jtandroid.ioc.exception.RegisterBeanException;
import com.jeramtough.jtandroid.ioc.interpreter.BeanInterpreterForClass;
import com.jeramtough.jtandroid.ioc.thread.RegisterBeanThread;
import com.jeramtough.jtandroid.ioc.thread.RegisterBeanThreadPool;
import com.jeramtough.jtandroid.ioc.util.JtBeanUtil;

import java.lang.reflect.Type;
import java.util.Objects;
import java.util.concurrent.Future;

/**
 * Created on 2019-08-30 17:42
 * by @author JeramTough
 */
public class RegisterBeanContainer extends BaseBeanContainer
        implements BeanContainer {


    RegisterBeanContainer(Context applicationContext) {
        super(applicationContext);
    }


    @Override
    public void registerBean(@NonNull Object bean, JtBeanPattern jtBeanPattern) {
        Objects.requireNonNull(bean);
        switch (jtBeanPattern) {
            case Singleton:
                //如果注入bean是接口的话，要迭代它的实现类以及接口
                putSingletonBean(bean.getClass(), bean);
                for (Type type : bean.getClass().getGenericInterfaces()) {
                    Class beanClass = (Class) type;
                    putSingletonBean(beanClass, bean);
                }
                break;
            case Prototype:
                putPrototypeBean(bean.getClass(), bean);
                for (Type type : bean.getClass().getGenericInterfaces()) {
                    Class beanClass = (Class) type;
                    putPrototypeBean(beanClass, bean);
                }
                break;
            case Context:
                throw new RegisterBeanException("The bean patten can't be the Context");
            default:
        }
    }

    @Override
    public void registerBean(Class beanClass) {

        if (JtBeanUtil.isJtBean(beanClass)) {
            switch (JtBeanUtil.getJtBeanPattern(beanClass)) {
                case Singleton:
                    if (!isContainedSingletonBean(beanClass)) {
                        JtBeanUtil.checkBeanClass(beanClass);
                        Object beanInstance = new BeanInterpreterForClass(
                                beanClass).getBeanInstance();
                        putSingletonBean(beanClass, beanInstance);
                    }
                    break;
                case Prototype:
                    JtBeanUtil.checkBeanClass(beanClass);
                    Object beanInstance = new BeanInterpreterForClass(
                            beanClass).getBeanInstance();
                    putPrototypeBean(beanClass, beanInstance);
                case Context:
                default:
            }
        }
        else {
            if (getBean(beanClass) == null) {
                throw new RegisterBeanException(
                        "The bean[" + beanClass.getName() + "] has been not " +
                                "registered by { \nBeanContainer.register(Object , " +
                                "JtBeanPatten) method\n}");
            }
        }

    }


    @Override
    public Future<Class> registerBeanAsync(final Class beanClass) {
        Future<Class> classFuture = RegisterBeanThreadPool.getInstance().submit(
                new RegisterBeanThread(beanClass) {
                    @Override
                    public Class call() {
                        registerBean(beanClass);
                        return beanClass;
                    }
                });

        return classFuture;
    }

    //***************************


}
