package com.jonas.yun_library.tablayout;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import static com.jonas.yun_library.tablayout.JPagerSlidingTabStrip.MODE_BUTTOM;
import static com.jonas.yun_library.tablayout.JPagerSlidingTabStrip.MODE_TOP;
import static com.jonas.yun_library.tablayout.JTabStyleBuilder.STYLE_DEFAULT;

/**
 * @author yun.
 * @date 2017/4/21
 * @des [一句话描述]
 * @since [https://github.com/mychoices]
 * <p><a href="https://github.com/mychoices">github</a>
 */
public class JTabStyleDelegate {

    private JPagerSlidingTabStrip mTabStrip;

    /**
     * 是否使用IconTabProvider提供的资源
     * true 不使用
     */
    private boolean mNotDrawIcon = true;
    private ColorStateList mTabTextColorStateList;

    private int mTabMode = MODE_TOP;

    private int mState = -1;
    private boolean mDragRight;
    private int currentPosition = 0;

    private int indicatorColor = 0xFF666666;
    private int underlineColor = 0x1A000000;
    private int dividerColor = 0x1A000000;
    //边框颜色
    private int mFrameColor = Color.TRANSPARENT;

    private boolean shouldExpand = false;
    private boolean textAllCaps = true;
    private int scrollOffset = 52;
    private int indicatorHeight = 8;
    private int underlineHeight = 2;
    private int dividerPadding = 12;
    private int tabPadding = 24;
    private int dividerWidth = 1;

    private int tabTextSize = 11;
    private int tabTextColor = 0xFF666666;
    private Typeface tabTypeface = null;
    private int tabTypefaceStyle = Typeface.NORMAL;
    private int mTabStyle = STYLE_DEFAULT;


