package com.way.mms.common.utils;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * <pre>
 *     author: Way Lin
 *     date  : 2017.11.04
 *     desc  :
 * </pre>
 */

public class PackageUtils {
    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
