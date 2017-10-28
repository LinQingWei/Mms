package com.way.mms.ui.welcome;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.way.mms.R;
import com.way.mms.ui.ThemeManager;
import com.way.mms.ui.view.colorpicker.ColorPickerPalette;
import com.way.mms.ui.view.colorpicker.ColorPickerSwatch;

/**
 * Way Lin, 20171026.
 */

public class WelcomeThemeFragment extends WayWelcomeFragment implements ColorPickerSwatch.OnColorSelectedListener,
        WayWelcomeFragment.WelcomeScrollListener {
    private final String TAG = "WelcomeThemeFragment";

    private ColorPickerPalette mPallette;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome_theme, container, false);

        mPallette = (ColorPickerPalette) view.findViewById(R.id.welcome_themes);
        mPallette.init(19, 4, this);
        mPallette.drawPalette(ThemeManager.PALETTE, ThemeManager.getActiveColor());

        return view;
    }

    @Override
    public void onColorSelected(int color) {
        ThemeManager.setColor(sActivity, color);
        mPallette.drawPalette(ThemeManager.PALETTE, color);
    }

    @Override
    public void onScrollOffsetChanged(WelcomeActivity activity, float offset) {
        if (mPallette != null) {
            offset = (float) Math.pow(1 - offset, 4);
            int alpha = (int) (255 * 0.6 * offset);
            mPallette.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
        }
    }
}
