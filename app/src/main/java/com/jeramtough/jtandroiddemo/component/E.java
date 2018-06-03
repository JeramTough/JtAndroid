package com.jeramtough.jtandroiddemo.component;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;

@JtComponent
public class E {

    private A a;
    private D d;

    @IocAutowire
    public E(A a, D d) {
        this.a = a;
        this.d = d;
    }
}
