package com.jeramtough.jtandroid.ioc.exception;

/**
 * @author 11718
 */
public class BeanAnnotationIllegalException extends RuntimeException {

    public BeanAnnotationIllegalException(Class beanClass) {
        super("The bean[" + beanClass.getName()
                + "] without the JtComponent annotation or" +
                " the JtServiceImpl annotation with ImplementClass");
    }
}
