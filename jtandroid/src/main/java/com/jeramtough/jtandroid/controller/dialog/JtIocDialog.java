package com.jeramtough.jtandroid.controller.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jeramtough.jtandroid.ioc.context.IocContext;
import com.jeramtough.jtandroid.ioc.annotation.JtController;
import com.jeramtough.jtandroid.ioc.context.IocContextImpl;

/**
 * @author 11718
 * on 2017  December 11 Monday 13:07.
 */
@JtController
public class JtIocDialog extends Dialog {
    private IocContext iocContext;

    public JtIocDialog(@NonNull Context context) {
        super(context);
        iocContext = new IocContextImpl(context);
        iocContext.injectBeansInto(this);
    }

    public JtIocDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        iocContext = new IocContextImpl(context);
        iocContext.injectBeansInto(this);

    }

    public JtIocDialog(@NonNull Context context, boolean cancelable,
                       @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        iocContext = new IocContextImpl(context);
        iocContext.injectBeansInto(this);
    }

    public IocContext getIocContext() {
        return iocContext;
    }
}
