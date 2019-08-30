package com.jeramtough.jtandroid.function;

import android.content.Context;
import android.content.SharedPreferences;

import com.jeramtough.jtandroid.ioc.annotation.IocAutowire;
import com.jeramtough.jtandroid.ioc.annotation.JtComponent;

@JtComponent
public class FirstBoot {
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @IocAutowire
    public FirstBoot(Context context) {
        super();
        this.context = context;

        sharedPreferences = context.getSharedPreferences(this.getClass().getSimpleName(), 0);
        editor = sharedPreferences.edit();
    }

    public void firstBootOver() {
        editor.putBoolean("isFirstBoot", false);
        editor.commit();
    }

    public boolean isFirstBoot() {
        return sharedPreferences.getBoolean("isFirstBoot", true);
    }

}
