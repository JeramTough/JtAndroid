package com.jeramtough.jtandroid.ioc.log;

import android.util.Log;

/**
 * @author 11718
 */
public class P {
    private static String tag = "JtIoc";

    public static void info(String message) {
        Log.i(tag, message);
    }

    public static <T extends Number> void info(T message) {
        info(message + "");
    }


    public static void info(Object message) {
        info(message.toString());
    }

    public static void warn(String message) {
        Log.w(tag, message);
    }

    public static <T extends Number> void warn(T message) {
        warn(message + "");
    }


    public static void warn(Object message) {
        warn(message.toString());
    }

    public static void error(String message) {
        Log.e(tag, message);
    }

    public static <T extends Number> void error(T message) {
        error(message + "");
    }


    public static void error(Object message) {
        error(message.toString());
    }

}
