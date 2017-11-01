package com.way.mms;

import android.content.Context;

import com.way.mms.common.utils.MLog;

/**
 * Way Lin, 20171101.
 */

public class MmsConfig {
    private static final String TAG = "Mms:MmsConfig";
    private static final boolean DEBUG = true;
    private static final boolean LOCAL_LOGV = false;

    // Email gateway alias support, including the master switch and different rules
    private static boolean mAliasEnabled = false;
    private static int mAliasRuleMinChars = 2;
    private static int mAliasRuleMaxChars = 48;

    public static void init(Context context) {
        if (LOCAL_LOGV) {
            MLog.v(TAG, "MmsConfig.init()");
        }
        // Always put the mnc/mcc in the log so we can tell which mms_config.xml was loaded.
        //Log.v(TAG, "mnc/mcc: " + android.os.SystemProperties.get(TelephonyProperties.PROPERTY_ICC_OPERATOR_NUMERIC));
    }


    public static boolean isAliasEnabled() {
        return mAliasEnabled;
    }

    public static int getAliasMinChars() {
        return mAliasRuleMinChars;
    }

    public static int getAliasMaxChars() {
        return mAliasRuleMaxChars;
    }
}
