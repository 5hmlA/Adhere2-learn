package com.jonas.yun_library.helper;

import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * @author yun.
 * @date 2016/12/21
 * @des [一句话描述]
 * @since [https://github.com/ZuYun]
 * <p><a href="https://github.com/ZuYun">github</a>
 */
public class PicUtil {

    public static void loadImage(String url, ImageView view) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(view.getContext()).load(url).centerCrop().into(view);
        }
    }

    public static void loadImage(String url, ImageView view, int reWidth, int reHeight) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(view.getContext()).load(url).resize(reWidth, reHeight).centerCrop().into(view);
        }
    }

    public static void loadImage(String url, ImageView view, int resPlace, int resError, int reWidth, int reHeight) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(view.getContext()).load(url).placeholder(resPlace).error(resError).resize(reWidth, reHeight).centerCrop().into(view);
        }
    }

    public static void loadAssetImage(String assetPath, ImageView view) {
        if (!TextUtils.isEmpty(assetPath)) {
            Picasso.with(view.getContext()).load("file:///android_asset/" + assetPath).centerCrop().into(view);
        }
    }

    public static void loadAssetImage(String assetPath, ImageView view, int reWidth, int reHeight) {
        if (!TextUtils.isEmpty(assetPath)) {
            Picasso.with(view.getContext()).load("file:///android_asset/" + assetPath).resize(reWidth, reHeight).centerCrop().into(view);
        }
    }
}
