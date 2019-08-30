package com.jeramtough.jtandroiddemo.component;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;

/**
 * Created on 2019-08-30 14:56
 * by @author JeramTough
 */
@JtComponent
public class H {

    @IocAutowire
    public H() {
        try {
            Thread.sleep(1);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
