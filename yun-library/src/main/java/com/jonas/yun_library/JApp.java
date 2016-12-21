package com.jonas.yun_library;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.Log;

/**
 * @author yun.
 * @date 2016/12/21
 * @des [一句话描述]
 * @since [https://github.com/ZuYun]
 * <p><a href="https://github.com/ZuYun">github</a>
 */
public class JApp extends Application{

//    @Override
//    public void onCreate() {
//        super.onCreate();
//        registerActivityLifecycleCallbacks();
//    }

    private static final String TAG = JApp.class.getSimpleName();

    private static Context sContext;//静态生命周期和进程一致
    private static String sPackageName;

    public static void init(Context context) {
        sContext = context.getApplicationContext();//最安全，无论是否是acitivity对象最终引用的是application对象，生命周期和进程一直
    }

    public static Context getContext() {
        checkContext();
        return sContext;
    }

//    public static String getPackageName() {
//        checkContext();
//        return sContext.getPackageName();
//    }

    public static PackageInfo getPackageInfo() {
        checkContext();
//        ApplicationInfo applicationInfo = sContext.getApplicationInfo();
//        applicationInfo.icon
//        sContext.getPackageManager().getPackageInfo(getPackageName(),)
        return null;
    }

    private static void checkContext() {
        if (sContext == null) {
            Log.e(TAG, "需要先调用init初始化context");
            throw new RuntimeException("必须 先调用init初始化context");
        }
    }

}
