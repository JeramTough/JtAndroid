package com.jeramtough.jtandroid.ioc.container;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jeramtough.jtandroid.ioc.interpreter.BeanInterpreter2;
import com.jeramtough.jtandroid.ioc.interpreter.Interpreter;
import com.jeramtough.jtandroid.ioc.thread.RegisterBeanThread;
import com.jeramtough.jtandroid.ioc.thread.RegisterBeanThreadPool;
import com.jeramtough.jtandroid.ioc.util.IocUtil;

import java.util.Objects;
import java.util.concurrent.Future;

/**
 * Created on 2019-08-30 17:42
 * by @author JeramTough
 */
public class RegisterBeansContainer extends BaseBeansContainer
        implements BeansContainer {


    public RegisterBeansContainer(Context applicationContext) {
        super(applicationContext);
    }

    @Override
    public void replaceBean(@NonNull Object bean) {
        registerBean(bean);
    }

    @Override
    public void registerBean(@NonNull Object bean) {
        Objects.requireNonNull(bean);
        super.beansMap.put(IocUtil.processKeyName(bean.getClass()), bean);
    }

    @Override
    public void registerBean(String aliasName, @NonNull Object bean) {
        Objects.requireNonNull(bean);
        super.beansMap.put(aliasName, bean);
    }


    @Override
    public void registerBean(Class beanClass) {

        if (!isContainedBean(beanClass)) {

            if (beanClass.isInterface()) {
                throw new IllegalArgumentException("beanClass can't be a class of interface");
            }

            Interpreter beanInterpreter = new BeanInterpreter2(applicationContext,
                    beanClass);
            Object beanInstance = beanInterpreter.getBeanInstance();

            switch (beanInterpreter.getBeanAnnotationInfo().getJtBeanPattern()) {
                case Singleton:
                    String beanKey = IocUtil.processKeyName(beanClass);
                    super.beansMap.put(beanKey, beanInstance);
                    break;
                case Prototype:
                    //do not anything
                default:
            }
        }
    }


    @Override
    public Future<Class> registerBeanAsync(Class beanClass) {
        Future<Class> classFuture = RegisterBeanThreadPool.getInstance().submit(
                new RegisterBeanThread(beanClass) {
                    @Override
                    public Class call() throws Exception {
                        if (!isContainedBean(beanClass)) {
                            registerBean(beanClass);
                        }
                        return beanClass;
                    }
                });

        return classFuture;
    }

}
