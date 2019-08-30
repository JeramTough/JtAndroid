package com.jeramtough.jtandroid;

import com.jeramtough.jtandroid.function.CountBetweenTime;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest
{
	@Test
	public void addition_isCorrect()
	{
		CountBetweenTime countBetweenTime=new CountBetweenTime();
		countBetweenTime.takeTime();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		countBetweenTime.takeTime();
		System.out.println(countBetweenTime.getIntervalTime());
	}
	
	@Test
	public void test1(){
	}
}