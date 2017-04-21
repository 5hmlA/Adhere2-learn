package com.jonas.yun_library.tablayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * mTabStrip.setShouldExpand(true);
 * mTabStrip.setAllCaps(false);
 * mTabStrip.setTextSize(getDimen(R.dimen.sug_event_textsize));
 * mTabStrip.setTextColor(Color.parseColor("#FB6522"));
 * mTabStrip.setTextColorStateResource(R.drawable.sug_tab_bg);
 * mTabStrip.setDividerColor(Color.parseColor("#FACDB9"));
 * mTabStrip.setDividerPadding(0);
 * mTabStrip.setUnderlineColor(Color.TRANSPARENT);
 * mTabStrip.setIndicatorColor(Color.parseColor("#FCE5DB"));
 * mTabStrip.setIndicatorHeight(getDimen(R.dimen.sug_event_tabheight));
 * ================================
 * radioButton.setButtonDrawable(android.R.color.transparent);   ==  android:button="@null"
 * //代码里面 设置字体颜色状态选择器 selector(ColorStateList)
 * setTextColor(ContextCompat.getColorStateList(getContext(), R.color.textview_selector));
 */

/**
 * @author yun.
 * @date 2016/12/21
 * @des [一句话描述]
 * @since [https://github.com/ZuYun]
 * <p><a href="https://github.com/ZuYun">github</a>
 */
public class JPagerSlidingTabStrip extends HorizontalScrollView {

    public static final int MODE_TOP = 0;
    public static final int MODE_BUTTOM = 1;
    private static final String TAG = JPagerSlidingTabStrip.class.getSimpleName();
    private JTabStyleDelegate mTabStyleDelegate;
    private int scrollOffset;
    private JTabStyle mJTabStyle;
    private int mLastCheckedPosition = -1;

    @IntDef({ MODE_TOP, MODE_BUTTOM }) public @interface TabMode {}

    public interface IconTabProvider {
        /**
         * 如果 返回 null 則調用getPageIconResId
         *
         * @param position 1,简单的背景图片
         * 2，0为checked pressed背景  1为normal背景
         */
        public int[] getPageIconResIds(int position);

        /**
         * 兩個都實現的話 默認使用getPageIconResIds
         */
        @DrawableRes public int getPageIconResId(int position);
    }

    private ViewGroup.LayoutParams defaultTabLayoutParams;
    private LinearLayout.LayoutParams expandedTabLayoutParams;

    private final PageListener pageListener = new PageListener();
    public OnPageChangeListener delegatePageListener;

    private LinearLayout tabsContainer;
    private ViewPager pager;
    private Locale locale;
    private int mTabCount;
    private int lastScrollX = 0;
    private float currentPositionOffset = 0f;
    private List<TextPaint> mTextPaints = new ArrayList<>();


    public JPagerSlidingTabStrip(Context context) {
        this(context, null);
    }


