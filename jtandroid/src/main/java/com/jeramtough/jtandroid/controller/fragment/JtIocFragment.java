package com.jeramtough.jtandroid.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jeramtough.jtandroid.ioc.context.IocContext;
import com.jeramtough.jtandroid.ioc.annotation.JtController;
import com.jeramtough.jtandroid.ioc.context.IocContextImpl;

/**
 * @author 11718
 * on 2017  December 09 Saturday 20:03.
 */
@JtController
public abstract class JtIocFragment extends JtBaseFragment {

    private IocContext iocContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        iocContext = new IocContextImpl(getContext());
        iocContext.injectBeansInto(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public IocContext getIocContext() {
        return iocContext;
    }
}
