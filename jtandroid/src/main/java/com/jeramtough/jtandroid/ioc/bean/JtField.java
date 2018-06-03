package com.jeramtough.jtandroid.ioc.bean;

import java.lang.reflect.Field;

/**
 * @author 11718
 * on 2017  December 07 Thursday 14:19.
 */

public class JtField {

    private Field field;
    private Class implClass;

    public JtField(Field field, Class implClass) {
        this.field = field;
        this.implClass = implClass;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Class getImplClass() {
        return implClass;
    }

    public void setImplClass(Class implClass) {
        this.implClass = implClass;
    }

}
