package com.jeramtough.jtandroid.ioc.exception;

/**
 * Created on 2019-08-30 20:48
 * by @author JeramTough
 */
public class DontInitException extends RuntimeException {

    public DontInitException() {
        super("The BeanContainer don't init");
    }
}
