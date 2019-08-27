package com.jeramtough.jtandroid.ioc;

/**
 * @author 11718
 */
public interface IocContext {

    /**
     * 将容器里的bean注入到对象里
     *
     * @param injectedObject 被注入bean的对象
     */
    void injectBeansInto(Object injectedObject);

    /**
     * 返回Bean容器
     */
    BeansContainer getBeansContainer();
}
