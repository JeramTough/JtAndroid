package com.jeramtough.jtandroid.ioc.container;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.jeramtough.jtandroid.ioc.util.JtBeanUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

/**
 * Created on 2019-08-30 17:44
 * by @author JeramTough
 */
abstract class BaseBeansContainer implements BeansContainer {

    private Map<String, Object> singletonBeansMap;
    private Map<String, LinkedList<Object>> prototypeBeansMap;
    Context applicationContext;

    BaseBeansContainer(Context applicationContext) {
        this.applicationContext = applicationContext;
        singletonBeansMap = new HashMap<>(32);
        prototypeBeansMap = new HashMap<>(16);
        singletonBeansMap.put(JtBeanUtil.processKeyName(Application.class),
                applicationContext);
        singletonBeansMap.put(JtBeanUtil.processKeyName(Context.class), applicationContext);
    }

    @Override
    public <T> T getBean(Class<T> beanClass) {

        if (JtBeanUtil.isJtBean(beanClass)) {
            switch (JtBeanUtil.getJtBeanPattern(beanClass)) {
                case Singleton:
                    return (T) getSingletonBean(beanClass);
                case Prototype:
                    return (T) getPrototypeBean(beanClass);
                default:

            }
        }
        else {
            Object bean = getSingletonBean(beanClass);
            if (bean == null) {
                return (T) getPrototypeBean(beanClass);
            }
            return (T) bean;
        }

        return null;
    }

    @Override
    public boolean isContainedBean(Class c) {
        boolean isContained;
        isContained = isContainedSingletonBean(c);
        if (!isContained) {
            isContained = isContainedPrototypeBean(c);
        }
        return isContained;
    }

    @Override
    public boolean isContainedSingletonBean(Class c) {
        return singletonBeansMap.containsKey(JtBeanUtil.processKeyName(c));
    }

    @Override
    public boolean isContainedPrototypeBean(Class c) {
        return prototypeBeansMap.containsKey(JtBeanUtil.processKeyName(c));
    }

    @Override
    public void replaceBean(@NonNull Object bean) {
        Objects.requireNonNull(bean);

        if (isContainedSingletonBean(bean.getClass())) {
            putSingletonBean(bean.getClass(), bean);
        }
        else {
            throw new IllegalArgumentException("The replaced bean must be a single mode bean");
        }
    }


    void putSingletonBean(Class beanClass, Object beanInstance) {
        singletonBeansMap.put(JtBeanUtil.processKeyName(beanClass), beanInstance);
    }

    void putPrototypeBean(Class beanClass, Object beanInstance) {
        LinkedList<Object> beanList;
        String beanName = JtBeanUtil.processKeyName(beanClass);
        beanList = prototypeBeansMap.get(beanName);
        if (beanList == null) {
            beanList = new LinkedList<>();
        }
        beanList.add(beanInstance);
        prototypeBeansMap.put(beanName, beanList);
    }

    Object getSingletonBean(Class beanClass) {
        return singletonBeansMap.get(JtBeanUtil.processKeyName(beanClass));
    }

    Object getPrototypeBean(Class beanClass) {
        String beanName = JtBeanUtil.processKeyName(beanClass);
        LinkedList<Object> beanList =
                prototypeBeansMap.get(beanName);
        if (beanList != null) {
            Object bean = beanList.removeFirst();
            if (beanList.size() == 0) {
                prototypeBeansMap.remove(beanName);
            }
            return bean;
        }
        return null;
    }

}
