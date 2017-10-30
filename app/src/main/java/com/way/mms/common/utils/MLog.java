package com.way.mms.common.utils;

import android.util.Log;

/**
 * Way Lin, 20171030.
 */

public class MLog {
    static boolean sDebugMode;
    static boolean sPerfLog;
    static boolean sSysLog;

    private static int sCallCounter = 1;
    private static Object sCallCounterLock = new Object();

    static {
        initLog(true);
    }

    public static void initLog(boolean debug) {
        sDebugMode = debug;
        sPerfLog = debug;
        sSysLog = debug;
    }

    public static final int v(String tag, String msg) {
        return sSysLog ? Log.v(tag, msg) : 0;
    }

    public static final int v(String tag, String format, Object... args) {
        return sSysLog ? Log.v(tag, format(format, args)) : 0;
    }

    public static final int d(String tag, String msg) {
        return sSysLog ? Log.d(tag, msg) : 0;
    }

    public static final int d(String tag, String msg, Throwable tr) {
        return sSysLog ? Log.d(tag, msg, tr) : 0;
    }

    public static final int d(String tag, String format, Object... args) {
        return sSysLog ? Log.d(tag, format(format, args)) : 0;
    }

    public static final int i(String tag, String msg) {
        return sSysLog ? Log.i(tag, msg) : 0;
    }

    public static final int i(String tag, String format, Object... args) {
        return sSysLog ? Log.i(tag, format(format, args)) : 0;
    }

    public static final int w(String tag, String msg) {
        return Log.w(tag, msg);
    }

    public static final int w(String tag, String msg, Throwable tr) {
        return Log.w(tag, msg, tr);
    }

    public static final int e(String tag, String msg) {
        return Log.e(tag, msg);
    }

    public static final int e(String tag, String msg, Throwable tr) {
        return Log.e(tag, msg, tr);
    }

    public static final int e(String tag, String format, Object... args) {
        return Log.e(tag, format(format, args));
    }

    public static final int wtf(String tag, String msg) {
        int ret = Log.wtf(tag, msg, null);
        return ret;
    }

    public static final int wtf(String tag, String msg, Throwable tr) {
        int ret = Log.wtf(tag, msg, tr);
        return ret;
    }

    protected static final String format(String format, Object... args) {
        try {
            return String.format(format, args);
        } catch (Exception e) {
            return "Exception in format Log >> " + format + "<< !!";
        }
    }

    public static final int logPerformance(String tag, String msg) {
        return sPerfLog ? Log.d(tag, msg + " @" + System.nanoTime()) : 0;
    }

    public static final int logCallMethod(String tag) {
        if (!sPerfLog) {
            return 0;
        }
        int count;
        synchronized (sCallCounterLock) {
            count = sCallCounter;
            sCallCounter = count + 1;
            count = logMethodTracing(tag, count);
        }
        return count;
    }

    public static final int logCallMethod(String tag, int counter) {
        if (sPerfLog) {
            return logMethodTracing(tag, counter);
        }
        return 0;
    }

    private static final int logMethodTracing(String tag, int counter) {
        StackTraceElement method = Thread.currentThread().getStackTrace()[4];
        Log.d(tag, "MTL [" + counter + "] #" + method.getClassName() + '.' + method.getMethodName() + " @" + System.nanoTime());
        return counter;
    }
}
