package com.way.mms.ui;

import android.os.Bundle;

import com.way.mms.ui.base.BaseActivity;
import com.way.mms.ui.settings.SettingsFragment;
import com.way.mms.ui.welcome.WelcomeActivity;

public class MainActivity extends BaseActivity {
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launchWelcomeActivity();
    }

    private void launchWelcomeActivity() {
        if (mPrefs.getBoolean(SettingsFragment.WELCOME_SEEN, false)) {
            // User has already seen the welcome screen
            return;
        }

        WelcomeActivity.startForResult(this, WelcomeActivity.class, WelcomeActivity.WELCOME_REQUEST_CODE);
    }
}
