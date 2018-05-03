package com.jeramtough.jtandroiddemo.business;

import android.content.Context;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtServiceImpl;

/**
 * @author 11718
 * on 2018  May 02 Wednesday 20:45.
 */
@JtServiceImpl
class TestServiceImpl implements TestService
{
	
	private Context context;
	
	@IocAutowire
	TestServiceImpl(Context context)
	{
		this.context = context;
	}
	
	@Override
	public void initApp()
	{
	
	}
}
