package com.jeramtough.jtandroiddemo.component;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;

/**
 * Created on 2019-09-01 02:59
 * by @author JeramTough
 */
@JtComponent
public class J {

    private JavaObject javaObject;
    private JavaService javaService;

    @IocAutowire
    public J(JavaObject javaObject, JavaService javaService) {
        this.javaObject = javaObject;
        this.javaService = javaService;
    }
}
