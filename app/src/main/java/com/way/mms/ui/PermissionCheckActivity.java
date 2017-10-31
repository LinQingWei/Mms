package com.way.mms.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.way.mms.R;
import com.way.mms.common.LiveViewManager;
import com.way.mms.common.utils.OsUtil;
import com.way.mms.common.utils.UiUtils;
import com.way.mms.enums.WayPreference;
import com.way.mms.ui.base.BaseActivity;
import com.way.mms.ui.view.WayTextView;

/**
 * <pre>
 *     author: Way Lin
 *     date  : 2017.10.29
 *     desc  : Activity to check if the user has required permissions. If not, it will try to prompt the user
 * to grant permissions. However, the OS may not actually prompt the user if the user had
 * previously checked the "Never ask again" checkbox while denying the required permissions.
 * </pre>
 */

public class PermissionCheckActivity extends BaseActivity {
    public static final int REQUIRED_PERMISSIONS_REQUEST_CODE = 1;
    private static final long AUTOMATED_RESULT_THRESHOLD_MILLLIS = 250;
    private static final String PACKAGE_URI_PREFIX = "package:";
    private long mRequestTimeMillis;
    private WayTextView mNextView;
    private WayTextView mSettingsView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (redirectIfNeeded()) {
            return;
        }

        setContentView(R.layout.activity_permission_check);
        UiUtils.setStatusBarColor(this, getColor(R.color.permission_check_activity_background));

        findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                finish();
            }
        });

        mNextView = findViewById(R.id.next);
        mNextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                tryRequestPermission();
            }
        });

        mSettingsView = findViewById(R.id.settings);
        mSettingsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse(PACKAGE_URI_PREFIX + getPackageName()));
                startActivity(intent);
            }
        });

        LiveViewManager.registerView(WayPreference.BACKGROUND, this, key -> {
            // Update the background color. This code is important during the welcome screen setup, when the activity
            // in the ThemeManager isn't the MainActivity
            View root = findViewById(R.id.root);
            if (root != null) {
                root.setBackgroundColor(ThemeManager.getBackgroundColor());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (redirectIfNeeded()) {
            return;
        }
    }

    private void tryRequestPermission() {
        final String[] missingPermissions = OsUtil.getMissingRequiredPermissions();
        if (missingPermissions.length == 0) {
            redirect();
            return;
        }

        mRequestTimeMillis = SystemClock.elapsedRealtime();
        requestPermissions(missingPermissions, REQUIRED_PERMISSIONS_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUIRED_PERMISSIONS_REQUEST_CODE) {
            // We do not use grantResults as some of the granted permissions might have been
            // revoked while the permissions dialog box was being shown for the missing permissions.
            if (OsUtil.hasRequiredPermissions()) {
                redirect();
            } else {
                final long currentTimeMillis = SystemClock.elapsedRealtime();
                // If the permission request completes very quickly, it must be because the system
                // automatically denied. This can happen if the user had previously denied it
                // and checked the "Never ask again" check box.
                if ((currentTimeMillis - mRequestTimeMillis) < AUTOMATED_RESULT_THRESHOLD_MILLLIS) {
                    mNextView.setVisibility(View.GONE);

                    mSettingsView.setVisibility(View.VISIBLE);
                    findViewById(R.id.enable_permission_procedure).setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /**
     * Returns true if the redirecting was performed
     */
    private boolean redirectIfNeeded() {
        if (!OsUtil.hasRequiredPermissions()) {
            return false;
        }

        redirect();
        return true;
    }

    private void redirect() {
        MainActivity.start(this, true);
        finish();
    }
}
