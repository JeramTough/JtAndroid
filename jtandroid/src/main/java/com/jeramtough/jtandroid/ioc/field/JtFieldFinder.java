package com.jeramtough.jtandroid.ioc.field;

import java.util.List;

/**
 * Created on 2019-08-31 13:47
 * by @author JeramTough
 */
public interface JtFieldFinder {

    List<JtField> findJtFieldsFromJtControllerClass(Class jtControllerClass);
}
