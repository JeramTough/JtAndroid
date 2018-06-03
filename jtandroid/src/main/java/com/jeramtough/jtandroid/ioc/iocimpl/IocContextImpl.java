package com.jeramtough.jtandroid.ioc.iocimpl;

import android.content.Context;

import com.jeramtough.jtandroid.function.JtExecutors;
import com.jeramtough.jtandroid.ioc.BeansContainer;
import com.jeramtough.jtandroid.ioc.IocContext;
import com.jeramtough.jtandroid.ioc.annotation.JtController;
import com.jeramtough.jtandroid.ioc.bean.JtField;
import com.jeramtough.jtandroid.ioc.finder.JtFieldFinder;
import com.jeramtough.jtandroid.ioc.finder.JtFieldFinderImpl;
import com.jeramtough.jtandroid.ioc.log.P;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author 11718
 */
public class IocContextImpl implements IocContext {

    private volatile static JtBeansContainer beansContainer;
    private Context context;
    private ExecutorService executorService;

    public IocContextImpl(Context context) {
        this.context = context;
        executorService = JtExecutors.newCachedThreadPool();
        getBeansContainer();
    }

    @Override
    public void injectBeansInto(Object beInjectedObject) {
        long startInjectingTime = System.currentTimeMillis();

        final ArrayList<JtField> jtFields = new ArrayList<>();
        final JtFieldFinder jtFieldFinder = new JtFieldFinderImpl();
        List<Future<List<JtField>>> futures = new ArrayList<>();
        //traverse all the JtController to find the JtFields.
        Class c = beInjectedObject.getClass();

        while (c.getSuperclass() != null) {
            final Class finalC = c;
            Future<List<JtField>> future = executorService.submit(new Callable<List<JtField>>() {
                @Override
                public List<JtField> call() {
                    if (finalC.getAnnotation(JtController.class) != null) {
                        return jtFieldFinder.findJtFieldFromClass(finalC);
                    }
                    return null;
                }
            });
            futures.add(future);
            c = c.getSuperclass();
        }

        for (Future<List<JtField>> future : futures) {
            if (future.isDone()) {
                try {
                    if (future.get() != null) {
                        jtFields.addAll(future.get());
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

        P.info(jtFields.size());


        for (JtField jtField : jtFields) {
            Object beanInstance = beansContainer.getBean(jtField);
            jtField.getField().setAccessible(true);
            try {
                jtField.getField().set(beInjectedObject, beanInstance);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        long endInjectingTime = System.currentTimeMillis();
        P.info("Speed " + (endInjectingTime - startInjectingTime)
                + " millis second on injecting "
                + jtFields.size()
                + " beans into 【" + beInjectedObject.getClass().getName() + "】");
    }

    @Override
    public BeansContainer getBeansContainer() {
        return getIocContainerInstance(context);
    }

    public static BeansContainer getIocContainerInstance(Context context) {
        if (beansContainer == null) {
            synchronized (IocContextImpl.class) {
                if (beansContainer == null) {
                    beansContainer = new JtBeansContainer(context.getApplicationContext());
                }
            }
        }
        return beansContainer;
    }
}
