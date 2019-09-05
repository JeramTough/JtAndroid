package com.jeramtough.jtandroid.util;

import android.widget.RadioGroup;

/**
 * Created on 2019-09-04 22:12
 * by @author JeramTough
 */
public class ViewUtil {

    public static void disableRadioGroup(RadioGroup radioGroup) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setEnabled(false);
        }
    }

    public static void enableRadioGroup(RadioGroup radioGroup) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setEnabled(true);
        }
    }


}
