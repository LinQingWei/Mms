package com.way.mms.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.way.mms.transaction.NotificationManager;

/**
 * Way Lin, 20171113.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager.initQuickCompose(context, false, false);
        NotificationManager.create(context);
    }

}
