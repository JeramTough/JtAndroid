package com.jeramtough.jtandroid.ioc.implfinder;

import android.support.annotation.NonNull;

import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ioc.exception.DontFindInterfaceImplException;
import com.jeramtough.jtandroid.ioc.util.JtBeanUtil;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * Created on 2019-08-31 02:57
 * by @author JeramTough
 */
public class DefaultInterfaceImplFinder implements InterfaceImplFinder {

    /**
     * it is bean class or JtController class
     */
    private Class mainClass;

    private Class interfaceClass;
    private Annotation[] interfaceInstanceAnnotations;

    public DefaultInterfaceImplFinder(Class mainClass, Class interfaceClass,
                                      Annotation[] interfaceInstanceAnnotations) {
        this.mainClass = mainClass;
        this.interfaceClass = interfaceClass;
        this.interfaceInstanceAnnotations = interfaceInstanceAnnotations;
    }

    @Override
    public Class find() {
        for (Annotation paramAnnotation : interfaceInstanceAnnotations) {
            if (paramAnnotation instanceof InjectComponent) {
                InjectComponent injectComponent = (InjectComponent) paramAnnotation;
                return find(injectComponent);
            }

            if (paramAnnotation instanceof InjectService) {
                InjectService injectService = (InjectService) paramAnnotation;
                return find(injectService);
            }
        }

        throw new DontFindInterfaceImplException(
                String.format("The instance of interface[%s] don't have " +
                                "@InjectComponent or @InjectService in [%s]",
                        interfaceClass.getName(), mainClass.getName()));
    }


    @Override
    public Class find(@NonNull InjectComponent injectComponent) {
        Objects.requireNonNull(injectComponent);
        Class beanImplClass = injectComponent.impl();
        if (beanImplClass == Object.class) {
            String errorMessage = String.format("Don't find implement of interface[%] " +
                            "or the impl param of @InjectComponent is Object.class in [%s]",
                    interfaceClass.getName(), mainClass.getName());
            throw new DontFindInterfaceImplException(errorMessage);
        }
        return beanImplClass;
    }

    @Override
    public Class find(@NonNull InjectService injectService) {
        Objects.requireNonNull(injectService);
        Class beanImplClass = injectService.impl();
        if (beanImplClass == Object.class) {
            try {
                //先以默认了实现类名去选择实现类
                beanImplClass = Class.forName(
                        JtBeanUtil.getDefaultImplClassName(interfaceClass));
                //如果未发现默认实现类，那就遍历该包选择实现类
                if (beanImplClass == null) {
                    // TODO: 2018-06-03  这个实现先留着，有空再写吧
                }
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return beanImplClass;
    }


}
