package com.jeramtough.jtandroiddemo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.jeramtough.jtandroid.ioc.context.IocContext;
import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.JtController;
import com.jeramtough.jtandroid.ioc.context.IocContextImpl;
import com.jeramtough.jtandroiddemo.component.H;
import com.jeramtough.jtlog.facade.L;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@JtController
public class GenerateBeanTest {
    @InjectComponent
    private H a;

    @InjectComponent
    private H aa;

    @Test
    public void testTheSameInjected() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        IocContext iocContext = new IocContextImpl(appContext);

        iocContext.injectBeansInto(this);

        L.arrive();
        L.debugs(a==null,aa==null);
    }
}
