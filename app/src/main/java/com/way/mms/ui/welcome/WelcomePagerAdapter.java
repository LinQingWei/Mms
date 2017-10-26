package com.way.mms.ui.welcome;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

/**
 * Way Lin, 20171026.
 */

public class WelcomePagerAdapter extends FragmentPagerAdapter {
    private final String TAG = "WelcomePagerAdapter";

    private Fragment[] mFragments = new Fragment[3];

    public final int PAGE_INTRO = 0;
    public final int PAGE_THEME = 1;
    public final int PAGE_NIGHT = 2;

    public WelcomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (mFragments[position] == null) {
            switch (position) {
                case PAGE_INTRO:
                    mFragments[position] = new WelcomeIntroFragment();
                    break;
                case PAGE_THEME:
                    mFragments[position] = new WelcomeThemeFragment();
                    break;
                case PAGE_NIGHT:
                    mFragments[position] = new WelcomeNightFragment();
                    break;
                default:
                    Log.e(TAG, "Uh oh, the pager requested a fragment at index " + position + "which doesn't exist");
            }
        }

        return mFragments[position];
    }

    @Override
    public int getCount() {
        return mFragments.length;
    }
}
