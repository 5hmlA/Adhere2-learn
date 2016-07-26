package com.jonas.yun_library.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;


/**
 *
 * @Author jwx338756
 * @Date: 2016
 * @Description: 下载进度
 * @Others:
 *
 */
public class ProgressButton extends View {

    private int mWidth;
    private RectF mRectF;
    private Paint mOutPaint;
    private RectF mProRectF;
    private Paint mProgPaint;
    private float mProg;
    private int mPading;
    private Path mPath;
    private int mBgColor;
    private int mPColor;
    private float radio = 5;
    private Matrix mShaderMatric;
    private LinearGradient mLinearGradient;
    private float mTransx;

    public ProgressButton(Context context) {
        super(context);
        init(context);
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mBgColor = Color.GRAY;
        mPColor = Color.BLUE;
        mOutPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mOutPaint.setStyle(Paint.Style.STROKE);
        mOutPaint.setColor(mBgColor);
        mOutPaint.setStrokeWidth(5);
        mProgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgPaint.setColor(mPColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        int height = getHeight();
        mPading = 0;
        mRectF = new RectF(mPading, mPading, mWidth - mPading, height - mPading);
        mProRectF = new RectF(mPading, mPading, mWidth - mPading, height - mPading);
        mPath = new Path();
        mPath.addRoundRect(mRectF, radio, radio, Path.Direction.CCW);

        mLinearGradient = new LinearGradient(0, 0, (mWidth - 2 * mPading), 2 * h,
                new int[]{mPColor, 0x801B79E8, mPColor},
                new float[]{0, 0.2f, 1}, Shader.TileMode.MIRROR);

        mShaderMatric = new Matrix();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.clipPath(mPath);
        canvas.drawPath(mPath, mOutPaint);
        mProRectF.right = (mWidth - 2 * mPading) * mProg + mProRectF.left;
        canvas.drawRoundRect(mProRectF, radio, radio, mProgPaint);
        if (mProg < 1) {
            mProgPaint.setShader(mLinearGradient);
            mTransx += ((mWidth - 2 * mPading) / 10f);
            if (mTransx > 2 * (mWidth - 2 * mPading)) {
                mTransx = -(mWidth - 2 * mPading);
            }
            mShaderMatric.setScale(mProg, 1);
            mShaderMatric.setTranslate(mTransx, 0);
            mLinearGradient.setLocalMatrix(mShaderMatric);
        }
        postInvalidateDelayed(50);
    }

    /**
     * 进度为0--1之间
     *
     * @param progress
     */
    public void setProgress(float progress) {
        mProg = progress;
        mProgPaint.setShader(null);
        postInvalidate();
    }
}
