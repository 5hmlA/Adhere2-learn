package com.jonas.yun_library.tablayout;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import static com.jonas.yun_library.tablayout.JPagerSlidingTabStrip.MODE_BUTTOM;

/**
 * @author yun.
 * @date 2017/4/21
 * @des [一句话描述]
 * @since [https://github.com/mychoices]
 * <p><a href="https://github.com/mychoices">github</a>
 */
public class DefaultTabStyle extends JTabStyle {

    private Paint rectPaint;
    private Paint dividerPaint;
    private float mOutRadio;
    private boolean mDragRight;
    private float mW;
    private float mH;
    private View mCurrentTab;
    private View mNextTab;
    //x:left  y:fight
    private PointF mLinePosition = new PointF(0, 0);
    private int mTabCounts;
    private View mLastTab;


    DefaultTabStyle(JPagerSlidingTabStrip slidingTabStrip) {
        super(slidingTabStrip);
        rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectPaint.setStyle(Paint.Style.FILL);

        dividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dividerPaint.setAntiAlias(true);
        dividerPaint.setStrokeWidth(mTabStyleDelegate.getDividerWidth());
    }


    @Override public void onSizeChanged(int w, int h, int oldw, int oldh) {
        float pading = dp2dip(0.5f);
        mW = w - pading;
        mH = h - pading;
        mTabCounts = mSts.getTabsContainer().getChildCount();
        mLastTab = mSts.getTabsContainer().getChildAt(mTabCounts - 1);
        if (mTabStyleDelegate.getTabMode() == MODE_BUTTOM) {
            mTabStyleDelegate.setIndicatorHeight((int) mH);
        }
    }


    @Override void afterFullContainer(ViewGroup container) {

    }


    @Override
    void onDraw(Canvas canvas, ViewGroup tabsContainer, float currentPositionOffset, int lastCheckedPosition) {

        if (mTabStyleDelegate.getFrameColor() != Color.TRANSPARENT) {
            //画边框
            rectPaint.setStyle(Paint.Style.STROKE);
            rectPaint.setColor(mTabStyleDelegate.getFrameColor());
            rectPaint.setStrokeWidth(dp2dip(1));
            canvas.drawRoundRect(dp2dip(0.5f), dp2dip(0.5f), mLastTab.getRight(), this.mH, mOutRadio,
                    mOutRadio, rectPaint);
        }

        if (mTabStyleDelegate.getIndicatorColor() != Color.TRANSPARENT) {
            // draw indicator line
            rectPaint.setColor(mTabStyleDelegate.getIndicatorColor());
            // default: line below current tab
            mCurrentTab = tabsContainer.getChildAt(mTabStyleDelegate.getCurrentPosition());
            mLinePosition.x = mCurrentTab.getLeft();
            mLinePosition.y = mCurrentTab.getRight();

            calcuteIndicatorLinePosition(tabsContainer, currentPositionOffset, lastCheckedPosition);
            rectPaint.setStyle(Paint.Style.FILL);
            //draw indicator
            canvas.drawRect(mLinePosition.x, mH - mTabStyleDelegate.getIndicatorHeight(), mLinePosition.y, mH,
                    rectPaint);
        }

        // draw underline
        rectPaint.setColor(mTabStyleDelegate.getUnderlineColor());
        canvas.drawRect(0, mH - mTabStyleDelegate.getUnderlineColor(), tabsContainer.getWidth(), mH,
                rectPaint);
        if (mTabStyleDelegate.getDividerColor() != Color.TRANSPARENT) {
            // draw divider
            dividerPaint.setColor(mTabStyleDelegate.getDividerColor());
            for (int i = 0; i < tabsContainer.getChildCount() - 1; i++) {
                View tab = tabsContainer.getChildAt(i);
                canvas.drawLine(tab.getRight(), mTabStyleDelegate.getDividerPadding(), tab.getRight(),
                        mH - mTabStyleDelegate.getDividerPadding(), dividerPaint);
            }
        }
    }


    protected void calcuteIndicatorLinePosition(ViewGroup tabsContainer, float currentPositionOffset, int
            lastCheckedPosition) {
        // if there is an offset, start interpolating left and right coordinates between current and next tab
        if (currentPositionOffset > 0f &&
                mTabStyleDelegate.getCurrentPosition() < tabsContainer.getChildCount() - 1) {

            mNextTab = tabsContainer.getChildAt(mTabStyleDelegate.getCurrentPosition() + 1);
            final float nextTabLeft = mNextTab.getLeft();
            final float nextTabRight = mNextTab.getRight();
            //moveStyle_normal(currentPositionOffset, nextTabLeft, nextTabRight);

            moveStyle_sticky(currentPositionOffset, lastCheckedPosition, nextTabLeft, nextTabRight);
        }
    }


    private void moveStyle_normal(float currentPositionOffset, float nextTabLeft, float nextTabRight) {
        mLinePosition.x = (currentPositionOffset * nextTabLeft +
                (1f - currentPositionOffset) * mLinePosition.x);
        mLinePosition.y = (currentPositionOffset * nextTabRight +
                (1f - currentPositionOffset) * mLinePosition.y);
    }


    protected void moveStyle_sticky(float currentPositionOffset, int lastCheckedPosition, float nextTabLeft, float
            nextTabRight) {
        if (mTabStyleDelegate.getState() == ViewPager.SCROLL_STATE_DRAGGING ||
                mTabStyleDelegate.getState() == ViewPager.SCROLL_STATE_IDLE) {
            if (lastCheckedPosition == mTabStyleDelegate.getCurrentPosition()) {
                mDragRight = true;
                //Log.d(TAG, "往右 ------>> ");
            }
            else {
                mDragRight = false;
                //Log.d(TAG, "往左 <<------");
            }
        }
        if (mDragRight) {
            //                ------>>
            if (currentPositionOffset >= 0.5) {
                mLinePosition.x = (
                        2 * (nextTabLeft - mLinePosition.x) * currentPositionOffset + 2 * mLinePosition.x -
                                nextTabLeft);
            }
            mLinePosition.y = (currentPositionOffset * nextTabRight +
                    (1f - currentPositionOffset) * mLinePosition.y);
        }
        else {
            //                <<------
            mLinePosition.x = (currentPositionOffset * nextTabLeft +
                    (1f - currentPositionOffset) * mLinePosition.x);
            if (currentPositionOffset <= 0.5) {
                mLinePosition.y = (2 * (nextTabRight - mLinePosition.y) * currentPositionOffset +
                        mLinePosition.y);
            }
            else {
                mLinePosition.y = nextTabRight;
            }
        }
    }
}
