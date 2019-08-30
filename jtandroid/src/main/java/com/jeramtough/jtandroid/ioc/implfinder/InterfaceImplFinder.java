package com.jeramtough.jtandroid.ioc.implfinder;

import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;

/**
 * Created on 2019-08-31 02:55
 * by @author JeramTough
 */
public interface InterfaceImplFinder {

    Class find();

    Class find(InjectComponent injectComponent);

    Class find(InjectService injectService);

}
