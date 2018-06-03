package com.jeramtough.jtandroiddemo.controller.activity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jeramtough.jtandroid.controller.activity.JtIocActivity;
import com.jeramtough.jtandroid.function.MusicPlayer;
import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroid.ioc.annotation.InjectView;
import com.jeramtough.jtandroid.listener.OnScrollBottomOrTopListener;
import com.jeramtough.jtandroiddemo.R;
import com.jeramtough.jtandroiddemo.business.TestService;
import com.jeramtough.jtandroiddemo.component.A;

import java.io.IOException;

/**
 * @author 11718
 */
public class MainActivity extends JtIocActivity {

    @InjectService
    private TestService testService;

    @InjectComponent
    private A a;

    @InjectComponent
    private MusicPlayer musicPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
