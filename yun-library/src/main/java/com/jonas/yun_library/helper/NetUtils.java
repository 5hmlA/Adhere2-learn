package com.jonas.yun_library.helper;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * @author yun.
 * @date 2016/12/21
 * @des [一句话描述]
 * @since [https://github.com/ZuYun]
 * <p><a href="https://github.com/ZuYun">github</a>
 */
public class NetUtils {

    private static String mUserAgent = null;

    public static String getUserAgent(String appName, Context context) {
        if (mUserAgent == null) {
            mUserAgent = appName;
            try {
                String packageName = context.getPackageName();
                String version = context.getPackageManager().getPackageInfo(packageName, 0).versionName;
                mUserAgent = mUserAgent + " (" + packageName + "/" + version + ")";
            } catch (PackageManager.NameNotFoundException e) {
            }
        }
        return mUserAgent;
    }
}
