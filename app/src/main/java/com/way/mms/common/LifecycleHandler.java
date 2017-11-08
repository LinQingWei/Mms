package com.way.mms.common;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.way.mms.common.utils.MLog;

/**
 * <pre>
 *     author: Way Lin
 *     date  : 2017.11.04
 *     desc  : http://stackoverflow.com/questions/3667022/checking-if-an-android-application-is-running-in-the-background/
 * </pre>
 */

public class LifecycleHandler implements Application.ActivityLifecycleCallbacks {
    private final String TAG = "Mms:LifecycleHandler";

    private static int sResumed;
    private static int sPaused;
    private static int sStarted;
    private static int sStopped;
    private static int sActivityCounter;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        sActivityCounter++;
        MLog.d(TAG, activity + "@onActivityCreated:" + sActivityCounter);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        sStarted++;
        MLog.d(TAG, activity + "@onActivityStarted: " + sStarted);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        sResumed++;
        MLog.d(TAG, activity + "@onActivityResumed: " + sResumed);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        sPaused++;
        MLog.d(TAG, activity + "@onActivityPaused: " + sPaused
                + ", application is in foreground: " + (sResumed > sPaused));
    }

    @Override
    public void onActivityStopped(Activity activity) {
        sStopped++;
        MLog.d(TAG, activity + "@onActivityStopped: " + sStopped
                + ", application is visible: " + (sStarted > sStopped));
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        MLog.d(TAG, activity + "@onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        MLog.d(TAG, activity + "@onActivityDestroyed");
        sActivityCounter--;
    }

    public static boolean isApplicationVisible() {
        return sStarted > sStopped;
    }

    public static boolean isApplicationInForeground() {
        return sResumed > sPaused;
    }

    public static boolean isNoActivitiesAlive() {
        return sActivityCounter <= 0;
    }
}
