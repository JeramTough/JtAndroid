package com.jeramtough.jtandroid.controller.handler;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * @author 11718
 * on 2017  November 30 Thursday 15:05.
 */

public class JtBaseHandler extends Handler implements View.OnClickListener {
    private AppCompatActivity activity;

    public JtBaseHandler(AppCompatActivity activity) {
        this.activity = activity;

    }

    protected void initResources()
    {

    }

    public <T extends View> T findViewById(@IdRes int viewId) {
        return activity.findViewById(viewId);
    }

    public AppCompatActivity getActivity() {
        return activity;
    }

    public Context getContext() {
        return activity;
    }

    public void onStop() {

    }

    public void onDestroy() {

    }

    public void onResume() {
    }

    @Override
    @Deprecated
    public void onClick(View v) {
        onClick(v, v.getId());
    }

    public void onClick(View view, int viewId) {

    }
}
