package com.jeramtough.jtandroiddemo.component;

import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;

/**
 * Created on 2019-08-29 17:48
 * by @author JeramTough
 */
@JtComponent
public class G {

    private E e;

    public F f;

    @IocAutowire
    public G(E e,
             @InjectComponent(impl = FImpl.class) F f) {
        this.e = e;
        this.f = f;
    }
}
