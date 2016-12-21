package com.jonas.yun_library.adapter;

import android.content.Context;

/**
 * @author yun.
 * @date 2016/7/7
 * @des [recycleview的holder 包括填充数据]
 * @since [https://github.com/ZuYun]
 * <p><a href="https://github.com/ZuYun">github</a>
 */

public abstract class BaseAwesCard {

    //弹出对话框的时候需要
//    private Activity mActivity;
//
//    public BaseAwesCard(@NonNull Activity activity) {
//        mActivity = activity;
//    }

    /**
     * 创建holder
     * @return
     * @param context
     */
    public abstract RecyclerHolder getRecyclerHolder(Context context);

    /**
     * 填充数据
     * holder可见的时候被调用
     * @param holder
     * @param position
     */
    public abstract void holderConvert(RecyclerHolder holder, int position);
}
