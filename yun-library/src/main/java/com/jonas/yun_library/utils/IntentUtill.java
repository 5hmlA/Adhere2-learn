package com.jonas.yun_library.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

/**
 * @author jiangzuyun.
 * @date 2016/7/1
 * @des [一句话描述]
 * @since [产品/模版版本]
 */

public class IntentUtill {

    //1.拨打电话
    public static void call(@NonNull Context cx, int num) {
        Uri uri = Uri.parse("tel:" + num);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        cx.startActivity(intent);
    }


    //2.发送短信
    public static void sendSms(@NonNull Context cx, int num, @NonNull String msg) {
        Uri uri = Uri.parse("smsto:" + num);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", msg);
        cx.startActivity(intent);

    }


    /**
     * 发送彩信（相当于发送带附件的短信）
     *
     * @param cx
     * @param num
     * @param msg
     * @param uri
     * @param type "image/png"
     */
    public static void sendSms(@NonNull Context cx, int num, @NonNull String msg, Uri uri, String type) {
        Uri uriTo = Uri.parse("smsto:" + num);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uriTo);
//        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra("sms_body", msg);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType(type);
        cx.startActivity(intent);
    }

    //4.打开浏览器:
    public static void openWebView(@NonNull Context cx, @NonNull String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        cx.startActivity(intent);
    }

//    //5.发送电子邮件:(阉割了Google服务的没戏!!!!)
//// 给someone@domain.com发邮件
//    Uri uri = Uri.parse("mailto:someone@domain.com");
//    Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
//
//    cx.startActivity(intent);

//    // 给someone@domain.com发邮件发送内容为“Hello”的邮件
//    Intent intent = new Intent(Intent.ACTION_SEND);
//    intent.putExtra(Intent.EXTRA_EMAIL,"someone@domain.com");
//    intent.putExtra(Intent.EXTRA_SUBJECT,"Subject");
//    intent.putExtra(Intent.EXTRA_TEXT,"Hello");
//    intent.setType("text/plain");
//
//    cx.startActivity(intent);

//    // 给多人发邮件
//    Intent intent = new Intent(Intent.ACTION_SEND);
//    String[] tos = {"1@abc.com", "2@abc.com"}; // 收件人
//    String[] ccs = {"3@abc.com", "4@abc.com"}; // 抄送
//    String[] bccs = {"5@abc.com", "6@abc.com"}; // 密送
//    intent.putExtra(Intent.EXTRA_EMAIL,tos);
//    intent.putExtra(Intent.EXTRA_CC,ccs);
//    intent.putExtra(Intent.EXTRA_BCC,bccs);
//    intent.putExtra(Intent.EXTRA_SUBJECT,"Subject");
//    intent.putExtra(Intent.EXTRA_TEXT,"Hello");
//    intent.setType("message/rfc822");
//
//    cx.startActivity(intent);

    /**
     * 打开Google地图中国北京位置（北纬39.9，东经116.3）
     *
     * @param cx
     * @param coordinate "39.9 116.3"
     */
    public static void showPositionAtGoogleMap(@NonNull Context cx, String coordinate) {
        Uri uri = Uri.parse("geo:" + coordinate);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        cx.startActivity(intent);
    }

    /**
     * 路径规划：从北京某地（北纬39.9，东经116.3）到上海某地（北纬31.2，东经121.4）
     *
     * @param cx
     * @param from "39.9 116.3"
     * @param to   "31.2 121.4"
     */
    public static void showPositionAtGoogleMap(@NonNull Context cx, String from, String to) {
        Uri uri = Uri.parse("http://maps.google.com/maps?f=d&saddr=" + from + "&daddr=" + to);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        cx.startActivity(intent);
    }

    //8.多媒体播放:

    /**
     * @param cx
     * @param path "sdcard/foo.mp3"
     * @param type "audio/mp3"
     */
    public static void playMedia(@NonNull Context cx, String path, String type) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("file:///" + path);
        intent.setDataAndType(uri, type);
        cx.startActivity(intent);
    }

    /**
     * 获取SD卡下所有音频文件,然后播放第一首=-=
     *
     * @param cx
     */
    public static void openAllaudios(@NonNull Context cx) {
        Uri uri = Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "1");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        cx.startActivity(intent);
    }

    /**
     * 打开拍照程序
     *
     * @param cx
     * @param requestCode <pre>
     *                                                                                    // 取出照片数据
     *                                                                             Bundle extras = intent.getExtras();
     *                                                                             Bitmap bitmap = (Bitmap) extras.get("data");
     *                                                                              </pre>
     */
    public static void takePicForResult(@NonNull Activity cx, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cx.startActivityForResult(intent, requestCode);
    }


