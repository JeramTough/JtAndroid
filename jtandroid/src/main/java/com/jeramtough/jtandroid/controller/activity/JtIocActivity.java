package com.jeramtough.jtandroid.controller.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jeramtough.jtandroid.ioc.context.IocContext;
import com.jeramtough.jtandroid.ioc.annotation.JtController;
import com.jeramtough.jtandroid.ioc.context.IocContextImpl;

/**
 * @author 11718
 * on 2017  December 07 Thursday 16:53.
 */
@JtController
public abstract class JtIocActivity extends JtBaseActivity {

    private IocContext iocContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        iocContext = new IocContextImpl(this);
        iocContext.injectBeansInto(this);
    }

    public IocContext getIocContext() {
        return iocContext;
    }


    //********************************


}
