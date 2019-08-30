package com.jeramtough.jtandroid.ioc.injector;

import com.jeramtough.jtandroid.ioc.container.BeansContainer;

/**
 * @author 11718
 */
public class BeansInjectorImpl implements BeansInjector {

    private int beInjectedBeansCount = 0;
    private BeansContainer beansContainer;

    public BeansInjectorImpl(BeansContainer beansContainer) {
        this.beansContainer = beansContainer;
    }

    @Override
    public void inject(Class handleBeansClass, Object beInjectedObject) {
       /* Field[] fields = handleBeansClass.getDeclaredFields();
        for (Field field : fields) {
            InjectComponent injectComponent = field.getAnnotation(InjectComponent.class);
            JtField jtField = null;
            if (injectComponent != null) {
                jtField = new JtField(field, injectComponent.impl());
            }
            InjectService injectService = field.getAnnotation(InjectService.class);
            if (injectService != null) {
                jtField = new JtField(field, injectService.impl());
            }

            if (jtField != null) {
                //the injectedObject is injected value.
                beInjectedBeansCount++;

                field.setAccessible(true);
                Object object = beansContainer.getBean(jtField);
                try {
                    field.set(beInjectedObject, object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }*/
    }

}
