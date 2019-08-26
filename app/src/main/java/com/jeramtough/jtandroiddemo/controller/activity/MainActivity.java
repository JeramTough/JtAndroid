package com.jeramtough.jtandroiddemo.controller.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Html;
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
import com.jeramtough.jtlog.annotation.JtLoggerConfig;
import com.jeramtough.jtlog.facade.P;

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        String content = "我叫<font color=\"#FF0000\">张小龙</font>";
        if (Build.VERSION.SDK_INT >= 24) {
            textView.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT));
        } else {
            textView.setText(Html.fromHtml(content));
        }

        P.info("aa");
    }
}
