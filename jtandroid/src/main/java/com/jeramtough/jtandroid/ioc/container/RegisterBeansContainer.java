package com.jeramtough.jtandroid.ioc.container;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jeramtough.jtandroid.ioc.annotation.JtBeanPattern;
import com.jeramtough.jtandroid.ioc.interpreter.BeanInterpreterForClass;
import com.jeramtough.jtandroid.ioc.thread.RegisterBeanThread;
import com.jeramtough.jtandroid.ioc.thread.RegisterBeanThreadPool;
import com.jeramtough.jtandroid.ioc.util.JtBeanUtil;

import java.util.Objects;
import java.util.concurrent.Future;

/**
 * Created on 2019-08-30 17:42
 * by @author JeramTough
 */
public class RegisterBeansContainer extends BaseBeansContainer
        implements BeansContainer {


    RegisterBeansContainer(Context applicationContext) {
        super(applicationContext);
    }


    @Override
    public void registerBean(@NonNull Object bean, JtBeanPattern jtBeanPattern) {
        Objects.requireNonNull(bean);
        switch (jtBeanPattern) {
            case Singleton:
                putSingletonBean(bean.getClass(), bean);
                break;
            case Prototype:
                putPrototypeBean(bean.getClass(), bean);
                break;
            default:
        }
    }


    @Override
    public void registerBean(Class beanClass) {

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

       /* if (!isContainedBean(beanClass)) {

            JtBeanUtil.checkBeanClass(beanClass);

            Object beanInstance = new BeanInterpreterForClass(beanClass).getBeanInstance();

            switch (JtBeanUtil.getJtBeanPattern(beanClass)) {
                case Singleton:
                    putSingletonBean(beanClass, beanInstance);
                    break;
                case Prototype:
                    putPrototypeBean(beanClass, beanInstance);
                default:
            }
        }*/
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
