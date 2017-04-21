package com.jonas.yun_library.tablayout;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ViewGroup;

/**
 * @author yun.
 * @date 2017/4/21
 * @des [一句话描述]
 * @since [https://github.com/mychoices]
 * <p><a href="https://github.com/mychoices">github</a>
 */
public abstract class JTabStyle {
    protected final JTabStyleDelegate mTabStyleDelegate;
    protected JPagerSlidingTabStrip mSts;


    JTabStyle(JPagerSlidingTabStrip slidingTabStrip) {
        mTabStyleDelegate = slidingTabStrip.getTabStyleDelegate();
        mSts = slidingTabStrip;
    }


    abstract void onSizeChanged(int w, int h, int oldw, int oldh);
    void afterFullContainer(ViewGroup container){};

    abstract void onDraw(Canvas canvas, ViewGroup tabsContainer, float currentPositionOffset, int
            lastCheckedPosition);


     float dp2dip(float dp) {
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm);
    }

}

