package com.jeramtough.jtandroid.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author 11718
 * on 2017  November 27 Monday 16:14.
 */

public class JtViewPager extends ViewPager
{
    private boolean isLastPage = false;
    private boolean isDragPage = false;
    private boolean canJumpPage = true;
    private boolean scrollble = true;
    
    private JumpToLastCaller jumpToLastCaller;
    
    private InitFinishedCaller initFinishedCaller;
    
    public JtViewPager(Context context)
    {
        super(context);
        this.initResources();
    }
    
    public JtViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.initResources();
    }
    
    protected void initResources()
    {
        this.addOnPageChangeListener(new OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels)
            {
                if ((isLastPage || getChildCount() == 1) && isDragPage &&
                        positionOffsetPixels == 0)
                {
                    //当前页是最后一页，并且是拖动状态，并且像素偏移量为0
                    if (canJumpPage)
                    {
                        canJumpPage = false;
                        jumpToLast();
                    }
                }
            }
            
            @Override
            public void onPageSelected(int position)
            {
                isLastPage = (position == getChildCount() - 1);
            }
            
            @Override
            public void onPageScrollStateChanged(int state)
            {
                isDragPage = state == 1;
            }
        });
    }
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        return scrollble && super.onInterceptTouchEvent(ev);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        return scrollble && super.onTouchEvent(ev);
    }
    
    public void setCanJumpPage(boolean canJumpPage)
    {
        this.canJumpPage = canJumpPage;
    }
    
    public void setJumpToLastCaller(JumpToLastCaller jumpToLastCaller)
    {
        this.jumpToLastCaller = jumpToLastCaller;
    }
    
    public void setScrollble(boolean scrollble)
    {
        this.scrollble = scrollble;
    }
    
    public void setCallFirstPageChangeListener(final OnPageChangeListener listener)
    {
        this.addOnPageChangeListener(listener);
        if (listener != null)
        {
            new Thread()
            {
                @Override
                public void run()
                {
                    for (; ; )
                    {
                        View view = JtViewPager.this.getChildAt(0);
                        if (view != null)
                        {
                            post(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    listener.onPageSelected(0);
                                }
                            });
                            break;
                        }
                    }
                }
            }.start();
        }
    }
    
    public void setLastPage(boolean lastPage)
    {
        isLastPage = lastPage;
    }
    
    /**
     * you must invoke the setAdapter() method before invoking this method.
     */
    public void setInitFinishedCaller(final InitFinishedCaller initFinishedCaller)
    {
        this.initFinishedCaller = initFinishedCaller;
        if (initFinishedCaller != null)
        {
            new Thread()
            {
                @Override
                public void run()
                {
                    for (; ; )
                    {
                        View view = JtViewPager.this.getChildAt(0);
                        if (view != null)
                        {
                            post(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    initFinishedCaller.initFinished();
                                }
                            });
                            break;
                        }
                    }
                }
            }.start();
        }
    }
    
    //************************************
    private void jumpToLast()
    {
        if (jumpToLastCaller != null)
        {
            jumpToLastCaller.jumpToLast();
        }
    }
    
    
    //{{{{{{{{{{{{{}}}}}}}}}}}}}}}}}}
    public interface JumpToLastCaller
    {
        void jumpToLast();
    }
    
    public interface InitFinishedCaller
    {
        void initFinished();
    }
    
}
