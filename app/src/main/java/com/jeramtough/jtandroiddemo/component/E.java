package com.jeramtough.jtandroiddemo.component;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;

@JtComponent
public class E {

    private A a;
    private D d;
    private B b;

    @IocAutowire
    public E(A a, D d, B b) {
        this.a = a;
        this.d = d;
        this.b = b;
    }
}
