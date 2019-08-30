package com.jeramtough.jtandroid.ioc.finder;

import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ioc.jtfield.DefaultJtFieldGenerator;
import com.jeramtough.jtandroid.ioc.jtfield.JtField;
import com.jeramtough.jtandroid.ioc.jtfield.JtFieldGenerator;

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
            JtFieldGenerator jtFieldGenerator = new DefaultJtFieldGenerator(field);
            if (injectComponent != null) {
                jtField = jtFieldGenerator.parse(injectComponent);
            }

            InjectService injectService = field.getAnnotation(InjectService.class);
            if (injectService != null) {
                jtField = jtFieldGenerator.parse(injectService);
            }
            if (jtField != null) {
                jtFields.add(jtField);
            }
        }
        return jtFields;
    }


}
