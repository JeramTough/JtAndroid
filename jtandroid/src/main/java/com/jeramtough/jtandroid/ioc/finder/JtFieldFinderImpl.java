package com.jeramtough.jtandroid.ioc.finder;

import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ioc.bean.JtField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 11718
 */
public class JtFieldFinderImpl implements JtFieldFinder {

    @Override
    public List<JtField> findJtFieldFromClass(Class c) {
        List<JtField> jtFields = new ArrayList<>();

        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            InjectComponent injectComponent = field.getAnnotation(InjectComponent.class);
            JtField jtField = null;
            if (injectComponent != null) {
                jtField = new JtField(field, injectComponent.impl());
            }
            InjectService injectService = field.getAnnotation(InjectService.class);
            if (injectService != null) {
                jtField = new JtField(field, injectService.impl());
            }
            if (jtField != null) {
                jtFields.add(jtField);
            }
        }
        return jtFields;
    }
}
