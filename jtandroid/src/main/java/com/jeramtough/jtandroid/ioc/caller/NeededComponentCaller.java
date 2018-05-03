package com.jeramtough.jtandroid.ioc.caller;

import android.content.Context;

/**
 * @author 11718
 * on 2018  May 04 Friday 02:15.
 */
public interface NeededComponentCaller
{
	Object needComponent(Context context, Class c);
}
