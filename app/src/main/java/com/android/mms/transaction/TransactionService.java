package com.android.mms.transaction;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Way Lin, 20171102.
 */

public class TransactionService extends Service implements Observer {
    private static final String TAG = "TransactionService";
    private static final boolean LOCAL_LOGV = false;

    /**
     * Action for the Intent which is sent to notify the TransactionService that it should attempt
     * to resend any pending transactions.
     */
    public static final String HANDLE_PENDING_TRANSACTIONS_ACTION =
            "android.intent.action.HANDLE_PENDING_TRANSACTIONS_ACTION";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void update(Observable observable) {

    }
}
