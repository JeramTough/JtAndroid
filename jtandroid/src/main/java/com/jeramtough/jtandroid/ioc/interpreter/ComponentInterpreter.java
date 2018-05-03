package com.jeramtough.jtandroid.ioc.interpreter;

import android.content.Context;
import com.jeramtough.jtandroid.ioc.IocUtil;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;
import com.jeramtough.jtandroid.ioc.caller.NeededComponentCaller;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * @author 11718
 * on 2017  December 06 Wednesday 22:03.
 */

public class ComponentInterpreter implements Interpreter
{
	private Context context;
	private NeededComponentCaller neededComponentCaller;
	
	public ComponentInterpreter(Context context)
	{
		this.context = context;
	}
	
	@Override
	public Object getFieldValueObject(Field field)
	{
		return this.instanceFieldObject(field.getType());
	}
	
	public Object instanceFieldObject(Class c)
	{
		Object fieldObject = null;
		for (Annotation annotation : c.getDeclaredAnnotations())
		{
			if (annotation instanceof JtComponent)
			{
				try
				{
					for (Constructor constructor : c.getConstructors())
					{
						if (constructor.getParameterTypes().length == 0)
						{
							fieldObject = constructor.newInstance();
							break;
						}
						else
						{
							if (constructor.getAnnotation(IocAutowire.class) != null ||
									constructor.getParameterTypes().length == 0)
							{
								ArrayList<Object> constructorParameters = new ArrayList<>();
								for (Class<?> c1 : constructor.getParameterTypes())
								{
									Object constructorParameter = null;
									String fieldKeyName;
									fieldKeyName = IocUtil.processKeyName(c1);
									
									if (c1.isAssignableFrom(context.getClass()))
									{
										constructorParameter = context;
									}
									else
									{
										if (constructorParameter == null &&
												neededComponentCaller != null)
										{
											constructorParameter = neededComponentCaller
													.needComponent(context, c1);
										}
									}
									
									if (constructorParameter == null)
									{
										throw new IllegalStateException(
												"fail to interpreted the " + "[" +
														c1.getSimpleName() +
														"] instance because the parameter[" +
														fieldKeyName + "] " +
														" of constructor");
									}
									constructorParameters.add(constructorParameter);
								}
								
								constructor.setAccessible(true);
								
								fieldObject = constructor.newInstance(constructorParameters
										.toArray(new Object[constructorParameters.size()]));
								break;
							}
						}
					}
				}
				catch (InstantiationException | IllegalAccessException | InvocationTargetException | IllegalArgumentException e)
				{
					e.printStackTrace();
				}
			}
		}
		return fieldObject;
	}
	
	public void setNeededComponentCaller(NeededComponentCaller neededComponentCaller)
	{
		this.neededComponentCaller = neededComponentCaller;
	}
}
