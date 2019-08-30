package com.jeramtough.jtandroid.ioc.jtfield;

import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ioc.jtfield.JtField;

/**
 * Created on 2019-08-30 20:01
 * by @author JeramTough
 */
public interface JtFieldGenerator {

    JtField parse(InjectComponent injectComponent);

    JtField parse(InjectService injectService);
}
