package com.vincent.framework.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by Vincent on 20/6/2017.
 */

public class ProcessUtil {
    /**
     * 判断进程是否运行
     *
     * @return
     */
    public static boolean isProcessRunning(Context context, String proessName) {

        boolean isRunning = false;
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo> lists = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : lists) {
            if (info.processName.equals(proessName)) {
                isRunning = true;
            }
        }

        return isRunning;
    }

}