    public JTabStyleDelegate obtainAttrs(JPagerSlidingTabStrip tabStrip, AttributeSet attrs, int defStyle) {
        mTabStrip = tabStrip;
        DisplayMetrics dm = tabStrip.getContext().getResources().getDisplayMetrics();
        scrollOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, scrollOffset, dm);
        indicatorHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, indicatorHeight, dm);
        underlineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, underlineHeight, dm);
        dividerPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerPadding, dm);
        tabPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tabPadding, dm);
        dividerWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerWidth, dm);
        tabTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, tabTextSize, dm);

        // get system attrs (android:textSize and android:textColor)

        //		TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);
        //
        //		tabTextSize = a.getDimensionPixelSize(0, tabTextSize);
        //		tabTextColor = a.getColor(1, tabTextColor);
        //
        //		a.recycle();
        //
        //		// get custom attrs
        //
        //		a = context.obtainStyledAttributes(attrs, R.styleable.PagerSlidingTabStrip);
        //
        //		indicatorColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsIndicatorColor, indicatorColor);
        //		underlineColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsUnderlineColor, underlineColor);
        //		dividerColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsDividerColor, dividerColor);
        //		indicatorHeight = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsIndicatorHeight, indicatorHeight);
        //		underlineHeight = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsUnderlineHeight, underlineHeight);
        //		dividerPadding = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsDividerPadding, dividerPadding);
        //		tabPadding = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsTabPaddingLeftRight, tabPadding);
        //		tabBackgroundResId = a.getResourceId(R.styleable.PagerSlidingTabStrip_pstsTabBackground, tabBackgroundResId);
        //		shouldExpand = a.getBoolean(R.styleable.PagerSlidingTabStrip_pstsShouldExpand, shouldExpand);
        //		scrollOffset = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsScrollOffset, scrollOffset);
        //		textAllCaps = a.getBoolean(R.styleable.PagerSlidingTabStrip_pstsTextAllCaps, textAllCaps);
        //
        //		a.recycle();

        return this;
    }


    public boolean isNotDrawIcon() {
        return mNotDrawIcon;
    }


    public ColorStateList getTabTextColorStateList() {
        return mTabTextColorStateList;
    }


    public void setTabTextColorStateList(ColorStateList tabTextColorStateList) {
        mTabTextColorStateList = tabTextColorStateList;
    }


    public int getState() {
        return mState;
    }


    public void setState(int state) {
        mState = state;
    }


    public boolean isDragRight() {
        return mDragRight;
    }


    public void setDragRight(boolean dragRight) {
        mDragRight = dragRight;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }


    public int setCurrentPosition(int currentPosition) {
        return this.currentPosition = currentPosition;
    }


    public int getIndicatorColor() {
        return indicatorColor;
    }


    public void setIndicatorColor(int indicatorColor) {
        this.indicatorColor = indicatorColor;
    }


    public int getUnderlineColor() {
        return underlineColor;
    }


    public void setUnderlineColor(int underlineColor) {
        this.underlineColor = underlineColor;
    }


    public int getDividerColor() {
        return dividerColor;
    }


    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
    }


    public boolean isShouldExpand() {
        return shouldExpand;
    }


    public void setShouldExpand(boolean shouldExpand) {
        this.shouldExpand = shouldExpand;
    }


    public boolean isTextAllCaps() {
        return textAllCaps;
    }


    public void setTextAllCaps(boolean textAllCaps) {
        this.textAllCaps = textAllCaps;
    }

    public void setTextColorStateResource(int resId){
        mTabTextColorStateList = ContextCompat.getColorStateList(mTabStrip.getContext(), resId);
    }
    public int getScrollOffset() {
        return scrollOffset;
    }


    public void setScrollOffset(int scrollOffset) {
        this.scrollOffset = scrollOffset;
    }


    public int getIndicatorHeight() {
        return indicatorHeight;
    }


    public void setIndicatorHeight(int indicatorHeight) {
        this.indicatorHeight = indicatorHeight;
    }


    public int getUnderlineHeight() {
        return underlineHeight;
    }


    public void setUnderlineHeight(int underlineHeight) {
        this.underlineHeight = underlineHeight;
    }


    public int getDividerPadding() {
        return dividerPadding;
    }


    public void setDividerPadding(int dividerPadding) {
        this.dividerPadding = dividerPadding;
    }


    public int getTabPadding() {
        return tabPadding;
    }


    public void setTabPadding(int tabPadding) {
        this.tabPadding = tabPadding;
    }


    public int getDividerWidth() {
        return dividerWidth;
    }


    public void setDividerWidth(int dividerWidth) {
        this.dividerWidth = dividerWidth;
    }


    public int getTabTextSize() {
        return tabTextSize;
    }


    public void setTabTextSize(int tabTextSize) {
        this.tabTextSize = tabTextSize;
    }


    public int getTabTextColor() {
        return tabTextColor;
    }


    public void setTabTextColor(int tabTextColor) {
        this.tabTextColor = tabTextColor;
    }


    public Typeface getTabTypeface() {
        return tabTypeface;
    }


    public void setTabTypeface(Typeface tabTypeface) {
        this.tabTypeface = tabTypeface;
    }


    public int getTabTypefaceStyle() {
        return tabTypefaceStyle;
    }


    public void setTabTypefaceStyle(int tabTypefaceStyle) {
        this.tabTypefaceStyle = tabTypefaceStyle;
    }


    public void setNotDrawIcon(boolean notDrawIcon) {
        mNotDrawIcon = notDrawIcon;
    }

    public int getTabMode() {
        return mTabMode;
    }


    public void setTabMode(@JPagerSlidingTabStrip.TabMode int tabMode) {
        mTabMode = tabMode;
        if (mTabMode == MODE_BUTTOM) {
            mTabStrip.setPadding(0, 8, 0, 8);
        }
    }

    public JTabStyleDelegate setJTabStyle(int tabStyle){
        mTabStyle = tabStyle;
        mTabStrip.setJTabStyle(JTabStyleBuilder.createJTabStyle(mTabStrip,mTabStyle));
        return this;
    }

    public JTabStyleDelegate setJTabStyle(JTabStyle tabStyle){
        mTabStrip.setJTabStyle(tabStyle);
        return this;
    }


    public int getFrameColor() {
        return mFrameColor;
    }


    public void setFrameColor(int frameColor) {
        mFrameColor = frameColor;
    }


    public JTabStyle getJTabStyle() {
        return JTabStyleBuilder.createJTabStyle(mTabStrip,mTabStyle);
    }


    public JPagerSlidingTabStrip getTabStrip() {
        return mTabStrip;
    }

}
