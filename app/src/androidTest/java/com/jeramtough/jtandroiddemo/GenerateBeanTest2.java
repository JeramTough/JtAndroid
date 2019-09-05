package com.jeramtough.jtandroiddemo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.jeramtough.jtandroid.function.MusicPlayer;
import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ioc.annotation.JtBeanPattern;
import com.jeramtough.jtandroid.ioc.annotation.JtController;
import com.jeramtough.jtandroid.ioc.context.IocContext;
import com.jeramtough.jtandroid.ioc.context.IocContextImpl;
import com.jeramtough.jtandroiddemo.business.TestService;
import com.jeramtough.jtandroiddemo.business.TestServiceImpl;
import com.jeramtough.jtandroiddemo.component.A;
import com.jeramtough.jtandroiddemo.component.B;
import com.jeramtough.jtandroiddemo.component.C;
import com.jeramtough.jtandroiddemo.component.D;
import com.jeramtough.jtandroiddemo.component.E;
import com.jeramtough.jtandroiddemo.component.G;
import com.jeramtough.jtandroiddemo.component.ImplTestService;
import com.jeramtough.jtandroiddemo.component.J;
import com.jeramtough.jtandroiddemo.component.JavaObject;
import com.jeramtough.jtandroiddemo.component.JavaService;
import com.jeramtough.jtandroiddemo.component.JavaServiceImpl;
import com.jeramtough.jtandroiddemo.component.TestImplComponent;
import com.jeramtough.jtlog.facade.L;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented injectTest, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@JtController
public class GenerateBeanTest2 {

    @InjectService
    private TestService testService;
    @InjectComponent
    private MusicPlayer musicPlayer;
    @InjectComponent
    private A a;

    @InjectComponent
    private B b;

    @InjectComponent
    private B b1;

    @InjectComponent
    private B b2;

    @InjectComponent
    private C c;
    @InjectComponent
    private D d;
    @InjectComponent
    private E e;
    @InjectComponent
    private G g;

    @InjectService
    private ImplTestService implTestService;


    @InjectComponent
    private TestImplComponent testImplComponent;

    @Test
    public void injectTest() {
        // Context of the app under injectTest.
        Context appContext = InstrumentationRegistry.getTargetContext();
        IocContext iocContext = new IocContextImpl(appContext);

        iocContext.injectBeansInto(this);

        L.debugs(testService == null, musicPlayer == null, a == null, b == null,
                b1 == null, b2 == null, c == null,
                d == null, e == null,
                g == null, g.f == null, implTestService == null,testImplComponent==null);
    }

    @Test
    public void injectCustomObject() {
        class Custom {

        }
        Custom custom1 = new Custom();
        Custom custom2 = new Custom();

        Context appContext = InstrumentationRegistry.getTargetContext();
        IocContext iocContext = new IocContextImpl(appContext);
        iocContext.getBeanContainer().registerBean(custom1, JtBeanPattern.Singleton);
        L.debugs(iocContext.getBeanContainer().getBean(Custom.class) == null);


        //replace test
        L.debugs(iocContext.getBeanContainer().getBean(Custom.class) == custom2,
                iocContext.getBeanContainer().getBean(Custom.class) == custom1);
        iocContext.getBeanContainer().replaceBean(custom2);
        L.debugs(iocContext.getBeanContainer().getBean(Custom.class) == custom2,
                iocContext.getBeanContainer().getBean(Custom.class) == custom1);


    }

    @Test
    public void injectInterfaceTest() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        TestService testService = new TestServiceImpl(appContext, null);

        IocContext iocContext = new IocContextImpl(appContext);
        iocContext.getBeanContainer().registerBean(testService, JtBeanPattern.Singleton);

        L.debugs(iocContext.getBeanContainer().getBean(TestService.class) == null);
    }

    @Test
    public void injectInterfaceTest2() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        IocContext iocContext = new IocContextImpl(appContext);

        JavaService javaService = new JavaServiceImpl();
        JavaObject javaObject = new JavaObject();

        iocContext.getBeanContainer().registerBean(javaObject, JtBeanPattern.Singleton);
        iocContext.getBeanContainer().registerBean(javaService, JtBeanPattern.Singleton);

        iocContext.getBeanContainer().registerBean(J.class);
        L.debugs(iocContext.getBeanContainer().getBean(J.class) == null);
    }
}
