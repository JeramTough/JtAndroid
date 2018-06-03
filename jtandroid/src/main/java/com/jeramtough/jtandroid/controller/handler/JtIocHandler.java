package com.jeramtough.jtandroid.controller.handler;

import android.support.v7.app.AppCompatActivity;

import com.jeramtough.jtandroid.ioc.annotation.JtController;
import com.jeramtough.jtandroid.ioc.IocContainer;
import com.jeramtough.jtandroid.ioc.iocimpl.JtIocContainer;

/**
 * @author 11718
 *         on 2017  December 07 Thursday 17:24.
 */
@JtController
public class JtIocHandler extends JtBaseHandler
{
	public JtIocHandler(AppCompatActivity activity)
	{
		super(activity);
		getIocContainer().injectCSV(activity, this);
	}
	
	public IocContainer getIocContainer()
	{
		return JtIocContainer.getIocContainer();
	}
}
