package com.jonas.yun_library.helper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.UUID;

/**
 * @author jiangzuyun.
 * @date 2016/1/27
 * @des [获取手机相关信息]
 * @since [产品/模版版本]
 */

public class PhoneUtill {
    /**
     * 通过android.os.Build 类，可以直接获得一些 Build 提供的系统信息。Build类包含了系统编译时，--编译时哦--的大量设备、配置信息，下面 列举一些常用的信息：
     * Build.BOARD : 主板 :BalongV9R1
     * Build.BRAND : 系统定制商： Huawei
     * Build.DEVICE : 设备参数：hw7d501l
     * Build.DISPLAY : 显示屏参数：7D-503LV100R002C598B003
     * Build.FINGERPRINT : 唯一编号：Huawei/MediaPad/hw7d501l:4.4.2/HuaweiMediaPad/7D503LV1R2C598B003:user/release-keys
     * Build.SERIAL : 硬件序列号：T7K6R14727000194
     * Build.ID : 修订版本列表：HuaweiMediaPad
     * Build.MANUFACTURER : 硬件制造商：HUAWEI
     * Build.MODEL : 版本 ：X1 7.0
     * Build.HARDWARE : 硬件名 ：hw7d501l
     * Build.PRODUCT : 手机产品名 ：MediaPad
     * Build.TAGS : 描述Build的标签 ：release-keys
     * Build.TYPE : Builder 类型 ：user
     * Build.CODENAME : 当前开发版本号 ：REL
     * Build.INCREMENTAL : 源码控制版本号 ：C598B003
     * Build.RELEASE : 版本字符串 ：4.4.2
     * Build.SDK_INT : 版本号 ：19
     * Build.HOST : Host值 ：screen2
     * Build.USER : User名 ：jslave
     * Build.TIME : 编译时间 ：1419969480000
     * SystemProperty 包含了许多系统配置属性值和参数，很多信息与上面通过android.os.Build 获取的值是相同的，下面同样列举了一些常用的信息：
     * System.getProperty("os.version") : OS版本 ：3.0.8-g0f59686
     * System.getProperty("os.name") : OS名称 ：Linux
     * System.getProperty("os.arch") : OS架构 ：armv7l
     * System.getProperty("user.home") : Home属性 ：/
     * System.getProperty("user.name") : Name属性 ：root
     * System.getProperty("user.dir") : Dir属性 ：/
     * System.getProperty("user.timezone") : 时区 ：null
     * System.getProperty("path.separator") : 路径分隔符 ：:
     * System.getProperty("line.separator") : 行分隔符 ：
     * System.getProperty("file.separator") : 文件分隔符 ：/
     * System.getProperty("java.vendor.url") : Java vender URL 属性：http://www.android.com/
     * System.getProperty("java.class.path") : Java Class 路径 ：.
     * System.getProperty("java.class.version") : Java Class 版本 ：50.0
     * System.getProperty("java.vendor") : Java Vender属性 ：The Android Project
     * System.getProperty("java.version") : Java 版本 ：0
     * System.getProperty("java.home") : Java Home属性 ：/system
     */

    private static final String LOG_TAG = "PhoneUtill";

    /**
     * 检查是否是MIUI
     *
     * @return boolean
     */
    public static boolean isMIUI(){
        boolean isMIUI = false;
        FileInputStream fileInputStream = null;
        try {
            Properties prop = new Properties();
            File file = new File(Environment.getRootDirectory(), "build.prop");
            fileInputStream = new FileInputStream(file);
            prop.load(fileInputStream);
            isMIUI = prop.getProperty("ro.miui.ui.version.code", null) != null ||
                    prop.getProperty("ro.miui.ui.version.name", null) != null ||
                    prop.getProperty("ro.miui.internal.storage", null) != null;
        }catch(Exception e) {
            Log.e(LOG_TAG, "isMIUI IOException->"+e.getMessage());
        }finally {
            if(fileInputStream != null) {
                try {
                    fileInputStream.close();
                }catch(Exception e) {
                    Log.e(LOG_TAG, "isMIUI Close InputStream IOException->"+e.getMessage());
                }
            }
        }
        return isMIUI;
    }

    public static String getDeviceID(Context context){
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static int getStatusBarHeight(){
        Resources system = Resources.getSystem();
        int resourceId = system.getIdentifier("status_bar_height", "dimen", "android");
        return system.getDimensionPixelSize(resourceId);
    }

    /**
     * 通過res下的圖片資源名字獲取圖片
     *
     * @param context
     * @param resName
     * @return
     */
    public static Drawable getResDrawable(Context context, String resName){
        int drawableId = context.getResources().getIdentifier(resName, "drawable", context.getPackageName());
        return context.getResources().getDrawable(drawableId);
    }

    public static Drawable getResString(Context context, String resName){
        int drawableId = context.getResources().getIdentifier(resName, "string", context.getPackageName());
        return context.getResources().getDrawable(drawableId);
    }

    //获得独一无二的Psuedo ID
    public static String getUniquePsuedoID(){
        String serial = null;

        String m_szDevIDShort = "35"+
                Build.BOARD.length()%10+Build.BRAND.length()%10+

                Build.CPU_ABI.length()%10+Build.DEVICE.length()%10+

                Build.DISPLAY.length()%10+Build.HOST.length()%10+

                Build.ID.length()%10+Build.MANUFACTURER.length()%10+

                Build.MODEL.length()%10+Build.PRODUCT.length()%10+

                Build.TAGS.length()%10+Build.TYPE.length()%10+

                Build.USER.length()%10; //13 位

        try {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();
            //API>=9 使用serial号
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        }catch(Exception exception) {
            //serial需要一个初始化
            serial = "serial"; // 随便一个初始化
        }
        //使用硬件信息拼凑出来的15位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }
}
