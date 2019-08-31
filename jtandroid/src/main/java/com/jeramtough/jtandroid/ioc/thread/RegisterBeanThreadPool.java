package com.jeramtough.jtandroid.ioc.thread;

import android.support.annotation.NonNull;

import com.jeramtough.jtandroid.ioc.annotation.JtBeanPattern;
import com.jeramtough.jtandroid.ioc.util.JtBeanUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2019-08-30 23:50
 * by @author JeramTough
 */
public class RegisterBeanThreadPool {

    private volatile static RegisterBeanThreadPool registerBeanThreadPool;

    public static RegisterBeanThreadPool getInstance() {
        if (registerBeanThreadPool == null) {
            synchronized (RegisterBeanThreadPool.class) {
                if (registerBeanThreadPool == null) {
                    registerBeanThreadPool = new RegisterBeanThreadPool();
                }
            }
        }
        return registerBeanThreadPool;
    }


    /**
     * 把持所有正在生成Bean线程的Future实例，防止重复的Bean实例提交到线程池中
     */
    private Map<Class, Future<Class>> allRegisterBeanFutureMap;

    private ThreadPoolExecutor executor;

    private RegisterBeanThreadPool() {
        allRegisterBeanFutureMap = new HashMap<>(30);

        executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>(), new ThreadFactory() {
            @Override
            public Thread newThread(@NonNull Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("RgisterBean_" + thread.getName());
                return thread;
            }
        });
    }

    public synchronized Future<Class> submit(RegisterBeanThread thread) {

        //如果beanClass是Context对象之类的，直接返回
        if (!JtBeanUtil.isJtBean(thread.getBeanClass())) {
            Future<Class> future = executor.submit(thread);
            return future;
        }

        Future<Class> future;
        if (JtBeanUtil.getJtBeanPattern(
                thread.getBeanClass()) == JtBeanPattern.Singleton) {
            future = allRegisterBeanFutureMap.get(thread.getBeanClass());
            if (future == null) {
                future = executor.submit(thread);
                allRegisterBeanFutureMap.put(thread.getBeanClass(), future);
            }
        }
        else {
            future = executor.submit(thread);
        }
        return future;
    }

    public synchronized void clearRegisterBeanFutures() {
        allRegisterBeanFutureMap.clear();
    }
}
