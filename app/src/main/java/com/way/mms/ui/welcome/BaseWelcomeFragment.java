package com.way.mms.ui.welcome;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.way.mms.ui.base.BaseFragment;

/**
 * Way Lin, 20171026.
 */

public class BaseWelcomeFragment extends BaseFragment {
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
