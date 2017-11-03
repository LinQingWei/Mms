package com.way.mms.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.way.mms.ui.settings.SettingsFragment;

/**
 * Way Lin, 20171103.
 */

public class ConversationPrefsHelper {
    public static final String CONVERSATIONS_FILE = "conversation_";

    private Context mContext;
    private SharedPreferences mPrefs;
    private SharedPreferences mConversationPrefs;

    public ConversationPrefsHelper(Context context, long threadId) {
        mContext = context;
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mConversationPrefs = context.getSharedPreferences(CONVERSATIONS_FILE + threadId, Context.MODE_PRIVATE);
    }

    public boolean getNotificationsEnabled() {
        return getBoolean(SettingsFragment.NOTIFICATIONS, true);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        boolean globalValue = mPrefs.getBoolean(key, defaultValue);
        return mConversationPrefs.getBoolean(key, globalValue);
    }
}
