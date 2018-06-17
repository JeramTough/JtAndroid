package com.jeramtough.jtandroiddemo.controller.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jeramtough.jtandroid.controller.activity.JtIocActivity;
import com.jeramtough.jtandroid.function.MusicPlayer;
import com.jeramtough.jtandroid.ioc.annotation.InjectComponent;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.jtandroiddemo.R;
import com.jeramtough.jtandroiddemo.business.TestService;
import com.jeramtough.jtandroiddemo.component.A;
import com.jeramtough.jtandroiddemo.component.B;
import com.jeramtough.jtandroiddemo.component.C;
import com.jeramtough.jtandroiddemo.component.D;
import com.jeramtough.jtandroiddemo.component.E;
import com.jeramtough.jtlog3.P;

/**
 * @author 11718
 */
public class MainActivity extends JtIocActivity {

    @InjectService
    private TestService testService;
    @InjectComponent
    private MusicPlayer musicPlayer;
    @InjectComponent
    private A a;
    @InjectComponent
    private B b;
    @InjectComponent
    private C c;
    @InjectComponent
    private D d;
    @InjectComponent
    private E e;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        textView.setOnTouchListener(new OnTouchDoubleClickListener() {
            @Override
            public void onDoubleClick(View view) {
                P.p(1);
            }

            @Override
            public void onSingleClick(View view) {
                P.p(2);
            }

            @Override
            public void onLongClick(View view) {
                P.p(3);
            }
        });
    }
}
