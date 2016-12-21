package com.jonas.yun_library;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yun.
 * @date 2016/12/21
 * @des [带有 网络状态加载界面的基类]
 * @since [https://github.com/ZuYun]
 * <p><a href="https://github.com/ZuYun">github</a>
 */
public abstract class BaseStateActivity extends Activity {

    protected View mLoadingView;
    private View mErrorView;
    protected ExecutorService mCachedThreadPool = Executors.newCachedThreadPool();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
        if (0 != getLoadingLayoutid()) {
            //子类 设置了 loadinglayoutid则显示
            mLoadingView = LayoutInflater.from(this).inflate(getLoadingLayoutid(), null);
            ((FrameLayout) findViewById(android.R.id.content)).addView(mLoadingView, -1, -1);
            if (setLoadingBackgroundColor() != -1) {
                mLoadingView.setBackgroundColor(setLoadingBackgroundColor());
            }
        }
        mCachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                initViewController();
                initData();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    protected int setLoadingBackgroundColor() {
        return -1;
    }

    /**
     * 显示 加载状态
     */
    protected void showLoading() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏加载状态界面
     */
    protected void finishLoading() {
        if (mLoadingView != null && mLoadingView.getVisibility() == View.VISIBLE) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLoadingView.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

    /**
     * 显示 网络错误界面
     */
    protected void showErrorLayout() {
        if (mLoadingView != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    visibleErrorLayout();
                }
            });
        }
    }

    private void visibleErrorLayout() {
        mLoadingView.setVisibility(View.INVISIBLE);
        if (null == ((FrameLayout) mLoadingView.getParent()).findViewById(getErrorLayoutID())) {
            mErrorView = LayoutInflater.from(this).inflate(getErrorLayoutID(), null);
            ((FrameLayout) mLoadingView.getParent()).addView(mErrorView, -1, -1);
        } else {
            mErrorView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏网络错误界面
     */
    protected void finishErrorLayout() {
        if (mErrorView != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mErrorView.setVisibility(View.INVISIBLE);
                }
            });
        }
    }


    protected int getErrorLayoutID() {
        return R.layout.layout_base_error_;
    }


    protected int getLoadingLayoutid() {
        return 0;
    }

    /**
     * 初始化布局
     **/
    public abstract void initLayout();

    /**
     * 初始化控件 只做findview setlistener
     * 子线程中执行
     **/
    public abstract void initViewController();

    /**
     * 获取数据 子线程
     **/
    public abstract void initData();

    /**
     * 刷新控件
     **/
    public void updateViewController() {
    }

    public int getDimen(int dimRes) {
        return (int) getResources().getDimension(dimRes);
    }

    public int getXmlColor(int colorRes) {
        return getResources().getColor(colorRes);
    }

    /**
     * 数据加载失败 点击页面重新加载数据
     *
     * @param v
     */
    public void retry(View v) {
        if (BuildConfig.DEBUG) Log.d("BaseStateActivity", "重新加载网络数据。");
    }
}
