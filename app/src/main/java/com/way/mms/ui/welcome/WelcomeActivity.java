package com.way.mms.ui.welcome;

import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.way.mms.R;
import com.way.mms.common.LiveViewManager;
import com.way.mms.common.utils.UiUtils;
import com.way.mms.enums.WayPreference;
import com.way.mms.ui.ThemeManager;
import com.way.mms.ui.base.BaseActivity;
import com.way.mms.ui.settings.SettingsFragment;
import com.way.mms.ui.view.RobotoTextView;

/**
 * Way Lin, 20171025.
 */

public class WelcomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private static final String TAG = "WelcomeActivity";

    public static final int WELCOME_REQUEST_CODE = 1025;

    private ViewPager mPager;
    private ImageView mPrevious;
    private ImageView mNext;
    private ImageView[] mIndicators;
    private View mBackground;
    private RobotoTextView mSkip;
    private boolean mFinished;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        getSupportActionBar().hide();

        mBackground = findViewById(R.id.welcome);
        mBackground.setBackgroundColor(ThemeManager.getActiveColor());

        mPrevious = (ImageView) findViewById(R.id.welcome_previous);
        mPrevious.setOnClickListener(this);

        mNext = (ImageView) findViewById(R.id.welcome_next);
        mNext.setOnClickListener(this);

        mSkip = (RobotoTextView) findViewById(R.id.welcome_skip);
        mSkip.setOnClickListener(this);

        mIndicators = new ImageView[]{
                (ImageView) findViewById(R.id.welcome_indicator_0),
                (ImageView) findViewById(R.id.welcome_indicator_1),
                (ImageView) findViewById(R.id.welcome_indicator_2)};
        tintIndicators(getResources().getColor(R.color.def_indicator_tint_color));

        mPager = (ViewPager) findViewById(R.id.welcome_pager);
        WayWelcomeFragment.setPager(mPager);
        WayWelcomeFragment.setActivity(this);
        mPager.setOnPageChangeListener(this);
        mPager.setAdapter(new WelcomePagerAdapter(getSupportFragmentManager()));

        LiveViewManager.registerView(WayPreference.THEME, this, key -> {
            mBackground.setBackgroundColor(ThemeManager.getActiveColor());
        });
    }

    public void setColorBackground(int color) {
        mBackground.setBackgroundColor(color);
    }

    public void tintIndicators(@ColorInt int color) {
        if (mIndicators != null) {
            for (ImageView indicator : mIndicators) {
                indicator.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            }
        }

        if (mSkip != null) {
            mSkip.setTextColor(color);
        }

        if (mPrevious != null) {
            mPrevious.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }

        if (mNext != null) {
            mNext.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }

    public void setFinished() {
        if (mSkip != null) {
            mFinished = true;
            mSkip.setText(R.string.welcome_finish);
            mSkip.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        WelcomePagerAdapter adapter = (WelcomePagerAdapter) mPager.getAdapter();
        Fragment fragment = adapter.getItem(position);
        if (fragment instanceof WayWelcomeFragment.WelcomeScrollListener) {
            ((WayWelcomeFragment.WelcomeScrollListener) fragment).onScrollOffsetChanged(this, positionOffset);
        }

        final int next = position + 1;
        if (next < adapter.getCount()) {
            fragment = adapter.getItem(next);
            if (fragment instanceof WayWelcomeFragment.WelcomeScrollListener) {
                ((WayWelcomeFragment.WelcomeScrollListener) fragment).onScrollOffsetChanged(this, 1 - positionOffset);
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mIndicators != null) {
            for (ImageView indicator : mIndicators) {
                indicator.setAlpha(0.56f);
            }

            mIndicators[position].setAlpha(1.00f);
        }

        if (mSkip != null) {
            mSkip.setVisibility(position == 0 || mFinished ? View.VISIBLE : View.INVISIBLE);
        }

        if (mPrevious != null) {
            mPrevious.setEnabled(position > 0);
            mPrevious.setAlpha(position > 0 ? 1f : 0.6f);
        }

        if (mNext != null) {
            mNext.setEnabled(position + 1 < mPager.getAdapter().getCount());
            mNext.setAlpha(position + 1 < mPager.getAdapter().getCount() ? 1f : 0.6f);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.welcome_skip:
                setResult(RESULT_OK, null);
                UiUtils.redirectToPermissionCheckIfNeeded(this);
                finish();
                break;
            case R.id.welcome_previous:
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                break;
            case R.id.welcome_next:
                mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences.Editor prefs = PreferenceManager.getDefaultSharedPreferences(this).edit();
        prefs.putBoolean(SettingsFragment.WELCOME_SEEN, true);
        prefs.apply();
    }
}
