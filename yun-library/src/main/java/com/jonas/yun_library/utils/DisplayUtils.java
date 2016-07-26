package com.jonas.yun_library.utils;

import android.content.Context;

/**
 */
public class DisplayUtils {

    private DisplayUtils() {
        throw new AssertionError();
    }

    public static float dp2Px(Context context, float dp) {
        if (context == null) {
            return -1;
        }
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static float px2Dp(Context context, float px) {
        if (context == null) {
            return -1;
        }
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float dp2PxInt(Context context, float dp) {
        return (int)(dp2Px(context, dp) + 0.5f);
    }

    public static float px2DpCeilInt(Context context, float px) {
        return (int)(px2Dp(context, px) + 0.5f);
    }
}
