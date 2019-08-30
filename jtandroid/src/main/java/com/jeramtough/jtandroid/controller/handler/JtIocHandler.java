package com.jeramtough.jtandroid.controller.handler;

import android.support.v7.app.AppCompatActivity;

import com.jeramtough.jtandroid.ioc.context.IocContext;
import com.jeramtough.jtandroid.ioc.annotation.JtController;
import com.jeramtough.jtandroid.ioc.context.IocContextImpl;

/**
 * @author 11718
 * on 2017  December 07 Thursday 17:24.
 */
@JtController
public class JtIocHandler extends JtBaseHandler {

    private IocContext iocContext;

    public JtIocHandler(AppCompatActivity activity) {
        super(activity);
        iocContext = new IocContextImpl(activity);
        iocContext.injectBeansInto(this);
    }

    public IocContext getIocContext() {
        return iocContext;
    }
}
