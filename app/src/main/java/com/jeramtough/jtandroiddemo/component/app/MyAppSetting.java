package com.jeramtough.jtandroiddemo.component.app;

import android.content.Context;
import com.jeramtough.jtandroid.function.AppSetting;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;

/**
 * @author 11718
 * on 2018  May 03 Thursday 14:50.
 */
@JtComponent
public class MyAppSetting extends AppSetting
{
	
	@IocAutowire
	public MyAppSetting(Context context)
	{
		super(context);
	}
	
	public int getPerLearningCount()
	{
		return getSharedPreferences().getInt("perLearningCount", 10);
	}
	
	public void setPerLearningCount(int perLearningCount)
	{
		getEditor().putInt("perLearningCount", perLearningCount);
		getEditor().apply();
	}
}