//    //另一种:
////调用系统相机应用程序，并存储拍下来的照片
//    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//    time=Calendar.getInstance().
//
//    getTimeInMillis();
//
//    intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new
//
//    File(Environment
//                 .getExternalStorageDirectory()
//
//    .
//
//    getAbsolutePath()
//
//    +"/tucue",time+".jpg")));
//
//    cx.startActivityForResult(intent,ACTIVITY_GET_CAMERA_IMAGE);

//    //10.获取并剪切图片
//// 获取并剪切图片
//    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//    intent.setType("image/*");
//    intent.putExtra("crop","true"); // 开启剪切
//    intent.putExtra("aspectX",1); // 剪切的宽高比为1：2
//    intent.putExtra("aspectY",2);
//    intent.putExtra("outputX",20); // 保存图片的宽和高
//    intent.putExtra("outputY",40);
//    intent.putExtra("output",Uri.fromFile(new
//
//    File("/mnt/sdcard/temp")
//
//    )); // 保存路径
//    intent.putExtra("outputFormat","JPEG");// 返回格式
//
//    cx.startActivityForResult(intent,0);

//    // 剪切特定图片
//    Intent intent = new Intent("com.android.camera.action.CROP");
//    intent.setClassName("com.android.camera","com.android.camera.CropImage");
//    intent.setData(Uri.fromFile(new
//
//    File("/mnt/sdcard/temp")
//
//    ));
//    intent.putExtra("outputX",1); // 剪切的宽高比为1：2
//    intent.putExtra("outputY",2);
//    intent.putExtra("aspectX",20); // 保存图片的宽和高
//    intent.putExtra("aspectY",40);
//    intent.putExtra("scale",true);
//    intent.putExtra("noFaceDetection",true);
//    intent.putExtra("output",Uri.parse("file:///mnt/sdcard/temp"));
//
//    cx.startActivityForResult(intent,0);

