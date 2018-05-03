package com.jeramtough.jtandroiddemo.component;

import android.content.Context;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;

/**
 * @author 11718
 * on 2018  May 04 Friday 02:11.
 */
@JtComponent
public class B
{
	private Context context;
	
	@IocAutowire
	public B(Context context)
	{
		this.context = context;
	}
}
