package com.jeramtough.jtandroid.ioc.container;

import android.content.Context;
import android.support.annotation.NonNull;

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
    public void replaceBean(@NonNull Object bean) {
        registerBean(bean);
    }

    @Override
    public void registerBean(@NonNull Object bean) {
        Objects.requireNonNull(bean);
        super.beansMap.put(JtBeanUtil.processKeyName(bean.getClass()), bean);
    }

    @Override
    public void registerBean(String aliasName, @NonNull Object bean) {
        Objects.requireNonNull(bean);
        super.beansMap.put(aliasName, bean);
    }


    @Override
    public void registerBean(Class beanClass) {

        if (!isContainedBean(beanClass)) {

            JtBeanUtil.checkBeanClass(beanClass);

            Object beanInstance = new BeanInterpreterForClass(beanClass).getBeanInstance();

            switch (JtBeanUtil.getJtBeanPattern(beanClass)) {
                case Singleton:
                    String beanKey = JtBeanUtil.processKeyName(beanClass);
                    super.beansMap.put(beanKey, beanInstance);
                    break;
                case Prototype:
                    //do not anything
                default:
            }
        }
    }


    @Override
    public Future<Class> registerBeanAsync(final Class beanClass) {
        Future<Class> classFuture = RegisterBeanThreadPool.getInstance().submit(
                new RegisterBeanThread(beanClass) {
                    @Override
                    public Class call() {

                        if (!isContainedBean(beanClass)) {
                            registerBean(beanClass);
                        }
                        return beanClass;
                    }
                });

        return classFuture;
    }

    //***************************


}
