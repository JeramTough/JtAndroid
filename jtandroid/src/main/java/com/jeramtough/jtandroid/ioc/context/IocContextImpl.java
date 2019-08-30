package com.jeramtough.jtandroid.ioc.context;

import android.content.Context;

import com.jeramtough.jtandroid.ioc.container.BeansContainer;
import com.jeramtough.jtandroid.ioc.container.JtBeansContainer;
import com.jeramtough.jtandroid.ioc.bean.JtField;
import com.jeramtough.jtandroid.ioc.log.IocLog;
import com.jeramtough.jtandroid.ioc.util.JtControllerUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author 11718
 */
public class IocContextImpl implements IocContext {


    private Context context;

    public IocContextImpl(Context context) {
        this.context = context;

        initJtBeansContainer();
    }

    private void initJtBeansContainer() {
        JtBeansContainer.init(context.getApplicationContext());
    }

    @Override
    public void injectBeansInto(final Object jtControllerObject) {
        long startInjectingTime = System.currentTimeMillis();

        //find JtFields from JtController
        List<JtField> jtFields = JtControllerUtil.findJtFieldsFromJtController(
                jtControllerObject);

        //Have gotten all JtField, than register its class to the BeansContainer..
        Map<JtField, Future<Class>> jtFieldFutureMap = new HashMap<>();
        for (final JtField jtField : jtFields) {
            jtFieldFutureMap.put(jtField,
                    getBeansContainer().registerBeanAsync(jtField.getImplClass()));
        }

        //Waiting for instancing all field finished.
        for (Map.Entry<JtField, Future<Class>> jtFieldFutureEntry : jtFieldFutureMap.entrySet()) {
            try {
                Class beanClass = jtFieldFutureEntry.getValue().get();
                Object beanObject = getBeansContainer().getBean(beanClass);
                Field field = jtFieldFutureEntry.getKey().getField();
                field.setAccessible(true);
                field.set(jtControllerObject, beanObject);
            }
            catch (InterruptedException | ExecutionException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        long endInjectingTime = System.currentTimeMillis();
        IocLog.info("Speed " + (endInjectingTime - startInjectingTime)
                + " millis second on injecting "
                + jtFields.size()
                + " beans into JtController【" + jtControllerObject.getClass().getName() + "】");
    }

    @Override
    public BeansContainer getBeansContainer() {
        return JtBeansContainer.getInstance();
    }

}
