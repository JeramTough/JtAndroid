package com.jeramtough.jtandroid.ioc.exception;

import java.lang.reflect.Field;

public class FailedInstanceClassNotFoundException extends RuntimeException {

    public FailedInstanceClassNotFoundException(Field field) {
        super("Don't find the instance class of [ "+field.getType().getName()+" ]");
    }
}
