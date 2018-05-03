package com.jeramtough.jtandroiddemo.component.dao.mapper;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.jtandroiddemo.component.dao.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 11718
 * on 2018  May 03 Thursday 23:38.
 */
@JtComponent
public class ShallLearningMapper extends DaoMapper
{
	@IocAutowire
	public ShallLearningMapper(MyDatabaseHelper myDatabaseHelper)
	{
		super(myDatabaseHelper);
	}
	
	public List<Integer> getShallLearningWords(int limit)
	{
		ArrayList<Integer> list = new ArrayList<>();
		
		
		
		return list;
	}
}
