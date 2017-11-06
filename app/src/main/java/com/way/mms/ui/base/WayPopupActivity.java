package com.way.mms.ui.base;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.way.mms.R;
import com.way.mms.ui.ThemeManager;
import com.way.mms.ui.settings.SettingsFragment;
import com.way.mms.ui.view.WayLinearLayout;

/**
 * <pre>
 *     author: Way Lin
 *     date  : 2017.11.04
 *     desc  :
 * </pre>
 */

public abstract class WayPopupActivity extends WayActivity {
    protected SharedPreferences mPrefs;
    protected Resources mRes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mRes = getResources();

        setFinishOnTouchOutside(mPrefs.getBoolean(SettingsFragment.QUICKREPLY_TAP_DISMISS, true));
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        setContentView(getLayoutResource());

        ((WayLinearLayout) findViewById(R.id.popup)).setBackgroundTint(ThemeManager.getBackgroundColor());

        View title = findViewById(R.id.title);
        if (title != null && title instanceof AppCompatTextView) {
            title.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getThemeRes() {
        switch (ThemeManager.getTheme()) {
            case DARK:
                return R.style.AppThemeDarkDialog;

            case BLACK:
                return R.style.AppThemeDarkAmoledDialog;
        }

        return R.style.AppThemeLightDialog;
    }

    protected abstract int getLayoutResource();
}
