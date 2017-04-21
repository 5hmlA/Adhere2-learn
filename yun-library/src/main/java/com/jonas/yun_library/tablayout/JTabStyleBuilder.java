package com.jonas.yun_library.tablayout;

import android.support.annotation.IntDef;

public class JTabStyleBuilder {
    public static final int STYLE_DEFAULT = 0;
    public static final int STYLE_ROUND = 1;
    public static final int STYLE_DOTS = 2;
    public static final int STYLE_CUSTOM = -1;

    @IntDef({ STYLE_DEFAULT, STYLE_ROUND, STYLE_DOTS }) public @interface TabStyle {}


    public static JTabStyle createJTabStyle(JPagerSlidingTabStrip slidingTabStrip, @TabStyle int tabStyle) {
        if (tabStyle == STYLE_DEFAULT) {
            return new DefaultTabStyle(slidingTabStrip);
        }
        else if (tabStyle == STYLE_ROUND) {
            return new RoundTabStyle(slidingTabStrip);
        }
        else if (tabStyle == STYLE_DOTS) {
            return new DotsTabStyle(slidingTabStrip);
        }
        else {
          return new DefaultTabStyle(slidingTabStrip);
        }


    }
}