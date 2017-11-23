package com.way.mms.transaction;

import android.content.Context;

/**
 * Way Lin, 20171102.
 */

public class NotificationManager {
    private static final String TAG = "NotificationManager";


    /**
     * Creates a new notification, called when a new message is received. This notification will have sound and
     * vibration
     */
    public static void create(final Context context) {

    }

    /**
     * Updates the notifications silently. This is called when a conversation is marked read or something like that,
     * where we need to update the notifications without alerting the user
     */
    public static void update(final Context context) {

    }

    /**
     * Set up the QK Compose notification
     *
     * @param override       If true, then show the QK Compose notification regardless of the user's preference
     * @param overrideCancel If true, dismiss the notification no matter what
     */
    public static void initQuickCompose(Context context, boolean override, boolean overrideCancel) {

    }
}
