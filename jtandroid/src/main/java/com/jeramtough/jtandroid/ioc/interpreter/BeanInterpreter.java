package com.jeramtough.jtandroid.ioc.interpreter;

import android.content.Context;

import com.jeramtough.jtandroid.function.JtExecutors;
import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.jtandroid.ioc.annotation.JtServiceImpl;
import com.jeramtough.jtandroid.ioc.bean.BeanAnnotationInfo;
import com.jeramtough.jtandroid.ioc.exception.BeanAnnotationIllegalException;
import com.jeramtough.jtandroid.ioc.exception.BeanConstructorIllegalException;
import com.jeramtough.jtandroid.ioc.log.IocLog;
import com.jeramtough.jtandroid.ioc.thread.GenerateBeanThread;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author 11718
 */
public class BeanInterpreter implements Interpreter {

    private Context applicationContext;
    private Class beanClass;
    private NeededParamCaller neededParamCaller;
    private BeanAnnotationInfo beanAnnotationInfo;

    private ExecutorService executorService;

    private long startInstanceTime = 0;

    private Constructor beUsedConstructor;

    public BeanInterpreter(Context applicationContext, Class beanClass) {
        this.beanClass = beanClass;
        this.applicationContext = applicationContext;
        this.checkLegalityOfBean(beanClass);

        executorService = JtExecutors.newCachedThreadPool();
    }

    @Override
    public Object getBeanInstance() {
        Object beanInstance = null;
        startInstanceTime = System.currentTimeMillis() - startInstanceTime;

        List<Future<Object>> beanInstancesFutures = new ArrayList<>();

        final ArrayList<Object> constructorParameters = new ArrayList<>();
        for (int i = 0; i < beUsedConstructor.getParameterTypes().length; i++) {
            Class paramClass=beUsedConstructor.getParameterTypes()[i];
            Annotation[] paramAnnotations=beUsedConstructor.getParameterAnnotations()[i];
            Future<Object> future=executorService.submit(new GenerateBeanThread(paramClass,
                    paramAnnotations) {
                @Override
                public Object call() {
                    Object beanInstance = null;
                    if (neededParamCaller != null) {
                        if (paramClass == Context.class) {
                            beanInstance = applicationContext;
                        }
                        else if (paramClass.isInterface()) {
                            beanInstance = neededParamCaller.getParamOfConstructor(getBeanImplClass());
                        }
                        else {
                            beanInstance = neededParamCaller.getParamOfConstructor(paramClass);
                        }
                    }
                    return beanInstance;
                }
            });
            beanInstancesFutures.add(future);
        }
        /*for (final Class<?> paramClass : beUsedConstructor.getParameterTypes()) {
            Future<Object> future = executorService.submit(new Callable<Object>() {
                @Override
                public Object call() {
                    Object beanInstance = null;
                    if (neededParamCaller != null) {
                        if (paramClass == Context.class) {
                            beanInstance = applicationContext;
                        }
                        else if (paramClass.isInterface()) {
                            InjectComponent injectComponentAnnotation =
                                    paramClass.getAnnotation(InjectComponent.class);
                            if (injectComponentAnnotation == null ||
                                    injectComponentAnnotation.impl() == Object.class) {
                                throw new BeanAnnotationIllegalException(paramClass);
                            }
                        }
                        else {
                            beanInstance = neededParamCaller.getParamOfConstructor(paramClass);
                        }
                    }
                    return beanInstance;
                }
            });
            beanInstancesFutures.add(future);
        }*/

        for (Future<Object> future : beanInstancesFutures) {
            try {
                constructorParameters.add(future.get());
            }
            catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        beUsedConstructor.setAccessible(true);
        try {
            beanInstance = beUsedConstructor.newInstance(constructorParameters
                    .toArray(new Object[constructorParameters.size()]));
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

    //{{{{{{{{{{}}}}}}}}}}}
    public interface NeededParamCaller {

        Object getParamOfConstructor(Class c);
    }
}
