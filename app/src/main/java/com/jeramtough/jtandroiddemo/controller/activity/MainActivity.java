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
public class MainActivity extends JtIocActivity
{
    @InjectService
    private TestService testService;
    
    @InjectComponent
    private A a;
    
    @InjectComponent
    private MusicPlayer musicPlayer;
    
    @InjectView(R.id.textView)
    private TextView textView;
    
    @InjectView(R.id.button_play)
    private Button button;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        this.getIocContainer().injectView(this, this);
        button.setOnDragListener(new View.OnDragListener()
        {
            @Override
            public boolean onDrag(View v, DragEvent event)
            {
                return true;
            }
        });
        textView.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                String tag = v.getId() + "";
                ClipData.Item item = new ClipData.Item(tag);
                ClipData dragData = new ClipData(new ClipDescription(tag,
                        new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}), item);
                ImageView imageView=new ImageView(MainActivity.this);
                imageView.setBackgroundColor(Color.RED);
                View.DragShadowBuilder myShadow = new MyDragShadowBuilder(textView);
    
                // Starts the drag
    
                v.startDrag(dragData,  // the data to be dragged
                        myShadow,  // the drag shadow builder
                        null,      // no need to use local data
                        0          // flags (not currently used, set to 0)
                );
                return true;
            }
        });
    }
    
    
    private static class MyDragShadowBuilder extends View.DragShadowBuilder
    {
        
        // The drag shadow image, defined as a drawable thing
        private static Drawable shadow;
        
        // Defines the constructor for myDragShadowBuilder
        public MyDragShadowBuilder(View v)
        {
            
            // Stores the View parameter passed to myDragShadowBuilder.
            super(v);
            
            // Creates a draggable image that will fill the Canvas provided by the system.
            shadow = new ColorDrawable(Color.LTGRAY);
        }
        
        // Defines a callback that sends the drag shadow dimensions and touch point back to the
        // system.
        @Override
        public void onProvideShadowMetrics(Point size, Point touch)
        {
            // Defines local variables
            int width, height;
            
            // Sets the width of the shadow to half the width of the original View
            width = getView().getWidth() / 2;
            
            // Sets the height of the shadow to half the height of the original View
            height = getView().getHeight() / 2;
            
            // The drag shadow is a ColorDrawable. This sets its dimensions to be the same as the
            // Canvas that the system will provide. As a result, the drag shadow will fill the
            // Canvas.
            shadow.setBounds(0, 0, width, height);
            
            // Sets the size parameter's width and height values. These get back to the system
            // through the size parameter.
            size.set(width, height);
            
            // Sets the touch point's position to be in the middle of the drag shadow
            touch.set(width / 2, height / 2);
        }
        
        // Defines a callback that draws the drag shadow in a Canvas that the system constructs
        // from the dimensions passed in onProvideShadowMetrics().
        @Override
        public void onDrawShadow(Canvas canvas)
        {
            
            // Draws the ColorDrawable in the Canvas passed in from the system.
            shadow.draw(canvas);
        }
    }
}
