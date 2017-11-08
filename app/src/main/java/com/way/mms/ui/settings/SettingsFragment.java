package com.way.mms.ui.settings;

import android.preference.PreferenceFragment;

/**
 * Way Lin, 20171025.
 */

public class SettingsFragment extends PreferenceFragment {
    public static final String TAG = "SettingsFragment";

    public static final String THEME = "pref_key_theme";
    public static final String BACKGROUND = "pref_key_background";

    public static final String MARKDOWN_ENABLED = "pref_key_markdown_enabled";

    public static final String FONT_FAMILY = "pref_key_font_family";
    public static final String FONT_SIZE = "pref_key_font_size";
    public static final String FONT_WEIGHT = "pref_key_font_weight";

    public static final String QUICKREPLY = "pref_key_quickreply_enabled";
    public static final String QUICKREPLY_TAP_DISMISS = "pref_key_quickreply_dismiss";
    public static final String QUICKCOMPOSE = "pref_key_quickcompose";

    public static final String WELCOME_SEEN = "pref_key_welcome_seen";

    public static final String AUTO_EMOJI = "pref_key_auto_emoji";

    public static final String MESSAGE_COUNT = "pref_key_message_count";

    public static final String BLOCKED_ENABLED = "pref_key_blocked_enabled";
    public static final String BLOCKED_SENDERS = "pref_key_blocked_senders";
    public static final String BLOCKED_FUTURE = "pref_key_block_future";
    public static final String SHOULD_I_ANSWER = "pref_key_should_i_answer";

    public static final String NOTIFICATIONS = "pref_key_notifications";
    public static final String NOTIFICATION_LED = "pref_key_led";
    public static final String NOTIFICATION_LED_COLOR = "pref_key_theme_led";
    public static final String WAKE = "pref_key_wake";
    public static final String NOTIFICATION_TICKER = "pref_key_ticker";
    public static final String PRIVATE_NOTIFICATION = "pref_key_notification_private";
    public static final String NOTIFICATION_VIBRATE = "pref_key_vibration";
    public static final String NOTIFICATION_TONE = "pref_key_ringtone";
    public static final String NOTIFICATION_CALL_BUTTON = "pref_key_notification_call";
    public static final String DEFAULT_NOTIFICATION_TONE = "content://settings/system/notification_sound";

    public static final String DISMISSED_READ = "pref_key_dismiss_read";
}
