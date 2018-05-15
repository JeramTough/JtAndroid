package com.jeramtough.jtandroid.function;

public class CountBetweenTime {

    private long lastTime = 0;
    private long intervalTime = 0;

    public void takeTime() {
        if (lastTime != 0) {
            intervalTime =  System.currentTimeMillis()-lastTime;
        }

        lastTime = System.currentTimeMillis();
    }

    public long getLastTime() {
        return lastTime;
    }

    public long getIntervalTime() {
        return intervalTime;
    }
}
