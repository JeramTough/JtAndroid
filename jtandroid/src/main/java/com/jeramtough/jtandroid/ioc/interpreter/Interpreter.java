package com.jeramtough.jtandroid.ioc.interpreter;

import com.jeramtough.jtandroid.ioc.bean.BeanAnnotationInfo;

/**
 * @author 11718
 * on 2017  December 06 Wednesday 21:58.
 */

public interface Interpreter {

    Object getBeanInstance();

    BeanAnnotationInfo getBeanAnnotationInfo();
}
