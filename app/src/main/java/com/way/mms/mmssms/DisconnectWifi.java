package com.way.mms.mmssms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiManager;

/**
 * Way Lin, 20171102.
 */

public class DisconnectWifi extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // if wifi tries to connect while we are sending an MMS, disable it until that message is done sending
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (!intent.getParcelableExtra(WifiManager.EXTRA_NEW_STATE).toString().equals(SupplicantState.SCANNING))
            wifi.disconnect();
    }
}
