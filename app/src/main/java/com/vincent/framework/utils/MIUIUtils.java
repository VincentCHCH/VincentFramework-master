package com.vincent.framework.utils;


import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by h234385 on 29/8/2017.
 */

public class MIUIUtils {
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    public static boolean isMIUI() {
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
            if (prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null) {
                return true;
            }
        } catch (IOException e) {
//            LogUtil.error("MIUIUtils", "isMIUI", e);
        }
        return false;
    }

    public static Integer getMIUIVersion() {
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
            String  versionName = prop.getProperty(KEY_MIUI_VERSION_NAME, null);
            return StringUtil.toInt(versionName.substring(1, versionName.length()), 0);
        } catch (IOException e) {
//            LogUtil.error("MIUIUtils", "isMIUI", e);
        }
        return 0;
    }
}
