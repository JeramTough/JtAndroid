package com.jeramtough.jtandroid;

import org.junit.Test;

/**
 * Created on 2019-09-01 01:42
 * by @author JeramTough
 */
public class InterfaceTest {

    public interface Service{
        void test();
    }

    public class ServiceImpl implements Service{

        @Override
        public void test() {

        }
    }

    @Test
    public void test1(){
        Service s=new ServiceImpl();
        System.out.println("aaaa");
    }

}