    public JPagerSlidingTabStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public JPagerSlidingTabStrip(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setFillViewport(true);
        setWillNotDraw(false);
        tabsContainer = new LinearLayout(context);
        tabsContainer.setGravity(Gravity.CENTER_VERTICAL);
        tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        tabsContainer.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        addView(tabsContainer);
        defaultTabLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);
        expandedTabLayoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);
        if (locale == null) {
            locale = getResources().getConfiguration().locale;
        }
        mTabStyleDelegate = new JTabStyleDelegate().obtainAttrs(this, attrs, defStyle);
        mJTabStyle = mTabStyleDelegate.getJTabStyle();
    }

    public void setViewPager(ViewPager pager) {
        this.pager = pager;
        if (pager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }

        pager.addOnPageChangeListener(pageListener);

        notifyDataSetChanged();
    }


    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.delegatePageListener = listener;
    }


    public void notifyDataSetChanged() {

        tabsContainer.removeAllViews();
        mTabCount = pager.getAdapter().getCount();
        for (int i = 0; i < mTabCount; i++) {

            if (pager.getAdapter() instanceof IconTabProvider) {
                if (((IconTabProvider) pager.getAdapter()).getPageIconResIds(i) != null) {
                    addIconTab(i, pager.getAdapter().getPageTitle(i).toString(),
                            ((IconTabProvider) pager.getAdapter()).getPageIconResIds(i));
                }
                else {
                    addIconTab(i, pager.getAdapter().getPageTitle(i).toString(),
                            ((IconTabProvider) pager.getAdapter()).getPageIconResId(i));
                }
            }
            else {
                addTextTab(i, pager.getAdapter().getPageTitle(i).toString());
            }
        }
        updateTabStyles();
        check(mTabStyleDelegate.setCurrentPosition(pager.getCurrentItem()));
    }


    private void addTextTab(final int position, String title) {
        addIconTab(position, title, 0);
    }


    private void addIconTab(final int position, String title, @NonNull @Size(min = 1) int... resId) {
        if (TextUtils.isEmpty(title)) {
            Log.e(TAG, "title is null ");
            return;
        }
        CheckedTextView tab = new CheckedTextView(getContext());
        tab.setTextAlignment(TEXT_ALIGNMENT_GRAVITY);
        tab.setGravity(Gravity.CENTER);
        if (!mTabStyleDelegate.isNotDrawIcon()) {
            if (mTabStyleDelegate.getTabMode() == MODE_TOP) {
                if (resId.length > 1) {
                    tab.setBackground(getListDrable(resId));
                }
                else {
                    tab.setBackgroundResource(resId[0]);
                }
            }
            else {
                mTabStyleDelegate.setShouldExpand(true);
                tab.setCompoundDrawablePadding(0);
                tab.setSingleLine();
                if (resId.length > 1) {
                    tab.setCompoundDrawablesWithIntrinsicBounds(null, getListDrable(resId), null, null);
                }
                else {
                    tab.setCompoundDrawablesWithIntrinsicBounds(null,
                            ContextCompat.getDrawable(getContext(), resId[0]), null, null);
                }
            }
        }
        tab.setText(title);
        addTab(position, tab);
        if (mTabStyleDelegate.getCurrentPosition() == 0) {
            pageListener.onPageSelected(0);
        }
    }


    private StateListDrawable getListDrable(@NonNull int... resId) {
        StateListDrawable listDrawable = new StateListDrawable();
        listDrawable.addState(new int[] { android.R.attr.state_checked },
                ContextCompat.getDrawable(getContext(), resId[0]));
        listDrawable.addState(new int[] { android.R.attr.state_pressed },
                ContextCompat.getDrawable(getContext(), resId[0]));
        listDrawable.addState(new int[] {}, ContextCompat.getDrawable(getContext(), resId[1]));
        return listDrawable;
    }


    private void addTab(final int position, View tab) {
        //        tab.setFocusable(true);
        tab.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                pager.setCurrentItem(position);
            }
        });
        tab.setPadding(mTabStyleDelegate.getTabPadding(), 0, mTabStyleDelegate.getTabPadding(), 0);
        tabsContainer.addView(tab, position,
                mTabStyleDelegate.isShouldExpand() ? expandedTabLayoutParams : defaultTabLayoutParams);
    }


    private void updateTabStyles() {

        for (int i = 0; i < mTabCount; i++) {

            View v = tabsContainer.getChildAt(i);
            if (v instanceof TextView) {
                TextView tab = (TextView) v;
                tab.setGravity(Gravity.CENTER);
                tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabStyleDelegate.getTabTextSize());
                tab.setTypeface(mTabStyleDelegate.getTabTypeface(), mTabStyleDelegate.getTabTypefaceStyle());
                if (mTabStyleDelegate.getTabTextColorStateList() == null) {
                    tab.setTextColor(mTabStyleDelegate.getTabTextColor());
                }
                else {
                    tab.setTextColor(mTabStyleDelegate.getTabTextColorStateList());
                }
                // setAllCaps() is only available from API 14, so the upper case is made manually if we are on a
                // pre-ICS-build
                if (mTabStyleDelegate.isTextAllCaps()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                        tab.setAllCaps(true);
                    }
                    else {
                        tab.setText(tab.getText().toString().toUpperCase(locale));
                    }
                }
            }
        }
    }


    private void scrollToChild(int position, int offset) {

        if (mTabCount == 0) {
            return;
        }
        int newScrollX = tabsContainer.getChildAt(position).getLeft() + offset;

        if (position > 0 || offset > 0) {
            newScrollX -= scrollOffset;
        }
        if (newScrollX != lastScrollX) {
            lastScrollX = newScrollX;
            scrollTo(newScrollX, 0);
        }
    }


    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mJTabStyle.onSizeChanged(w, h, oldw, oldh);
    }


    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //        if (isInEditMode() || mTabCount == 0 || mTabMode != MODE_TOP) {
        if (isInEditMode() || mTabCount == 0) {
            return;
        }

        mJTabStyle.onDraw(canvas, tabsContainer, currentPositionOffset, mLastCheckedPosition);
    }


    private class PageListener implements OnPageChangeListener {

        @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            mTabStyleDelegate.setCurrentPosition(position);
            currentPositionOffset = positionOffset;
            scrollToChild(position, (int) (positionOffset * tabsContainer.getChildAt(position).getWidth()));
            invalidate();

            if (delegatePageListener != null) {
                delegatePageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }


        @Override public void onPageScrollStateChanged(int state) {
            mTabStyleDelegate.setState(state);
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                scrollToChild(pager.getCurrentItem(), 0);
            }

            if (delegatePageListener != null) {
                delegatePageListener.onPageScrollStateChanged(state);
            }
        }


        @Override public void onPageSelected(int position) {
            check(position);
            if (delegatePageListener != null) {
                delegatePageListener.onPageSelected(position);
            }
        }
    }


    //单选
    private void check(int position) {
        if (mLastCheckedPosition != -1) {
            ((Checkable) tabsContainer.getChildAt(mLastCheckedPosition)).setChecked(false);
        }
        mLastCheckedPosition = position;
        ((Checkable) tabsContainer.getChildAt(position)).setChecked(true);
    }


    @Override public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        mTabStyleDelegate.setCurrentPosition(savedState.currentPosition);
        requestLayout();
    }


    @Override public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPosition = mTabStyleDelegate.getCurrentPosition();
        return savedState;
    }


    static class SavedState extends BaseSavedState {
        int currentPosition;


        public SavedState(Parcelable superState) {
            super(superState);
        }


        private SavedState(Parcel in) {
            super(in);
            currentPosition = in.readInt();
        }


        @Override public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPosition);
        }


        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }


            @Override public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }


    public JTabStyleDelegate getTabStyleDelegate() {
        return mTabStyleDelegate;
    }


    public void setJTabStyle(JTabStyle JTabStyle) {
        mJTabStyle = JTabStyle;
    }


    public ViewGroup getTabsContainer() {
        return tabsContainer;
    }
}
