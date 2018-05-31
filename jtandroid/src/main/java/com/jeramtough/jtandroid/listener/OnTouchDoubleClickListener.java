package com.jeramtough.jtandroid.listener;

import android.view.MotionEvent;
import android.view.View;

/**
 * @author 11718
 * on 2018  February 06 Tuesday 19:41.
 */

public abstract class OnTouchDoubleClickListener implements View.OnTouchListener
{
    private long downTime;
    private boolean isFirst = true;
    
    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            downTime = System.currentTimeMillis();
        }
        else if (event.getAction() == MotionEvent.ACTION_UP ||
                event.getAction() == MotionEvent.ACTION_CANCEL)
        {
            long upTime = System.currentTimeMillis();
            long intervalTime = upTime - downTime;
            if (intervalTime < 100)
            {
                if (isFirst)
                {
                    isFirst = false;
                }
                else
                {
                    isFirst = true;
                    onDoubleClick(v);
                }
            }
        }
        return true;
    }
    
    public abstract void onDoubleClick(View view);
}
