package com.jeramtough.jtandroiddemo.component;

import android.content.Context;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;

@JtComponent
public class C {
    private A a;
    private B b;
    private Context context;

    @IocAutowire
    public C(A a, B b, Context context) {
        this.a = a;
        this.b = b;
        this.context = context;
    }
}