//    //11.打开Google Market
//// 打开Google Market直接进入该程序的详细页面
//    Uri uri = Uri.parse("market://details?id=" + "com.demo.app");
//    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//
//    cx.startActivity(intent);

    /**
     * 进入手机设置界面:android.provider.Settings.ACTION_WIRELESS_SETTINGS
     *
     * @param cx
     * @param page        see {@link android.provider.Settings}
     *                    <p>android.provider.Settings.ACTION_WIRELESS_SETTINGS</p>
     * @param requestCode <p>
     *                      <li>com.android.settings.AccessibilitySettings 辅助功能设置
     *                    　<li>com.android.settings.ActivityPicker 选择活动
     *                    　<li>com.android.settings.ApnSettings APN设置
     *                    　<li>com.android.settings.ApplicationSettings 应用程序设置
     *                    　<li>com.android.settings.BandMode 设置GSM/UMTS波段
     *                    　<li>com.android.settings.BatteryInfo 电池信息
     *                    　<li>com.android.settings.DateTimeSettings 日期和坝上旅游网时间设置
     *                    　<li>com.android.settings.DateTimeSettingsSetupWizard 日期和时间设置
     *                    　<li>com.android.settings.DevelopmentSettings 应用程序设置=》开发设置
     *                    　<li>com.android.settings.DeviceAdminSettings 设备管理器
     *                    　<li>com.android.settings.DeviceInfoSettings 关于手机
     *                    　<li>com.android.settings.Display 显示——设置显示字体大小及预览
     *                    　<li>com.android.settings.DisplaySettings 显示设置
     *                    　<li>com.android.settings.DockSettings 底座设置
     *                    　<li>com.android.settings.IccLockSettings SIM卡锁定设置
     *                    　<li>com.android.settings.InstalledAppDetails 语言和键盘设置
     *                    　<li>com.android.settings.LanguageSettings 语言和键盘设置
     *                    　<li>com.android.settings.LocalePicker 选择手机语言
     *                    　<li>com.android.settings.LocalePickerInSetupWizard 选择手机语言
     *                    　<li>com.android.settings.ManageApplications 已下载（安装）软件列表
     *                    　<li>com.android.settings.MasterClear 恢复出厂设置
     *                    　<li>com.android.settings.MediaFormat 格式化手机闪存
     *                    　<li>com.android.settings.PhysicalKeyboardSettings 设置键盘
     *                    　<li>com.android.settings.PrivacySettings 隐私设置
     *                    　<li>com.android.settings.ProxySelector 代理设置
     *                    　<li>com.android.settings.RadioInfo 手机信息
     *                    　<li>com.android.settings.RunningServices 正在运行的程序（服务）
     *                    　<li>com.android.settings.SecuritySettings 位置和安全设置
     *                    　<li>com.android.settings.Settings 系统设置
     *                    　<li>com.android.settings.SettingsSafetyLegalActivity 安全信息
     *                    　<li>com.android.settings.SoundSettings 声音设置
     *                    　<li>com.android.settings.TestingSettings 测试——显示手机信息、电池信息、使用情况统计、Wifi information、服务信息
     *                    　<li>com.android.settings.TetherSettings 绑定与便携式热点
     *                    　<li>com.android.settings.TextToSpeechSettings 文字转语音设置
     *                    　<li>com.android.settings.UsageStats 使用情况统计
     *                    　<li>com.android.settings.UserDictionarySettings 用户词典
     *                    　<li>com.android.settings.VoiceInputOutputSettings 语音输入与输出设置
     *                    　<li>com.android.settings.WirelessSettings 无线和网络设置
     *                    </p>
     */
    public static void toSettingPage(@NonNull Activity cx, String page, int requestCode) {
        Intent intent = new Intent(page);
        cx.startActivityForResult(intent, requestCode);
    }

    /**
     * 安装apk:
     *
     * @param cx
     * @param packagename
     */
    public static void installApk(Context cx, String packagename) {
        Uri installUri = Uri.fromParts("package", packagename, null);
        cx.startActivity(new Intent(Intent.ACTION_PACKAGE_ADDED, installUri));

    }

    /**
     * 卸载apk:
     *
     * @param cx
     * @param packagename
     */
    public static void unInstallApk(Context cx, String packagename) {
        Uri uri = Uri.fromParts("package", packagename, null);
        Intent it = new Intent(Intent.ACTION_DELETE, uri);

        cx.startActivity(it);
    }
//    //15.发送附件:
//    Intent it = new Intent(Intent.ACTION_SEND);
//    it.putExtra(Intent.EXTRA_SUBJECT,"The email subject text");
//    it.putExtra(Intent.EXTRA_STREAM,"file:///sdcard/eoe.mp3");
//    sendIntent.setType("audio/mp3");
//
//    cx.startActivity(Intent.createChooser(it,"Choose Email Client")
//
//            );

    /**
     * 进入联系人页面
     *
     * @param cx
     * @param packagename
     */
    public static void toContantPage(Context cx, String packagename) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Contacts.People.CONTENT_URI);
        cx.startActivity(intent);
    }

//    //17.查看指定联系人:
//    Uri personUri = ContentUris.withAppendedId(Contacts.People.CONTENT_URI, info.id);//info.id联系人ID
//    Intent intent = new Intent();
//    intent.setAction(Intent.ACTION_VIEW);
//    intent.setData(personUri);
//    cx.startActivity(intent);
}
