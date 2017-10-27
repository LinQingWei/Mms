package com.way.mms.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.way.mms.enums.WayPreference;

/**
 * <pre>
 *     author: Way Lin
 *     date  : 2017.10.27
 *     desc  :
 * </pre>
 */

public abstract class WayPreferences {
    private static SharedPreferences sPrefs;

    public static void init(Context context) {
        sPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean getBoolean(WayPreference preference) {
        return sPrefs.getBoolean(preference.getKey(), (boolean) preference.getDefaultValue());
    }

    public static void setBoolean(WayPreference preference, boolean newValue) {
        sPrefs.edit().putBoolean(preference.getKey(), newValue).apply();
    }

    public static int getInt(WayPreference preference) {
        return sPrefs.getInt(preference.getKey(), (int) preference.getDefaultValue());
    }

    public static void setInt(WayPreference preference, int newValue) {
        sPrefs.edit().putInt(preference.getKey(), newValue).apply();
    }

    public static long getLong(WayPreference preference) {
        return sPrefs.getLong(preference.getKey(), (int) preference.getDefaultValue());
    }

    public static void setLong(WayPreference preference, long newValue) {
        sPrefs.edit().putLong(preference.getKey(), newValue).apply();
    }

    public static String getString(WayPreference preference) {
        return sPrefs.getString(preference.getKey(), (String) preference.getDefaultValue());
    }

    public static void setString(WayPreference preference, String newValue) {
        sPrefs.edit().putString(preference.getKey(), newValue).apply();
    }
}
