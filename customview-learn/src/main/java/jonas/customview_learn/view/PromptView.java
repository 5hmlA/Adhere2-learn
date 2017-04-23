package jonas.customview_learn.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;

/**
 * @author yun.
 * @date 2017/4/22
 * @des [一句话描述]
 * @since [https://github.com/mychoices]
 * <p><a href="https://github.com/mychoices">github</a>
 */
//public class PromptView extends android.support.v7.widget.AppCompatCheckedTextView {
public class PromptView extends android.support.v7.widget.AppCompatCheckedTextView {
    private Paint mBgPaint;
    private Paint mNumPaint;
    private int color_bg = Color.RED;
    private int color_num = Color.WHITE;
    private int num_size = 12;
    private float mHalfW;
    private float mNumHeight;
    private String msg_str = "78";
    private PointF mPromptCenterPoint;
    private RectF mMsgBg;
    private static final String NOTIFY = "nofity";
    private static final String ALOT = "~";
    //private static final String ALOT = "...~~";


    public static float dp2px(float px) {
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, dm);
    }


    public static float getFontHeight(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return -fontMetrics.top - fontMetrics.bottom;
    }


    public PromptView(Context context) {
        this(context, null);
    }


    public PromptView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public PromptView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTextAlignment(TEXT_ALIGNMENT_GRAVITY);
        setGravity(Gravity.CENTER);
        mNumPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mNumPaint.setTextAlign(Paint.Align.CENTER);
        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }


    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHalfW = w / 2f;
        mNumPaint.setTextSize(dp2px(num_size));
        mNumHeight = getFontHeight(mNumPaint);
        refreshNotifyBg();

        setPadding(0, (int) (mNumHeight), 0, (int) (mNumHeight));

        mBgPaint.setColor(color_bg);
        mNumPaint.setColor(color_num);
    }


    private boolean haveCompoundDrawable(Drawable[] compoundDrawables) {
        for (Drawable compoundDrawable : compoundDrawables) {
            if (compoundDrawable != null) {
                return true;
            }
        }
        return false;
    }


    private void refreshNotifyBg() {
        int textWidth = getTextWidth(getPaint(), getText().toString());
        int msgWidth = getTextWidth(mNumPaint, msg_str);
        float promptOffset = mNumHeight / 2;
        float halfMsgBgW = msgWidth / 2 + promptOffset;
        halfMsgBgW = halfMsgBgW > mNumHeight ? halfMsgBgW : mNumHeight;

        if (!TextUtils.isEmpty(getText())) {
            //textWidth的宽度不小于3个字的宽度
            textWidth = getText().length() < 3 ? textWidth / getText().length() * 3 : textWidth;
        }
        else {
            textWidth = (int) (mHalfW * 2);
        }
        Drawable[] compoundDrawables = getCompoundDrawables();
        if (!haveCompoundDrawable(compoundDrawables)) {
            promptOffset = promptOffset / 2;
        }
        mPromptCenterPoint = new PointF(mHalfW + textWidth / 2 - promptOffset, mNumHeight);
        mMsgBg = new RectF(mPromptCenterPoint.x - halfMsgBgW, 0, mPromptCenterPoint.x + halfMsgBgW,
                mPromptCenterPoint.y + mNumHeight);
        //防止画到屏幕外
        if (mMsgBg.right > 2 * mHalfW) {
            //顺序不可变 因为mPromptCenterPoint依赖mMsgBg
            mPromptCenterPoint.offset(2 * mHalfW - mMsgBg.right, 0);
            mMsgBg.offset(2 * mHalfW - mMsgBg.right, 0);
            //mPromptCenterPoint.x += 2 * mHalfW - mMsgBg.right;
        }
    }


    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        System.out.println(getTextSize());
        if (!TextUtils.isEmpty(msg_str)) {
            if (msg_str.equals(NOTIFY)) {
                //画提示圆点即可
                canvas.drawCircle(mPromptCenterPoint.x, mPromptCenterPoint.y, mNumHeight / 2, mBgPaint);
            }
            else {
                canvas.drawRoundRect(mMsgBg, mNumHeight, mNumHeight, mBgPaint);
                canvas.drawText(msg_str, mPromptCenterPoint.x, mPromptCenterPoint.y + mNumHeight / 2,
                        mNumPaint);
            }
        }
    }


    public static int getTextWidth(Paint paint, String str) {
        Rect bounds = new Rect();
        paint.getTextBounds(str, 0, str.length(), bounds);
        //int iRet = 0;
        //if (str != null && str.length() > 0) {
        //    int len = str.length();
        //    float[] widths = new float[len];
        //    paint.getTextWidths(str, widths);
        //    for (int j = 0; j < len; j++) {
        //        iRet += (int) Math.ceil(widths[j]);
        //    }
        //}
        //return iRet;
        return bounds.width();
    }


    /**
     * 当num的值小于0 显示提示小圆点
     * 等于0 不现实任何
     */
    public PromptView setPromptNum(int num) {
        msg_str = String.format("%d", num);
        if (num > 99) {
            msg_str = ALOT;
        }
        else if (num == 0) {
            msg_str = "";
        }
        else if (num < 0) {
            msg_str = NOTIFY;
        }
        refreshNotifyBg();
        return this;
    }


    public void setColor_bg(int color_bg) {
        this.color_bg = color_bg;
    }


    public void setColor_num(int color_num) {
        this.color_num = color_num;
    }


    public void setNum_size(int num_size) {
        this.num_size = num_size;
    }
}
