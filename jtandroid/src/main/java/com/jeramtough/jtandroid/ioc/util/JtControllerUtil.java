package com.jeramtough.jtandroid.ioc.util;

import com.jeramtough.jtandroid.function.JtExecutors;
import com.jeramtough.jtandroid.ioc.annotation.JtController;
import com.jeramtough.jtandroid.ioc.bean.JtField;
import com.jeramtough.jtandroid.ioc.implfinder.DefaultInterfaceImplFinder;
import com.jeramtough.jtandroid.ioc.implfinder.InterfaceImplFinder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created on 2019-08-30 15:49
 * by @author JeramTough
 */
public class JtControllerUtil {


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
                                return findJtFieldsFromJtControllerClass(finalC);
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


    //***********************

    private static List<JtField> findJtFieldsFromJtControllerClass(Class jtControllerClass) {
        List<JtField> jtFields = new ArrayList<>();

        Field[] fields = jtControllerClass.getDeclaredFields();
        for (Field field : fields) {
            JtField jtField = new JtField();
            if (field.getType().isInterface()) {
                Class interfaceClass = field.getType();
                InterfaceImplFinder interfaceImplFinder =
                        new DefaultInterfaceImplFinder(jtControllerClass, interfaceClass,
                                field.getDeclaredAnnotations());
                jtField.setField(field);
                jtField.setImplClass(interfaceImplFinder.find());
            }
            else {
                jtField.setField(field);
                jtField.setImplClass(field.getType());
            }
            jtFields.add(jtField);
        }
        return jtFields;
    }
}
