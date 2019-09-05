package com.jeramtough.jtandroid.ioc.interpreter;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.container.JtBeanContainer;
import com.jeramtough.jtandroid.ioc.exception.BeanConstructorIllegalException;
import com.jeramtough.jtandroid.ioc.implfinder.DefaultInterfaceImplFinder;
import com.jeramtough.jtandroid.ioc.implfinder.InterfaceImplFinder;
import com.jeramtough.jtandroid.ioc.log.IocLog;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 使用Class生成Bean实例
 *
 * @author 11718
 */
public class BeanInterpreterForClass implements BeanInterpreter {

    private Class beanClass;


    public BeanInterpreterForClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public Object getBeanInstance() {
        long startInstanceTime = System.currentTimeMillis();

        Constructor beUsedConstructor = this.parseBeUsedConstructor(beanClass);

        Object beanInstance = null;

        List<Future<Class>> beanClassFutureList = new ArrayList<>();

        final ArrayList<Object> constructorParamInstanceList = new ArrayList<>();
//        final Map<Class,Object> constructorParamInstanceList = new HashMap<>();
        for (int i = 0; i < beUsedConstructor.getParameterTypes().length; i++) {
            Class paramClass = beUsedConstructor.getParameterTypes()[i];
            Annotation[] paramAnnotations = beUsedConstructor.getParameterAnnotations()[i];

            Future<Class> future;
            if (paramClass.isInterface()) {
                InterfaceImplFinder interfaceImplFinder =
                        new DefaultInterfaceImplFinder(beanClass, paramClass,
                                paramAnnotations);
                Class paramClassImpl = interfaceImplFinder.find();
                future = JtBeanContainer.getInstance().registerBeanAsync(
                        paramClassImpl);
            }
            else {
                future = JtBeanContainer.getInstance().registerBeanAsync(
                        paramClass);
            }


            beanClassFutureList.add(future);
        }

        for (Future<Class> future : beanClassFutureList) {
            try {
                Class beanClass = future.get();
                constructorParamInstanceList.add(
                        JtBeanContainer.getInstance().getBean(beanClass));
            }
            catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        beUsedConstructor.setAccessible(true);
        try {
            beanInstance = beUsedConstructor.newInstance(constructorParamInstanceList
                    .toArray(new Object[0]));
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        long endInstanceTime = System.currentTimeMillis();
        IocLog.info(
                "Spend " + (endInstanceTime - startInstanceTime) + " millis second on instancing [" + beanClass.getName() + "]");
        return beanInstance;
    }


    //**********************

    private Constructor parseBeUsedConstructor(Class beanClass) {

        Constructor[] constructors = beanClass.getDeclaredConstructors();
        Constructor beUsedConstructor = null;
        for (Constructor constructor : constructors) {
            if (constructor.getAnnotation(IocAutowire.class) != null) {
                beUsedConstructor = constructor;
                break;
            }
            if (constructor.getParameterTypes().length == 0) {
                beUsedConstructor = constructor;
                break;
            }
        }
        if (beUsedConstructor == null) {
            throw new BeanConstructorIllegalException(beanClass);
        }

        return beUsedConstructor;

    }


}
