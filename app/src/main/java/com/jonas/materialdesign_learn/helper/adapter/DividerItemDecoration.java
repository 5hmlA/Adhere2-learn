package com.jonas.materialdesign_learn.helper.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author 赟.
 * @date 2016/1/27
 * @des [recycleview间隔对象]
 * @since [产品/模版版本]
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private int mDivider;

    public DividerItemDecoration(int divider) {
        mDivider = divider;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildPosition(view) != 0)
            outRect.top = mDivider;
    }
}
