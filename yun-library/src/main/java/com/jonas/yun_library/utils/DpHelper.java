package com.jonas.yun_library.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 */
public class DpHelper {

    private DpHelper() {
        throw new AssertionError();
    }

    public static float dp2px(float dp) {
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, dp, dm);
//        return dp * dm.density;
    }

    public static float px2dp(float px) {
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, dm);
//        return px / dm.density;
    }
    public static float sp2px(float px) {
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, px, dm);
//        return px / dm.density;
    }

    public static int dp2pxCeilInt(float dp) {
        return (int) (dp2px(dp) + 0.5f);
    }

    public static int px2dpCeilInt(float px) {
        return (int) (px2dp(px) + 0.5f);
    }
}
