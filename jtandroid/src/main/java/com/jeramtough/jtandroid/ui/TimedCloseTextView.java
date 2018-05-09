package com.jeramtough.jtandroid.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

/**
 * @author 11718
 * on 2017  December 03 Sunday 12:23.
 */

public class TimedCloseTextView extends android.support.v7.widget.AppCompatTextView
{
	private Drawable defaultBackground;
	private ColorStateList defaultTextColor;
	
	public TimedCloseTextView(Context context)
	{
		super(context);
		initResources();
	}
	
	public TimedCloseTextView(Context context, @Nullable AttributeSet attrs)
	{
		super(context, attrs);
		initResources();
	}
	
	protected void initResources()
	{
		this.setGravity(Gravity.CENTER);
		this.setVisibility(View.GONE);
		defaultBackground = this.getBackground();
		defaultTextColor = this.getTextColors();
	}
	
	public void closeDelayed(long time)
	{
		this.post(new Runnable()
		{
			@Override
			public void run()
			{
				TimedCloseTextView.this.setVisibility(View.VISIBLE);
			}
		});
		this.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				TimedCloseTextView.this.setVisibility(View.GONE);
				setPrimaryMessage("");
				TimedCloseTextView.this.setTextColor(defaultTextColor);
				TimedCloseTextView.this.setBackground(defaultBackground);
				
			}
		}, time);
	}
	
	public TimedCloseTextView setPrimaryMessage(String message)
	{
		this.setBackgroundColor(Color.BLUE);
		this.setTextColor(Color.WHITE);
		this.setText(message);
		return this;
	}
	
	public TimedCloseTextView setErrorMessage(String message)
	{
		this.setBackgroundColor(Color.RED);
		this.setTextColor(Color.WHITE);
		this.setText(message);
		return this;
	}
	
	public TimedCloseTextView setNiceMessage(String message)
	{
		this.setBackgroundColor(0xFF34b103);
		this.setTextColor(Color.WHITE);
		this.setText(message);
		return this;
	}
	
	public void visible()
	{
		this.setVisibility(View.VISIBLE);
	}
	
	public void invisible()
	{
		this.setVisibility(View.INVISIBLE);
	}
	
	public void gone()
	{
		this.setVisibility(View.GONE);
	}
}
