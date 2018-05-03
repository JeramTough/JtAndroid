package com.jeramtough.jtandroid.ioc.interpreter;

import android.content.Context;
import com.jeramtough.jtandroid.ioc.IocUtil;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtServiceImpl;
import com.jeramtough.jtandroid.ioc.caller.NeededComponentCaller;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author 11718
 * on 2017  December 06 Wednesday 23:04.
 */

public class ServiceInterpreter implements Interpreter
{
	private Context context;
	private NeededComponentCaller neededComponentCaller;
	
	public ServiceInterpreter(Context context)
	{
		this.context = context;
	}
	
	@Override
	public Object getFieldValueObject(Field field)
	{
		boolean isHadEspecialAnnotation = false;
		Object fieldObject = null;
		
		InjectService injectService = field.getAnnotation(InjectService.class);
		Class serverImplClass = injectService.implement();
		
		if (serverImplClass.equals(DefaultServiceImpl.class))
		{
			try
			{
				serverImplClass =
						Class.forName(IocUtil.getDefaultServerImplClassName(field.getType()));
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < serverImplClass.getDeclaredAnnotations().length; i++)
		{
			Annotation annotation = serverImplClass.getDeclaredAnnotations()[i];
			
			if (annotation instanceof JtServiceImpl)
			{
				isHadEspecialAnnotation = true;
				
				try
				{
					
					for (Constructor constructor : serverImplClass.getDeclaredConstructors())
					{
						if (constructor.getAnnotation(IocAutowire.class) != null ||
								constructor.getParameterTypes().length == 0)
						{
							ArrayList<Object> constructorParameters = new ArrayList<>();
							for (Class<?> c : constructor.getParameterTypes())
							{
								Object constructorParameter = null;
								String fieldKeyName = IocUtil.processKeyName(c);
								if (c.equals(Context.class))
								{
									constructorParameter = context;
								}
								
								if (constructorParameter == null &&
										neededComponentCaller != null)
								{
									constructorParameter =
											neededComponentCaller.needComponent(context, c);
								}
								
								if (constructorParameter == null)
								{
									throw new IllegalStateException(
											"fail to interpreted the " + "[" +
													serverImplClass.getSimpleName() +
													"] instance because the parameter[" +
													fieldKeyName + "] " + " of constructor");
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
				catch (InstantiationException | IllegalAccessException | InvocationTargetException | IllegalArgumentException e)
				{
					e.printStackTrace();
				}
			}
			
			//here is last circulation
			if (!isHadEspecialAnnotation)
			{
				throw new IllegalStateException(
						"fail to interpreted the " + "[" + serverImplClass.getSimpleName() +
								"] instance because no annotation of the TestServiceImpl");
			}
		}
		return fieldObject;
	}
	
	public void setNeededComponentCaller(NeededComponentCaller neededComponentCaller)
	{
		this.neededComponentCaller = neededComponentCaller;
	}
	
}
