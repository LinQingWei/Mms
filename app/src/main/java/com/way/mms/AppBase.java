package com.way.mms;

import android.content.res.Configuration;
import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.way.mms.common.LiveViewManager;
import com.way.mms.common.WayPreferences;
import com.way.mms.ui.ThemeManager;

/**
 * <pre>
 *     author: Way Lin
 *     date  : 2017.10.24
 *     desc  :
 * </pre>
 */

public class AppBase extends MultiDexApplication {
    public static final String LOG_TAG = "Mms";

    public static final int LOADER_CONVERSATIONS = 0;

    private static AppBase sAppBase;

    @Override
    public void onCreate() {
        super.onCreate();

        if (Log.isLoggable(LogTag.STRICT_MODE_TAG, Log.DEBUG)) {
            // Log tag for enabling/disabling StrictMode violation log. This will dump a stack
            // in the log that shows the StrictMode violator.
            // To enable: adb shell setprop log.tag.Mms:strictmode DEBUG
            StrictMode.setThreadPolicy(
                    new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
        }

        sAppBase = this;

        ThemeManager.init(this);
        LiveViewManager.init(this);
        WayPreferences.init(this);
    }

    public synchronized static AppBase getApplication() {
        return sAppBase;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}
