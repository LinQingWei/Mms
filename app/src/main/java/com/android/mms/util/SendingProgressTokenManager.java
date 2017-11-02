package com.android.mms.util;

import com.way.mms.common.utils.MLog;

import java.util.HashMap;

/**
 * Way Lin, 20171102.
 */

public class SendingProgressTokenManager {
    private static final String TAG = "SendingProgressTokenManager";
    private static final boolean DEBUG = false;
    private static final boolean LOCAL_LOGV = false;
    private static final HashMap<Object, Long> TOKEN_POOL;

    public static final long NO_TOKEN = -1L;

    static {
        TOKEN_POOL = new HashMap<Object, Long>();
    }

    synchronized public static long get(Object key) {
        Long token = TOKEN_POOL.get(key);
        if (LOCAL_LOGV) MLog.v(TAG, "TokenManager.get(" + key + ") -> " + token);
        return token != null ? token : NO_TOKEN;
    }

    synchronized public static void put(Object key, long token) {
        if (LOCAL_LOGV) MLog.v(TAG, "TokenManager.put(" + key + ", " + token + ")");
        TOKEN_POOL.put(key, token);
    }

    synchronized public static void remove(Object key) {
        if (LOCAL_LOGV) MLog.v(TAG, "TokenManager.remove(" + key + ")");
        TOKEN_POOL.remove(key);
    }
}
