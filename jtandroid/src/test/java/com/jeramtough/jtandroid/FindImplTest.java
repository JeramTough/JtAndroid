package com.jeramtough.jtandroid;

import com.jeramtough.jtandroid.ioc.util.JtBeanUtil;

import org.junit.Test;

/**
 * Created on 2019-09-02 19:26
 * by @author JeramTough
 */
public class FindImplTest {
    
    @Test
    public void test1(){
        JtBeanUtil.getDefaultImplClassName1(this.getClass());
    }
    
}
