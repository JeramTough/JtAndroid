package com.jeramtough.jtandroid.ioc.container;

import android.content.Context;

import com.jeramtough.jtandroid.ioc.exception.DontInitException;

/**
 * 2019-8-30
 *
 * @author 11718
 */
public class JtBeanContainer extends RegisterBeanContainer implements BeanContainer {

    private volatile static JtBeanContainer jtBeansContainer;


    private JtBeanContainer(Context applicationContext) {
        super(applicationContext);
    }

    public static void init(Context applicationContext) {
        if (jtBeansContainer == null) {
            synchronized (JtBeanContainer.class) {
                if (jtBeansContainer == null) {
                    jtBeansContainer = new JtBeanContainer(applicationContext);
                }
            }
        }
    }

    public static JtBeanContainer getInstance() {
        if (jtBeansContainer == null) {
            throw new DontInitException();
        }
        return jtBeansContainer;
    }
}
