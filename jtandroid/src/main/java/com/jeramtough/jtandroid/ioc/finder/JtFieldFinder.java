package com.jeramtough.jtandroid.ioc.finder;

import com.jeramtough.jtandroid.ioc.bean.JtField;

import java.util.List;

/**
 * @author 11718
 */
public interface JtFieldFinder {

    List<JtField> findJtFieldFromClass(Class c);

}