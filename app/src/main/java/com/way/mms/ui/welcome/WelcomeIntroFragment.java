package com.way.mms.ui.welcome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.way.mms.R;
import com.way.mms.ui.view.WayTextView;

/**
 * Way Lin, 20171026.
 */

public class WelcomeIntroFragment extends WayWelcomeFragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome_greeting, container, false);

        WayTextView start = (WayTextView) view.findViewById(R.id.welcome_start);
        start.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.welcome_start:
                sPager.setCurrentItem(1);
                break;
            default:
                break;
        }
    }
}
