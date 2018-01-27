package com.vincent.framework.utils.log;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import com.vincent.framework.LibApplication;
import com.vincent.framework.utils.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuyuan on 2/22/16.
 */
public class LogHandlerUtil {

    private static final String TAG = "LogHandler";

    public static void sendEmailToDeveloper(String fileString,Context mContext,String subject){

        Intent intent = new Intent(Intent.ACTION_SEND);
        String[] tos = { "Yuan.Wu@Honeywell.com","Jin.Qian@Honeywell.com","Vincent.Chen@Honeywell.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, tos);

        intent.putExtra(Intent.EXTRA_SUBJECT, subject);

        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(fileString));
        intent.setType("text/plain");
        mContext.startActivity(intent);

    }


    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    private static Map<String, String>  collectDeviceInfo(Context ctx) {

        //用来存储设备信息和异常信息
        Map<String, String> infos = new HashMap<String, String>();

        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = Integer.toString(pi.versionCode);
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (Exception e) {
            LogUtil.log(LogUtil.LogLevel.ERROR,TAG, "an error occured when collect package info"+e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                LogUtil.log(LogUtil.LogLevel.DEBUG,TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                LogUtil.log(LogUtil.LogLevel.ERROR,TAG, "an error occured when collect crash info"+ e);
            }
        }

        return infos;
    }


    /**
     * 保存错误信息到文件中
     *
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    public static String saveLogInfo2File(String logTxt, Context ctx) {

        Map<String, String> infos = collectDeviceInfo(ctx);

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        sb.append(logTxt);
        FileOutputStream fos = null;

        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + ".txt";

            String path = Environment.getExternalStorageDirectory().getPath() + File.separator + "crash/";
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            fos = new FileOutputStream(path + fileName);
            fos.write(sb.toString().getBytes());

            return "file://" + path + fileName;
        } catch (Exception e) {
            LogUtil.log(LogUtil.LogLevel.ERROR,TAG, "an error occured while writing file..."+e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e){
                    LogUtil.log(LogUtil.LogLevel.ERROR,TAG, "an error occured while close"+ e);
                }
            }
        }
        return null;
    }

}
