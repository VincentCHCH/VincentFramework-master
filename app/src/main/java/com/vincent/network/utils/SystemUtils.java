package com.vincent.network.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

/**
 * Created by Vincent on 29/3/16.
 */
public class SystemUtils {
    private final static String TAG = "SystemUtils";

    /**
     * 判断应用是否已经启动
     *
     * @param context     一个context
     * @return boolean
     */
    public static boolean isAppAlive(Context context) {
        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = activityManager.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : list) {
            //不能通过判断是否有app包名的actviity，现在的弹框都是用的activity弹框。会出现弹框在的时候，判断为appAlive，导致vieocall 接收不到。
            if (info.topActivity.getClassName().contains("com.honeywell.hch.airtouch.ui.main.ui.common.LoginActivity") ||
                    info.baseActivity.getClassName().contains("com.honeywell.hch.airtouch.ui.main.ui.common.LoginActivity")) {
                return true;
            }
        }
        return false;
    }

    /*
    判断app是否在前台
     */
    public static boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> processInfos = activityManager.getRunningTasks(1);
        if (!processInfos.isEmpty()) {
            ComponentName topActivity = processInfos.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return false;
            }
        }
        return true;
    }

    public static String getPackageVersion(Context ctx){
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                return pi.versionName;
            }
        } catch (Exception e) {
            LogUtil.log(LogUtil.LogLevel.ERROR,TAG, "an error occured when collect package info"+ e);
        }
        return "";
    }


    public static String getLanguage(Context context) {
        String localLanguage = context.getResources().getConfiguration().locale.getLanguage();
        if ("en".equals(localLanguage)){
            return "en_us";
        }else{
            return "zh_cn";
        }
    }

    public static void getLanguages(Context context) {
        Locale[] locales = Locale.getAvailableLocales();
        Map map = new TreeMap();
        for (int i = 0; i < locales.length; i++) {
            map.put(locales[i].getLanguage(), i);
        }
        Set set = map.keySet();
        List<String> languagesList = new ArrayList<>();
        for (Iterator iter = set.iterator(); iter.hasNext(); ) {
            languagesList.add("@" + "\"" + String.valueOf(iter.next()) + "\"");
//                    LogUtil.log(LogUtil.LogLevel.INFO, TAG, "languages" + i + "@\" " + String.valueOf(iter.next()));
//            i++;
        }
        LogUtil.log(LogUtil.LogLevel.INFO, TAG, "languages: " + languagesList.toString());

    }


    /**
     * 获取单个App版本号
     **/
    public static String getAppVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            LogUtil.log(LogUtil.LogLevel.ERROR, TAG, e.toString());
            return "";
        }
    }

    /**
     * 获取手机的MAC地址
     * @return
     */
    public static String getMacAddress()  {
        String address = UUID.randomUUID().toString();
        // 把当前机器上的访问网络接口的存入 Enumeration集合中
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface netWork = interfaces.nextElement();
                // 如果存在硬件地址并可以使用给定的当前权限访问，则返回该硬件地址（通常是 MAC）。
                byte[] by = netWork.getHardwareAddress();
                if (by == null || by.length == 0) {
                    continue;
                }
                StringBuilder builder = new StringBuilder();
                for (byte b : by) {
                    builder.append(String.format("%02X:", b));
                }
                if (builder.length() > 0) {
                    builder.deleteCharAt(builder.length() - 1);
                }
                String mac = builder.toString();
                // 从路由器上在线设备的MAC地址列表，可以印证设备Wifi的 name 是 wlan0
                if ("wlan0".equals(netWork.getName())) {
                    address = mac;
                }
            }
        }catch (Exception e){
            LogUtil.log(LogUtil.LogLevel.ERROR, TAG, e.toString());
        }
        address = address.replaceAll(":","-");
        return address;
    }

    public static void goToPermissionSetting(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        context.startActivity(localIntent);
    }
}
