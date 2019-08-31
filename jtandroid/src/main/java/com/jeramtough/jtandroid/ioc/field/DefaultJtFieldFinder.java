package com.jeramtough.jtandroid.ioc.field;

import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ioc.implfinder.DefaultInterfaceImplFinder;
import com.jeramtough.jtandroid.ioc.implfinder.InterfaceImplFinder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019-08-31 13:48
 * by @author JeramTough
 */
public class DefaultJtFieldFinder implements JtFieldFinder {

    @Override
    public List<JtField> findJtFieldsFromJtControllerClass(Class jtControllerClass) {
        List<JtField> jtFields = new ArrayList<>();

        Field[] fields = jtControllerClass.getDeclaredFields();
        for (Field field : fields) {
            if (isInjectedField(field)) {
                JtField jtField = new JtField();
                if (field.getType().isInterface()) {
                    Class interfaceClass = field.getType();
                    InterfaceImplFinder interfaceImplFinder =
                            new DefaultInterfaceImplFinder(jtControllerClass, interfaceClass,
                                    field.getDeclaredAnnotations());
                    jtField.setField(field);
                    jtField.setImplClass(interfaceImplFinder.find());
                }
                else {
                    jtField.setField(field);
                    jtField.setImplClass(field.getType());
                }
                jtFields.add(jtField);
            }
        }
        return jtFields;
    }

    //**********************************************************

    private boolean isInjectedField(Field field) {
        InjectComponent injectComponent = field.getAnnotation(InjectComponent.class);
        if (injectComponent != null) {
            return true;
        }
        InjectService injectService = field.getAnnotation(InjectService.class);
        if (injectService != null) {
            return true;
        }
        return false;
    }
}
