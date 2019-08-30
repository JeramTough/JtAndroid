package com.jeramtough.jtandroid.ioc.jtfield;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author 11718
 * on 2017  December 07 Thursday 14:19.
 */

public class JtField {

    private Field field;

    /**
     * 如果field是个接口，implClass就是接口的实现Class，如果fidld是个普通类，那就是它本生Class
     */
    private Class implClass;

    JtField(Field field, Class implClass) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JtField jtField = (JtField) o;
        return Objects.equals(field, jtField.field) &&
                Objects.equals(implClass, jtField.implClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, implClass);
    }
}
