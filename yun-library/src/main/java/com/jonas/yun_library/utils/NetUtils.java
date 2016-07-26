package com.jonas.yun_library.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

/**
 * @author jiangzuyun.
 * @date 2016/6/22
 * @des [一句话描述]
 * @since [产品/模版版本]
 */

public class NetUtils {
    /**
     * 哪个页面需要intent传参数 哪个页面自己定义常量
     *
     * Android软件时，常常需要打开系统设置或信息界面，来设置相关系统项或查看系统的相关信息，这时我们就可以使用以下语句来实现：（如打开“无线和网络设置”界面）
     　　Intent intent = new Intent("/");
     　　ComponentName cm = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
     　　intent.setComponent(cm);
     　　intent.setAction("android.intent.action.VIEW");
     　　activity.startActivityForResult( intent , 0);
     　　经过测试，使用下面字段可以在软件中直接打开相应的系统界面
     　　com.android.settings.AccessibilitySettings 辅助功能设置
     　　com.android.settings.ActivityPicker 选择活动
     　　com.android.settings.ApnSettings APN设置
     　　com.android.settings.ApplicationSettings 应用程序设置
     　　com.android.settings.BandMode 设置GSM/UMTS波段
     　　com.android.settings.BatteryInfo 电池信息
     　　com.android.settings.DateTimeSettings 日期和坝上旅游网时间设置
     　　com.android.settings.DateTimeSettingsSetupWizard 日期和时间设置
     　　com.android.settings.DevelopmentSettings 应用程序设置=》开发设置
     　　com.android.settings.DeviceAdminSettings 设备管理器
     　　com.android.settings.DeviceInfoSettings 关于手机
     　　com.android.settings.Display 显示——设置显示字体大小及预览
     　　com.android.settings.DisplaySettings 显示设置
     　　com.android.settings.DockSettings 底座设置
     　　com.android.settings.IccLockSettings SIM卡锁定设置
     　　com.android.settings.InstalledAppDetails 语言和键盘设置
     　　com.android.settings.LanguageSettings 语言和键盘设置
     　　com.android.settings.LocalePicker 选择手机语言
     　　com.android.settings.LocalePickerInSetupWizard 选择手机语言
     　　com.android.settings.ManageApplications 已下载（安装）软件列表
     　　com.android.settings.MasterClear 恢复出厂设置
     　　com.android.settings.MediaFormat 格式化手机闪存
     　　com.android.settings.PhysicalKeyboardSettings 设置键盘
     　　com.android.settings.PrivacySettings 隐私设置
     　　com.android.settings.ProxySelector 代理设置
     　　com.android.settings.RadioInfo 手机信息
     　　com.android.settings.RunningServices 正在运行的程序（服务）
     　　com.android.settings.SecuritySettings 位置和安全设置
     　　com.android.settings.Settings 系统设置
     　　com.android.settings.SettingsSafetyLegalActivity 安全信息
     　　com.android.settings.SoundSettings 声音设置
     　　com.android.settings.TestingSettings 测试——显示手机信息、电池信息、使用情况统计、Wifi information、服务信息
     　　com.android.settings.TetherSettings 绑定与便携式热点
     　　com.android.settings.TextToSpeechSettings 文字转语音设置
     　　com.android.settings.UsageStats 使用情况统计
     　　com.android.settings.UserDictionarySettings 用户词典
     　　com.android.settings.VoiceInputOutputSettings 语音输入与输出设置
     　　com.android.settings.WirelessSettings 无线和网络设置
     */

    /**
     * 意图跳转
     * <p>先判断 intent是否能正常跳转
     * @param context
     */
    public static void startActivitySafe(@NonNull Context context, Class dest){
        Intent intent = new Intent(context, dest);
        //判断 改intent跳转的目标是否存在
        boolean activityExists = intent.resolveActivityInfo(context.getPackageManager(), 0) != null;
        if (activityExists) {
            context.startActivity(intent);
        }
    }
    /**
     * 意图跳转  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
     * <p>先判断 intent是否能正常跳转
     * @param context
     */
    public static void startActivityWFlag(@NonNull Context context, Class dest){
        Intent intent = new Intent(context, dest);
        //判断 改intent跳转的目标是否存在
        boolean activityExists = intent.resolveActivityInfo(context.getPackageManager(), 0) != null;
        if (activityExists) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

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
