package com.jonas.yun_library.utils;

import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * @author jiangzuyun.
 * @date 2016/1/27
 * @des [一句话描述]
 * @since [产品/模版版本]
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
