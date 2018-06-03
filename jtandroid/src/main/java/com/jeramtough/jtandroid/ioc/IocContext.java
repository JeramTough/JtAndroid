package com.jeramtough.jtandroid.ioc;

/**
 * @author 11718
 */
public interface IocContext {

    void injectBeansInto(Object injectedObject);

    BeansContainer getBeansContainer();
}
