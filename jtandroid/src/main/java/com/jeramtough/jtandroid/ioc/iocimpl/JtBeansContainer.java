package com.jeramtough.jtandroid.ioc.iocimpl;

import android.content.Context;

import com.jeramtough.jtandroid.ioc.BeansContainer;
import com.jeramtough.jtandroid.ioc.bean.JtField;
import com.jeramtough.jtandroid.ioc.exception.FailedInstanceClassNotFoundException;
import com.jeramtough.jtandroid.ioc.interpreter.BeanInterpreter;
import com.jeramtough.jtandroid.ioc.util.IocUtil;

import java.util.HashMap;
import java.util.Map;

class JtBeansContainer implements BeansContainer, BeanInterpreter.NeededParamCaller {

    private Map<String, Object> beans;
    private Context applicationContext;

    JtBeansContainer(Context applicationContext) {
        this.applicationContext = applicationContext;
        beans = new HashMap<>();
    }


    @Override
    public synchronized <T> T getBean(Class<T> requiredBeanClass) {
        Object beanInstance;
        BeanInterpreter beanInterpreter = new BeanInterpreter(applicationContext, requiredBeanClass);
        beanInterpreter.setNeededParamCaller(this);

        if (!isContainedBean(requiredBeanClass)) {
            beanInstance = beanInterpreter.getBeanInstance();
        } else {
            beanInstance = beans.get(IocUtil.processKeyName(requiredBeanClass));
        }
        switch (beanInterpreter.getBeanAnnotationInfo().getJtBeanPattern()) {
            case Singleton:
                String beanKey = IocUtil.processKeyName(requiredBeanClass);
                beans.put(beanKey, beanInstance);
                break;
        }

        return (T) beanInstance;
    }

    @Override
    public synchronized Object getBean(JtField jtField) {
        Class c = null;
        if (!jtField.getImplClass().equals(Object.class)) {
            c = jtField.getImplClass();
        } else {
            //如果被注入的Field是个接口，那就去那个包内寻找该接口的实现
            //if the injected field is interface ,searching the implement of interface in that package.
            if (jtField.getField().getType().isInterface()) {
                try {
                    //先以默认了实现类名去选择实现类
                    c = Class.forName(IocUtil.getDefaultImplClassName(jtField.getField().getType()));
                    //如果未发现默认实现类，那就遍历该包选择实现类
                    if (c == null) {
                        // TODO: 2018-06-03  这个实现先留着，有空再写吧
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                c = jtField.getField().getType();
            }
        }

        if (c == null) {
            throw new FailedInstanceClassNotFoundException(jtField.getField());
        }

        return getBean(c);
    }

    @Override
    public boolean isContainedBean(Class c) {
        return beans.containsKey(IocUtil.processKeyName(c));
    }


    @Override
    public void replaceBean(Object object) {
        beans.put(IocUtil.processKeyName(object.getClass()), object);
    }

    @Override
    public Object getParamOfConstructor(Class c) {
        return getBean(c);
    }

}
