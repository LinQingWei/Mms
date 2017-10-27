package com.way.mms.enums;

import com.way.mms.ui.ThemeManager;

/**
 * Way Lin, 20171027.
 */

public enum WayPreference {
    // Appearance
    THEME("pref_key_theme", ThemeManager.DEFAULT_COLOR),
    BACKGROUND("pref_key_background", "offwhite"),;

    private String mKey;
    private Object mDefaultValue;

    WayPreference(String key) {
        mKey = key;
    }

    WayPreference(String key, Object defaultValue) {
        mKey = key;
        mDefaultValue = defaultValue;
    }

    public String getKey() {
        return mKey;
    }

    public Object getDefaultValue() {
        return mDefaultValue;
    }
}
