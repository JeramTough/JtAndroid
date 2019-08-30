package com.jeramtough.jtandroid.ioc.jtfield;

import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ioc.exception.FailedInstanceClassNotFoundException;
import com.jeramtough.jtandroid.ioc.util.IocUtil;

import java.lang.reflect.Field;

/**
 * Created on 2019-08-30 20:02
 * by @author JeramTough
 */
public class DefaultJtFieldGenerator implements JtFieldGenerator {

    private Field field;

    public DefaultJtFieldGenerator(Field field) {
        this.field = field;
    }

    @Override
    public JtField parse(InjectComponent injectComponent) {
        return parseBeanImplClass(injectComponent.impl());
    }

    @Override
    public JtField parse(InjectService injectService) {
        return parseBeanImplClass(injectService.impl());
    }

    //****************************

    private JtField parseBeanImplClass(Class defaultImplClass) {
        Class beanImplClass = null;
        if (!defaultImplClass.equals(Object.class)) {
            beanImplClass = defaultImplClass;
        }
        else {
            //如果被注入的Field是个接口，那就去那个包内寻找该接口的实现
            //if the injected field is interface ,searching the implement of interface in that package.
            if (field.getType().isInterface()) {
                try {
                    //先以默认了实现类名去选择实现类
                    beanImplClass = Class.forName(
                            IocUtil.getDefaultImplClassName(field.getType()));
                    //如果未发现默认实现类，那就遍历该包选择实现类
                    if (beanImplClass == null) {
                        // TODO: 2018-06-03  这个实现先留着，有空再写吧
                    }
                }
                catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else {
                beanImplClass = field.getType();
            }
        }

        if (beanImplClass == null) {
            throw new FailedInstanceClassNotFoundException(field);
        }

        return new JtField(field, beanImplClass);
    }
}
