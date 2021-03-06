package com.jeramtough.jtandroid.ioc.annotation;

/**
 * @author 11718
 *         on 2017  December 08 Friday 22:10.
 */

public enum JtBeanPattern
{
	/**
	 * singleton pattern
	 */
	Singleton,
	
	/**
	 * new instance each
	 */
	Prototype,

	/**
	 * android context
	 */
	Context
}
