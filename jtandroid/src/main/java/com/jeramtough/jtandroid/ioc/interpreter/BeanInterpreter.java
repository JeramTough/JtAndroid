package com.jeramtough.jtandroid.ioc.interpreter;

import android.content.Context;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.jtandroid.ioc.annotation.JtServiceImpl;
import com.jeramtough.jtandroid.ioc.bean.BeanAnnotationInfo;
import com.jeramtough.jtandroid.ioc.exception.BeanAnnotationIllegalException;
import com.jeramtough.jtandroid.ioc.exception.BeanConstructorIllegalException;
import com.jeramtough.jtandroid.ioc.log.P;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * @author 11718
 */
public class BeanInterpreter implements Interpreter {

    private Context applicationContext;
    private Class beanClass;
    private NeededParamCaller neededParamCaller;
    private BeanAnnotationInfo beanAnnotationInfo;

    private long startInstanceTime = 0;

    private Constructor beUsedConstructor;

    public BeanInterpreter(Context applicationContext, Class beanClass) {
        this.beanClass = beanClass;
        this.applicationContext = applicationContext;
        this.checkLegalityOfBean(beanClass);
    }

    @Override
    public Object getBeanInstance() {
        Object beanInstance = null;
        startInstanceTime = System.currentTimeMillis() - startInstanceTime;

        ArrayList<Object> constructorParameters = new ArrayList<>();
        for (Class<?> paramClass : beUsedConstructor.getParameterTypes()) {
            if (neededParamCaller != null) {
                if (paramClass == Context.class) {
                    constructorParameters.add(applicationContext);
                } else {
                    constructorParameters.add(neededParamCaller.getParamOfConstructor(paramClass));
                }
            }
        }

        beUsedConstructor.setAccessible(true);
        try {
            beanInstance = beUsedConstructor.newInstance(constructorParameters
                    .toArray(new Object[constructorParameters.size()]));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        long endInstanceTime = System.currentTimeMillis();
        P.info("Spend " + (endInstanceTime - startInstanceTime) + " millis second on instancing [" + beanClass.getName() + "]");
        return beanInstance;
    }

    @Override
    public BeanAnnotationInfo getBeanAnnotationInfo() {
        return beanAnnotationInfo;
    }

    public void setNeededParamCaller(NeededParamCaller neededParamCaller) {
        this.neededParamCaller = neededParamCaller;
    }

    //**********************
    private void checkLegalityOfBean(Class beanClass) {
        startInstanceTime = System.currentTimeMillis();

        JtComponent jtComponent = (JtComponent) beanClass.getAnnotation(JtComponent.class);
        if (jtComponent != null) {
            beanAnnotationInfo = new BeanAnnotationInfo();
            beanAnnotationInfo.setJtBeanPattern(jtComponent.pattern());
        }
        JtServiceImpl jtServiceImpl = (JtServiceImpl) beanClass.getAnnotation(JtServiceImpl.class);
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

    //{{{{{{{{{{}}}}}}}}}}}
    public interface NeededParamCaller {

        Object getParamOfConstructor(Class c);
    }
}
