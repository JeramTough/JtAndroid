package com.jeramtough.jtandroid.ioc.exception;

/**
 * @author 11718
 */
public class BeanConstructorIllegalException extends RuntimeException {

    public BeanConstructorIllegalException(Class beanClass) {
        super("The count of params of constructor of bean["+beanClass.getName()+"] greater than 0 " +
                ",but the bean constructor without the IocAutowire annotation");
    }
}
