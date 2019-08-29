package com.jeramtough.jtandroid.ioc.thread;

import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.exception.BeanAnnotationIllegalException;

import java.lang.annotation.Annotation;
import java.util.concurrent.Callable;

/**
 * Created on 2019-08-29 20:11
 * by @author JeramTough
 */
public abstract class GenerateBeanThread implements Callable<Object> {

    private Class beanClass;
    private Annotation[] annotations;

    public GenerateBeanThread(Class beanClass, Annotation[] annotations) {
        this.beanClass = beanClass;
        this.annotations = annotations;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public Annotation[] getAnnotations() {
        return annotations;
    }

    public Class getBeanImplClass() {

        Class beanImplClass = Object.class;

        for (Annotation annotation : annotations) {
            if (annotation instanceof InjectComponent) {
                beanImplClass = ((InjectComponent) annotation).impl();
                break;
            }
        }

        if (beanImplClass == Object.class) {
            throw new BeanAnnotationIllegalException(beanClass);
        }

        return beanImplClass;
    }

}