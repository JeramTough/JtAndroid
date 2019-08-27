package com.jeramtough.jtandroid.ioc.exception;

/**
 * Created on 2019-08-27 15:50
 * by @author JeramTough
 */
public class RegisterBeanException extends RuntimeException {

    public RegisterBeanException() {
        super("The bean must be annotation the @JtComponent or @JtServiceImpl or " +
                "@JtController");
    }
}
