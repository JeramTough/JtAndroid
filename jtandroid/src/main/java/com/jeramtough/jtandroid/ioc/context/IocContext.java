package com.jeramtough.jtandroid.ioc.context;

import com.jeramtough.jtandroid.ioc.container.BeanContainer;

/**
 * IocContext实例为每个JtController都会持有一个；
 *负责收集JtField和将JtField的实例注入回去。
 *
 * @author 11718
 */
public interface IocContext {

    /**
     * 将容器里的bean注入到对象里
     *
     * @param jtControllerObject 被注入bean的JtController对象
     */
    void injectBeansInto(Object jtControllerObject);

    /**
     * 返回Bean容器
     */
    BeanContainer getBeanContainer();
}
