package com.jeramtough.jtandroiddemo.controller.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    private TextView textView;

    @InjectView(R.id.button_play)
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getIocContainer().injectView(this, this);
        button.setOnClickListener(this);


        ListView listView = null;
        listView.setOnScrollListener(new OnScrollBottomOrTopListener() {
            @Override
            public void onScrollBottom() {
                super.onScrollBottom();
            }

            @Override
            public void onScrollTop() {
                super.onScrollTop();
            }
        });
    }

    @Override
    public void onClick(View view, int viewId) {
        switch (viewId) {
            case R.id.button_play:
                Uri uri = Uri.parse("http://openapi.youdao.com/ttsapi?q=kitten&langType=en&sign=9DED8152F174457A13BB8D3C44A04B09&salt=1526476020111&voice=4&format=mp3&appKey=4f4fa57d53190d0f");
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource("http://openapi.youdao.com/ttsapi?q=kitten&langType=en&sign=9DED8152F174457A13BB8D3C44A04B09&salt=1526476020111&voice=4&format=mp3&appKey=4f4fa57d53190d0f");
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                break;
        }
    }
}
