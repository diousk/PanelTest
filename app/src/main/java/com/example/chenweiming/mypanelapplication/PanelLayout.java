package com.example.chenweiming.mypanelapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.example.chenweiming.mypanelapplication.panel.SlidingUpPanelLayout;


/**
 * Created by david.chen@soocii.me on 2018/6/4.
 */
public class PanelLayout extends SlidingUpPanelLayout {
    public PanelLayout(Context context) {
        super(context);
    }

    public PanelLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PanelLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
