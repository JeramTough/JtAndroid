package com.jeramtough.jtandroid.ioc.interpreter;

import android.content.Context;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.jtandroid.ioc.annotation.JtServiceImpl;
import com.jeramtough.jtandroid.ioc.bean.BeanAnnotationInfo;
import com.jeramtough.jtandroid.ioc.container.JtBeansContainer;
import com.jeramtough.jtandroid.ioc.exception.BeanAnnotationIllegalException;
import com.jeramtough.jtandroid.ioc.exception.BeanConstructorIllegalException;
import com.jeramtough.jtandroid.ioc.log.IocLog;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author 11718
 */
public class BeanInterpreter2 implements Interpreter {

    private Context applicationContext;
    private Class beanClass;
    private BeanAnnotationInfo beanAnnotationInfo;

    private long startInstanceTime = 0;

    private Constructor beUsedConstructor;

    public BeanInterpreter2(Context applicationContext, Class beanClass) {
        this.beanClass = beanClass;
        this.applicationContext = applicationContext;
        this.checkLegalityOfBean(beanClass);

    }

    @Override
    public Object getBeanInstance() {
        Object beanInstance = null;
        startInstanceTime = System.currentTimeMillis() - startInstanceTime;

        List<Future<Class>> beanClassFutureList = new ArrayList<>();

        final ArrayList<Object> constructorParamInstanceList = new ArrayList<>();
        for (int i = 0; i < beUsedConstructor.getParameterTypes().length; i++) {
            Class paramClass = beUsedConstructor.getParameterTypes()[i];
//            Annotation[] paramAnnotations = beUsedConstructor.getParameterAnnotations()[i];
            Future<Class> future = JtBeansContainer.getInstance().registerBeanAsync(
                    paramClass);
            if (future != null) {
                beanClassFutureList.add(future);
            }
        }

        for (Future<Class> future : beanClassFutureList) {
            try {
                Class beanClass = future.get();
                constructorParamInstanceList.add(
                        JtBeansContainer.getInstance().getBean(beanClass));
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

    @Override
    public BeanAnnotationInfo getBeanAnnotationInfo() {
        return beanAnnotationInfo;
    }


    //**********************

    private void checkLegalityOfBean(Class beanClass) {
        startInstanceTime = System.currentTimeMillis();

        JtComponent jtComponent = (JtComponent) beanClass.getAnnotation(JtComponent.class);
        if (jtComponent != null) {
            beanAnnotationInfo = new BeanAnnotationInfo();
            beanAnnotationInfo.setJtBeanPattern(jtComponent.pattern());
        }
        JtServiceImpl jtServiceImpl = (JtServiceImpl) beanClass.getAnnotation(
                JtServiceImpl.class);
        if (jtServiceImpl != null) {
            beanAnnotationInfo = new BeanAnnotationInfo();
            beanAnnotationInfo.setJtBeanPattern(jtServiceImpl.pattern());
        }
        if (beanAnnotationInfo == null) {
            throw new BeanAnnotationIllegalException(beanClass);
        }

        Constructor[] constructors = beanClass.getDeclaredConstructors();

        for (Constructor constructor : constructors) {
            if (constructor.getAnnotation(IocAutowire.class) != null) {
                this.beUsedConstructor = constructor;
                break;
            }
            if (constructor.getParameterTypes().length == 0) {
                this.beUsedConstructor = constructor;
                break;
            }
        }
        if (beUsedConstructor == null) {
            throw new BeanConstructorIllegalException(beanClass);
        }

        startInstanceTime = System.currentTimeMillis() - startInstanceTime;
    }

}
