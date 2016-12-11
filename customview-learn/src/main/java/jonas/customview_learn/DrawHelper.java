package jonas.customview_learn;

import android.graphics.Paint;

/**
 * @author jiangzuyun.
 * @date 2016/11/25
 * @des [一句话描述]
 * @since [产品/模版版本]
 */


public class DrawHelper {
    public static float getFontHeight(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return -fontMetrics.top - fontMetrics.bottom;
    }
}
