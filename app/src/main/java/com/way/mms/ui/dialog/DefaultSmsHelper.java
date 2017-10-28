package com.way.mms.ui.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Telephony;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.way.mms.R;
import com.way.mms.common.utils.SnackbarUtils;

/**
 * Way Lin, 20171028.
 */

public class DefaultSmsHelper {
    private Context mContext;
    private int mMessage;
    private static long sLastShown;
    private boolean mIsDefault = true;

    // Listener is currently useless because we can't listen for response from the system dialog
    public DefaultSmsHelper(Context context, int messageRes) {
        mContext = context;
        mMessage = messageRes != 0 ? messageRes : R.string.default_info;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            String defaultSmsPackage = Telephony.Sms.getDefaultSmsPackage(mContext);
            mIsDefault = defaultSmsPackage != null && defaultSmsPackage.equals(mContext.getPackageName());
        } else {
            mIsDefault = true;
        }
    }

    public void showIfNotDefault(View view) {
        if (!mIsDefault) {
            final long deltaTime = (System.nanoTime() / 1000000) - sLastShown;
            final int duration = deltaTime > 60 * 1000 ? 8000 : 3000;

            Snackbar snackbar = SnackbarUtils.indefiniteSnackbar(view, mContext.getString(mMessage), duration, SnackbarUtils.COLOR_CONFIRM);
            snackbar.setAction(R.string.upgrade_now, v -> setDefault()).show();

            sLastShown = System.nanoTime() / 1000000;
        }
    }

    public void setDefault() {
        Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
        intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, mContext.getPackageName());
        mContext.startActivity(intent);
    }

    public boolean isDefault() {
        return mIsDefault;
    }
}
