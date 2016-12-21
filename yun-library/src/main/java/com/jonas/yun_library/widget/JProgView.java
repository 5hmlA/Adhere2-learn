package com.jonas.yun_library.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.util.AttributeSet;
import android.view.View;

import com.jonas.yun_library.helper.DrawHelper;

import java.text.NumberFormat;

/**
 * @author yun.
 * @date 2016/12/21
 * @des [一句话描述]
 * @since [https://github.com/ZuYun]
 * <p><a href="https://github.com/ZuYun">github</a>
 */
public class JProgView extends View {

    private int mWidth;
    private RectF mOutRectF;
    private Paint mOutPaint;
    private RectF mProRectF;
    private Paint mProgPaint;
    private float mProg;
    private int mPading;
    private Path mOutPath;
    private int mPColor = Color.RED;
    private int mSecPColor = Color.TRANSPARENT;

    private int mTPColor = Color.WHITE;
    private int mSecTPColor;

    /**
     * 默认-1 为半圆
     */
    private float radio = -1;
    private Matrix mShaderMatric;
    private LinearGradient mLinearGradient;
    private float mTransx;
    private Matrix mProgMatrix;
    private Path mProgPath;
    private LinearGradient mProgLinearGradient;
    private LinearGradient mProgTextLinearGradient;
    private Paint mTextPaint;
    private NumberFormat mPercentFormat;
    private PointF mCenter;
    private Drawable mBackground;
    private String[] mShowMsgs;
    private String mShow;

    {
        mOutPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(50);
        mOutPaint.setColor(Color.GRAY);
    }

    public JProgView(Context context) {
        super(context);
        init(context);
    }

    public JProgView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public JProgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mPercentFormat = NumberFormat.getPercentInstance();
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mOutPaint.setStrokeWidth(5);
        mOutPaint.setStyle(Paint.Style.STROKE);
        if (mSecTPColor == 0) {
            mSecTPColor = mPColor;
        }
        mBackground = getBackground();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        int height = getHeight();
        mPading = 0;
        mCenter = new PointF(w / 2f, h / 2f);

        int[] colors = new int[]{mPColor, mSecPColor};
        int[] textColors = new int[]{mTPColor, mSecTPColor};

        mProgLinearGradient = new LinearGradient(mPading, 0, mWidth - mPading, 0, colors, new float[]{0f, 0.001f}, Shader.TileMode.CLAMP);//渐变区域0-0.001
        mProgTextLinearGradient = new LinearGradient(mPading, 0, mWidth - mPading, 0, textColors, new float[]{0f, 0.001f}, Shader.TileMode.CLAMP);

        mProgMatrix = new Matrix();
        mProgPaint.setShader(mProgLinearGradient);
        mTextPaint.setShader(mProgTextLinearGradient);

        float strokeWidth = mOutPaint.getStrokeWidth();
        mOutRectF = new RectF(mPading + strokeWidth / 2, mPading + strokeWidth / 2, mWidth - mPading - strokeWidth / 2, height - mPading - strokeWidth / 2);
        mProRectF = new RectF(mPading, mPading, mWidth - mPading, height - mPading);

        if (radio == -1) {
            radio = mProRectF.height() / 2;
        }
        mOutPath = new Path();
        mProgPath = new Path();

        mOutPath.addRoundRect(mOutRectF, radio, radio, Path.Direction.CCW);
        mProgPath.addRoundRect(mProRectF, radio, radio, Path.Direction.CCW);

//        mLinearGradient = new LinearGradient(0, 0, (mWidth - 2 * mPading), 2 * h,
//                new int[]{mPColor, 0x801B79E8, mPColor},
//                new float[]{0, 0.2f, 1}, Shader.TileMode.MIRROR);
//        mShaderMatric = new Matrix();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            GradientDrawable gradientDrawable = new GradientDrawable();
//            gradientDrawable.setColor(getColorList());
            gradientDrawable.setCornerRadius(radio);
            RippleDrawable rippleDrawable = new RippleDrawable(getColorList(),gradientDrawable , null);
            setBackground(rippleDrawable);
//            setBackground(gradientDrawable);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mShow = mPercentFormat.format(mProg);
        if (mShowMsgs != null && mShowMsgs.length >= 2) {
            if (mProg <= 0) {
                mShow = mShowMsgs[0];
            } else if (mProg >= 1) {
                mShow = mShowMsgs[1];
            }
        }

        if (mBackground != null && (mProg == 0 || mProg >= 1)) {
            canvas.drawText(mShow, mCenter.x, mCenter.y + DrawHelper.getFontHeight(mTextPaint) / 2, mTextPaint);
            return;
        }
        canvas.drawPath(mOutPath, mOutPaint);//画 边框
        canvas.drawPath(mOutPath, mProgPaint);// 画进度
        canvas.drawText(mShow, mCenter.x, mCenter.y + DrawHelper.getFontHeight(mTextPaint) / 2, mTextPaint);
//        if (mProg < 1) {
//            mProgPaint.setShader(mLinearGradient);
//            mTransx += ((mWidth - 2 * mPading) / 10f);
//            if (mTransx > 2 * (mWidth - 2 * mPading)) {
//                mTransx = -(mWidth - 2 * mPading);
//            }
//            mShaderMatric.setScale(mProg, 1);
//            mShaderMatric.setTranslate(mTransx, 0);
//            mLinearGradient.setLocalMatrix(mShaderMatric);
//        }
//        postInvalidateDelayed(50);
    }

    /**
     * 进度为0--1之间
     *
     * @param progress
     */
    public void setProgress(float progress) {
        mProg = progress;
        mProgMatrix.setTranslate((mWidth - 2 * mPading) * mProg, 0);
        mProgLinearGradient.setLocalMatrix(mProgMatrix);
        mProgTextLinearGradient.setLocalMatrix(mProgMatrix);
        if (mBackground != null) {
            if (progress > 0 && progress < 1) {
                setBackground(null);
            } else {
                setBackground(mBackground);
            }
        }
        postInvalidate();
    }

    public float getProg() {
        return mProg;
    }

    /**
     * 进度颜色包括 进度条颜色 进度背景色
     *
     * @param colors
     */
    public void setProgColors(@NonNull @Size(min = 1) int... colors) {
        mPColor = colors[0];
        if (colors.length > 1) {
            mSecPColor = colors[1];
        }
    }

    public void setOutLineColor(@ColorInt int color) {
        mOutPaint.setColor(color);
    }

    /**
     * 进度颜色包括 进度条颜色 进度背景色
     *
     * @param colors
     */
    public void setTextColors(@NonNull @Size(min = 1) int... colors) {
        mTPColor = colors[0];
        if (colors.length > 1) {
            mSecTPColor = colors[1];
        }
    }

    /**
     * 设置 默认的文字 和 进度结束显示的内容
     */
    public void setTextSD(String... msg) {
        mShowMsgs = msg;
    }

    public float getRadio() {
        return radio;
    }

    public void setRadio(float radio) {
        this.radio = radio;
    }

    public void setTextSize(float textSize) {
        mTextPaint.setTextSize(textSize);
    }

    private ColorStateList getColorList() {
        return new ColorStateList(new int[][]{new int[]{android.R.attr.state_pressed},new int[]{android.R.attr.state_active},new int[]{}},
                new int[]{mPColor,mPColor,Color.TRANSPARENT});
    }
}
