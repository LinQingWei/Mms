package com.way.mms.ui.welcome;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.way.mms.ui.base.WayFragment;

/**
 * Way Lin, 20171026.
 */

public class WayWelcomeFragment extends WayFragment {
    public interface WelcomeScrollListener {
        void onScrollOffsetChanged(WelcomeActivity activity, float offset);
    }

    protected static ViewPager sPager;
    protected static WelcomeActivity sActivity;

    public static void setPager(ViewPager pager) {
        sPager = pager;
    }

    public static void setActivity(WelcomeActivity activity) {
        sActivity = activity;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Do nothing
    }
}
