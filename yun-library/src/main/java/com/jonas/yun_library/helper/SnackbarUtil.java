package com.jonas.yun_library.helper;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author yun.
 * @date 2016/12/21
 * @des [一句话描述]
 * @since [https://github.com/ZuYun]
 * <p><a href="https://github.com/ZuYun">github</a>
 */
public class SnackbarUtil {
    private Snackbar mSnackbar;
    private final Snackbar.SnackbarLayout mSnackbarLayout;
    private final TextView mMessageView;
    private final Button mActionView;

    private SnackbarUtil(Snackbar snackbar) {
        mSnackbar = snackbar;
        mSnackbarLayout = (Snackbar.SnackbarLayout) mSnackbar.getView();
        mMessageView = (TextView) mSnackbarLayout.findViewById(android.support.design.R.id.snackbar_text);
        mActionView = (Button) mSnackbarLayout.findViewById(android.support.design.R.id.snackbar_action);
    }

    public static SnackbarUtil create(Snackbar snackbar) {
        return new SnackbarUtil(snackbar);
    }

    public SnackbarUtil setBackground(int bgColor) {
        mSnackbarLayout.setBackgroundColor(bgColor);
        return this;
    }

    public SnackbarUtil setMessageViewColor(int bgColor) {
        mMessageView.setTextColor(bgColor);
        return this;
    }
    public SnackbarUtil setActionViewColor(int bgColor) {
        mActionView.setTextColor(bgColor);
        return this;
    }
    public SnackbarUtil addCustomView(View view,int position) {
        mSnackbarLayout.addView(view,position);
        return this;
    }

    public TextView getMessageView() {
        return mMessageView;
    }

    public Button getActionView() {
        return mActionView;
    }
}
