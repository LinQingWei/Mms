package com.way.mms.enums;

import com.way.mms.ui.ThemeManager;

/**
 * Way Lin, 20171027.
 */

public enum WayPreference {
    // Appearance
    THEME("pref_key_theme", ThemeManager.DEFAULT_COLOR),
    BACKGROUND("pref_key_background", "offwhite"),

    TINTED_STATUS("pref_key_status_tint", true),
    TINTED_NAV("pref_key_navigation_tint", false),

    FONT_FAMILY("pref_key_font_family", "0"),
    FONT_SIZE("pref_key_font_size", "1"),
    FONT_WEIGHT("pref_key_font_weight", "0"),

    HIDE_AVATAR_CONVERSATIONS("pref_key_hide_avatar_conversations", false),

    TEXT_FORMATTING("pref_key_markdown_enabled", false),;

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
