package com.jeramtough.jtandroid.ioc.iocimpl;

import android.content.Context;

import com.jeramtough.jtandroid.ioc.BeansContainer;
import com.jeramtough.jtandroid.ioc.IocContext;
import com.jeramtough.jtandroid.ioc.annotation.JtController;
import com.jeramtough.jtandroid.ioc.bean.JtField;
import com.jeramtough.jtandroid.ioc.finder.JtFieldFinder;
import com.jeramtough.jtandroid.ioc.finder.JtFieldFinderImpl;
import com.jeramtough.jtandroid.ioc.log.P;

import java.util.ArrayList;

/**
 * @author 11718
 */
public class IocContextImpl implements IocContext {

    private volatile static JtBeansContainer beansContainer;
    private Context context;

    public IocContextImpl(Context context) {
        this.context = context;
        getBeansContainer();
    }

    @Override
    public void injectBeansInto(Object beInjectedObject) {
        long startInjectingTime = System.currentTimeMillis();

        ArrayList<JtField> jtFields = new ArrayList<>();
        JtFieldFinder jtFieldFinder = new JtFieldFinderImpl();

        //traverse all the JtController to find the JtFields.
        Class c = beInjectedObject.getClass();
        while (c.getSuperclass() != null) {
            if (c.getAnnotation(JtController.class) != null) {
                jtFields.addAll(jtFieldFinder.findJtFieldFromClass(c));
            }
            c = c.getSuperclass();
        }

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
