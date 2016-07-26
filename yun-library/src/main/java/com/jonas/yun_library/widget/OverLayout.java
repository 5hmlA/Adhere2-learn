package com.jonas.yun_library.widget;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * @author jiangzuyun.
 * @date 2016/7/6
 * @des [一句话描述]
 * @since [产品/模版版本]
 */


public class OverLayout extends RelativeLayout implements GestureDetector.OnGestureListener {

    private GestureDetectorCompat mGestureDetectorCompat;

    public OverLayout(Context context) {
        super(context);
    }

    public OverLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OverLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mGestureDetectorCompat = new GestureDetectorCompat(getContext(), new GestureDetector.SimpleOnGestureListener(){});
//        mGestureDetectorCompat.setOnDoubleTapListener();
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
