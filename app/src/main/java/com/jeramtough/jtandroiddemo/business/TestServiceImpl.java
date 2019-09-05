package com.jeramtough.jtandroiddemo.business;

import android.content.Context;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtServiceImpl;
import com.jeramtough.jtandroiddemo.component.dao.mapper.ShallLearningMapper;

/**
 * @author 11718
 * on 2018  May 02 Wednesday 20:45.
 */
@JtServiceImpl
public class TestServiceImpl implements TestService
{
	
	private Context context;
	private ShallLearningMapper shallLearningMapper;
	
	@IocAutowire
	public TestServiceImpl(Context context, ShallLearningMapper shallLearningMapper)
	{
		this.context = context;
		this.shallLearningMapper = shallLearningMapper;
	}
	
	@Override
	public void initApp()
	{
	
	}
}
