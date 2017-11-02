package com.way.mms.mmssms;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Way Lin, 20171102.
 * <p>
 * Class to process transaction requests for sending
 */

public class Transaction {
    private static final String TAG = "Transaction";
    private static final boolean LOCAL_LOGV = false;

    public static Settings settings;
    private Context context;
    private ConnectivityManager mConnMgr;
}
