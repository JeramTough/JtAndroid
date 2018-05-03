package com.jeramtough.jtandroiddemo.business;

import android.content.Context;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtServiceImpl;
import com.jeramtough.jtandroiddemo.component.A;

/**
 * @author 11718
 * on 2018  May 02 Wednesday 20:45.
 */
@JtServiceImpl
class TestServiceImpl implements TestService
{
	
	private Context context;
	private A a;
	
	@IocAutowire
	TestServiceImpl(Context context, A a)
	{
		this.context = context;
		this.a = a;
	}
	
	@Override
	public void initApp()
	{
	
	}
}
