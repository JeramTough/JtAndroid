package com.jeramtough.jtandroid.function;

import android.content.Context;
import android.content.SharedPreferences;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;

/**
 * @author 11718
 * on 2018  May 03 Thursday 14:51.
 */
@JtComponent
public class AppSetting
{
	private Context context;
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor editor;
	
	@IocAutowire
	public AppSetting(Context context)
	{
		this.context = context;
		sharedPreferences = context.getSharedPreferences(this.getClass().getSimpleName(), 0);
		editor = sharedPreferences.edit();
	}
	
	public SharedPreferences getSharedPreferences()
	{
		return sharedPreferences;
	}
	
	public SharedPreferences.Editor getEditor()
	{
		return editor;
	}
}
