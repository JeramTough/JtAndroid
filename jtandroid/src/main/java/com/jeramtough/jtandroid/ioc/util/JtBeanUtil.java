package com.jeramtough.jtandroid.ioc.util;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.jeramtough.jtandroid.ioc.annotation.JtBeanPattern;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.jtandroid.ioc.annotation.JtController;
import com.jeramtough.jtandroid.ioc.annotation.JtServiceImpl;
import com.jeramtough.jtandroid.ioc.exception.RegisterBeanException;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * @author 11718
 * on 2017  December 07 Thursday 01:46.
 */

public class JtBeanUtil {

    public static boolean isJtBean(Object o) {
        return isJtBean(o.getClass());
    }

    public static boolean isJtBean(Class beanClass) {
        Annotation[] annotations = beanClass.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof JtComponent
                    || annotation instanceof JtController
                    || annotation instanceof JtServiceImpl) {
                return true;
            }
        }
        return false;
    }

    public static void checkBeanClass(Class beanClass) {

        if (beanClass.isInterface()) {
            throw new RegisterBeanException(String.format("The bean[%s] can't be a class of " +
                    "interface", beanClass.getName()));
        }

        if (!JtBeanUtil.isJtBean(beanClass)) {
            throw new RegisterBeanException(
                    String.format(
                            "The bean[%s] must be annotation the @JtComponent or @JtServiceImpl " +
                                    "or @JtController", beanClass.getName()));
        }
    }

    public static String processKeyName(Class c) {
        String fieldName = c.getName();
        return fieldName;
    }

    public static @NonNull
    JtBeanPattern getJtBeanPattern(Class beanClass) {
        JtBeanPattern jtBeanPattern = null;

        JtComponent jtComponent = (JtComponent) beanClass.getAnnotation(JtComponent.class);
        if (jtComponent != null) {
            jtBeanPattern = jtComponent.pattern();
        }

        JtServiceImpl jtServiceImpl = (JtServiceImpl) beanClass.getAnnotation(
                JtServiceImpl.class);
        if (jtServiceImpl != null) {
            jtBeanPattern = jtServiceImpl.pattern();
        }

        if (beanClass == Application.class || beanClass == Context.class) {
            jtBeanPattern = JtBeanPattern.Context;
        }
        if (jtBeanPattern == null) {
            Class tempClass = beanClass;
            while (tempClass.getSuperclass() != null) {
                tempClass = tempClass.getSuperclass();
                if (tempClass == Application.class || tempClass == Context.class) {
                    jtBeanPattern = JtBeanPattern.Context;
                    break;
                }
            }
        }


        Objects.requireNonNull(jtBeanPattern);
        return jtBeanPattern;
    }

    public static String getDefaultImplClassName(Class serviceClass) {
        String className = serviceClass.getName() + "Impl";
        return className;
    }

    public static String getDefaultImplClassName1(Class serviceClass) {
        String className = serviceClass.getPackage().getName()
                + ".impl." + serviceClass.getSimpleName() +
                "Impl";
        return className;
    }


    public static String getDefaultComponentClassName(Class interfaceClass) {
        String className = interfaceClass.getPackage().getName()
                + ".Default" + interfaceClass.getSimpleName();
        return className;
    }
}