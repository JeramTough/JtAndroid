package com.jeramtough.jtandroiddemo.controller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.jeramtough.jtandroid.controller.activity.JtIocActivity;
import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroiddemo.R;
import com.jeramtough.jtandroiddemo.business.TestService;
import com.jeramtough.jtandroiddemo.component.A;

/**
 * @author 11718
 */
public class MainActivity extends JtIocActivity
{
//	@InjectService
//	private TestService testService;
	
	@InjectComponent
	private A a;
	
	private TextView textView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
}
