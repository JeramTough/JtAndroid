package com.jeramtough.jtandroid.ioc.container;

import android.content.Context;

import com.jeramtough.jtandroid.ioc.exception.DontInitException;

/**
 * 2019-8-30
 *
 * @author 11718
 */
public class JtBeansContainer extends RegisterBeansContainer implements BeansContainer {

    private volatile static JtBeansContainer jtBeansContainer;


    private JtBeansContainer(Context applicationContext) {
        super(applicationContext);
    }

    public static void init(Context applicationContext) {
        if (jtBeansContainer == null) {
            synchronized (JtBeansContainer.class) {
                if (jtBeansContainer == null) {
                    jtBeansContainer = new JtBeansContainer(applicationContext);
                }
            }
        }
    }

    public static JtBeansContainer getInstance() {
        if (jtBeansContainer == null) {
            throw new DontInitException();
        }
        return jtBeansContainer;
    }
}
