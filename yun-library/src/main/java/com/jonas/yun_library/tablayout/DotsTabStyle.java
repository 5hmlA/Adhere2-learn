package com.jonas.yun_library.tablayout;

import android.graphics.Canvas;
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
public class DotsTabStyle extends JTabStyle {

    private Paint bgPaint;
    private Paint mIndicatorPaint;
    private float mW;
    private float mH;
    private View mCurrentTab;
    private float dosRadio = 10;
    private View mNextTab;
    //x:left  y:fight
    private PointF mLinePosition = new PointF(0, 0);
    private boolean mDragRight;
    private float mTabWidth;


    public DotsTabStyle(JPagerSlidingTabStrip slidingTabStrip) {
        super(slidingTabStrip);
        mTabStyleDelegate.setShouldExpand(true);
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(mTabStyleDelegate.getDividerColor());

        mIndicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setStyle(Paint.Style.FILL);
        mIndicatorPaint.setColor(mTabStyleDelegate.getIndicatorColor());
        mIndicatorPaint.setStrokeWidth(mTabStyleDelegate.getDividerWidth());
        //dosRadio = mTabStyleDelegate.getDividerWidth();
    }


    @Override public void onSizeChanged(int w, int h, int oldw, int oldh) {
        float pading = dp2dip(0.5f);
        mW = w - pading;
        mH = h - pading;

        if (mTabStyleDelegate.getTabMode() == MODE_BUTTOM) {
            mTabStyleDelegate.setIndicatorHeight((int) mH);
        }

        mTabWidth = mW / mTabStyleDelegate.getTabStrip().getTabsContainer().getChildCount();
    }


    @Override void afterFullContainer(ViewGroup container) {

    }


    @Override
    public void onDraw(Canvas canvas, ViewGroup tabsContainer, float currentPositionOffset, int lastCheckedPosition) {

        tabsContainer.setVisibility(View.INVISIBLE);
        // draw indicator line
        // default: line below current tab
        mCurrentTab = tabsContainer.getChildAt(mTabStyleDelegate.getCurrentPosition());
        mLinePosition.x = mCurrentTab.getLeft();
        mLinePosition.y = mCurrentTab.getRight();
        calcuteIndicatorLinePosition(tabsContainer, currentPositionOffset,lastCheckedPosition);
        //draw indicator
        canvas.drawRoundRect(mLinePosition.x+mTabWidth/2-dosRadio, mH/2-dosRadio, mLinePosition
                        .y-mTabWidth/2+dosRadio, mH/2+dosRadio,dosRadio,dosRadio, mIndicatorPaint);

        //画默认圆
        for (int i = 0; i < tabsContainer.getChildCount(); i++) {
            canvas.drawCircle(mTabWidth/2+mTabWidth*i,mH/2,dosRadio,bgPaint);
        }
    }

    private void calcuteIndicatorLinePosition(ViewGroup tabsContainer, float currentPositionOffset, int
            lastCheckedPosition){
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
        mLinePosition.x = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset) * mLinePosition.x);
        mLinePosition.y = (currentPositionOffset * nextTabRight + (1f - currentPositionOffset) * mLinePosition.y);
    }


    private void moveStyle_sticky(float currentPositionOffset, int lastCheckedPosition, float nextTabLeft, float nextTabRight) {
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
                mLinePosition.x = (2 * (nextTabLeft - mLinePosition.x) * currentPositionOffset + 2 * mLinePosition.x -
                        nextTabLeft);
            }
            mLinePosition.y = (currentPositionOffset * nextTabRight + (1f - currentPositionOffset) * mLinePosition.y);
        }
        else {
            //                <<------
            mLinePosition.x = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset) * mLinePosition.x);
            if (currentPositionOffset <= 0.5) {
                mLinePosition.y = (2 * (nextTabRight - mLinePosition.y) * currentPositionOffset + mLinePosition.y);
            }
            else {
                mLinePosition.y = nextTabRight;
            }
        }
    }

}
