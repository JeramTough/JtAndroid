package com.jeramtough.jtandroid.listener;

import android.view.MotionEvent;
import android.view.View;

import com.jeramtough.jtandroid.function.JtExecutors;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author 11718
 * on 2018  February 06 Tuesday 19:41.
 */

public abstract class OnTouchClickListener implements View.OnTouchListener {
    private long downTime;
    private boolean isFirst = true;
    private boolean isDoubleClick = false;
    private boolean isSingleClick = false;

    private ScheduledExecutorService scheduledExecutorService;

    public OnTouchClickListener() {
        scheduledExecutorService = JtExecutors.newScheduledThreadPool(4);
    }

    @Override
    public boolean onTouch(final View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            downTime = System.currentTimeMillis();
        } else if (event.getAction() == MotionEvent.ACTION_UP ||
                event.getAction() == MotionEvent.ACTION_CANCEL) {
            long upTime = System.currentTimeMillis();
            long intervalTime = upTime - downTime;
            if (intervalTime < 100) {
                if (isFirst) {
                    isFirst = false;
                    isDoubleClick = false;
                    isSingleClick = false;

                    scheduledExecutorService.schedule(new Runnable() {
                        @Override
                        public void run() {
                            if (!isDoubleClick) {
                                isFirst = true;
                                isSingleClick = true;
                                onSingleClick(v);
                            }
                        }
                    }, 300, TimeUnit.MILLISECONDS);

                } else {
                    isFirst = true;
                    if (!isSingleClick) {
                        isDoubleClick = true;
                        onDoubleClick(v);
                    }
                }
            } else if (intervalTime > 1000) {
                onLongClick(v);
            }
        }
        return true;
    }

    public abstract void onDoubleClick(View view);

    public abstract void onSingleClick(View view);

    public abstract void onLongClick(View view);
}
