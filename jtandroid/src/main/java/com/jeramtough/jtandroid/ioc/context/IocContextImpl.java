package com.jeramtough.jtandroid.ioc.context;

import android.content.Context;

import com.jeramtough.jtandroid.function.JtExecutors;
import com.jeramtough.jtandroid.ioc.annotation.JtController;
import com.jeramtough.jtandroid.ioc.container.BeansContainer;
import com.jeramtough.jtandroid.ioc.container.JtBeansContainer;
import com.jeramtough.jtandroid.ioc.field.DefaultJtFieldFinder;
import com.jeramtough.jtandroid.ioc.field.JtField;
import com.jeramtough.jtandroid.ioc.log.IocLog;
import com.jeramtough.jtandroid.ioc.thread.RegisterBeanThreadPool;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
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
        List<JtField> jtFields = findJtFieldsFromJtController(jtControllerObject);

        //Have gotten all JtField, than register its class to the BeansContainer..
        Map<JtField, Future<Class>> jtFieldFutureMap = new HashMap<>();
        for (final JtField jtField : jtFields) {
            jtFieldFutureMap.put(jtField,
                    getBeansContainer().registerBeanAsync(jtField.getImplClass()));
        }
        //---------------Waiting for instancing all field finished.

        //Start to inject bean instance to field.
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

        //end
        RegisterBeanThreadPool.getInstance().clearRegisterBeanFutures();
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


    //******************************************

    public static List<JtField> findJtFieldsFromJtController(Object jtControllerObject) {

        ExecutorService service = JtExecutors.newCachedThreadPool();
        List<Future<List<JtField>>> jtFieldsFutures = new ArrayList<>();

        //traverse all the JtController to find the JtFields.
        Class c = jtControllerObject.getClass();
        while (c.getSuperclass() != null) {
            final Class finalC = c;
            Future<List<JtField>> future = service.submit(
                    new Callable<List<JtField>>() {
                        @Override
                        public List<JtField> call() {
                            if (finalC.getAnnotation(JtController.class) != null) {
                                return new DefaultJtFieldFinder()
                                        .findJtFieldsFromJtControllerClass(finalC);
                            }
                            return null;
                        }
                    });
            jtFieldsFutures.add(future);
            c = c.getSuperclass();
        }

        service.shutdown();

        ArrayList<JtField> jtFields = new ArrayList<>();
        for (Future<List<JtField>> future : jtFieldsFutures) {
            try {
                if (future.get() != null) {
                    jtFields.addAll(future.get());
                }
            }
            catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return jtFields;
    }
}
