package com.jeramtough.jtandroid.ioc.container;

import android.app.Application;
import android.content.Context;

import com.jeramtough.jtandroid.ioc.util.IocUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2019-08-30 17:44
 * by @author JeramTough
 */
abstract class BaseBeansContainer implements BeansContainer {

    Map<String, Object> beansMap;
    Context applicationContext;

    public BaseBeansContainer(Context applicationContext) {
        this.applicationContext = applicationContext;
        beansMap = new HashMap<>();
        beansMap.put(IocUtil.processKeyName(Application.class), applicationContext);
        beansMap.put(IocUtil.processKeyName(Context.class), applicationContext);
    }

    @Override
    public <T> T getBean(Class<T> beanClass) {

        if (isContainedBean(beanClass)) {
            return (T) beansMap.get(IocUtil.processKeyName(beanClass));
        }
        return null;
    }

    @Override
    public boolean isContainedBean(Class c) {
        return beansMap.containsKey(IocUtil.processKeyName(c));
    }


}
