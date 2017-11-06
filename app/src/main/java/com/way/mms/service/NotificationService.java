package com.way.mms.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.way.mms.common.ConversationPrefsHelper;
import com.way.mms.common.LifecycleHandler;
import com.way.mms.data.Message;
import com.way.mms.transaction.SmsHelper;
import com.way.mms.ui.popup.WayReplyActivity;
import com.way.mms.ui.settings.SettingsFragment;

/**
 * <pre>
 *     author: Way Lin
 *     date  : 2017.11.04
 *     desc  :
 * </pre>
 */

public class NotificationService extends Service {
    private final String TAG = "Mms:NotificationService";

    public static final String EXTRA_POPUP = "popup";
    public static final String EXTRA_URI = "uri";
    private Context context = this;
    private Intent popupIntent;
    private SharedPreferences prefs;
    private ConversationPrefsHelper conversationPrefs;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);

        Uri uri = Uri.parse(intent.getStringExtra(EXTRA_URI));

        // Try to get the message's ID, in case the given Uri is bad.
        long messageId = -1;
        Cursor cursor = context.getContentResolver().query(uri, new String[]{SmsHelper.COLUMN_ID},
                null, null, null);
        if (cursor.moveToFirst()) {
            messageId = cursor.getLong(cursor.getColumnIndexOrThrow(SmsHelper.COLUMN_ID));
        }
        cursor.close();

        // Make sure we found a message before showing QuickReply and using PushBullet.
        if (messageId != -1) {

            Message message = new Message(context, messageId);

            conversationPrefs = new ConversationPrefsHelper(context, message.getThreadId());

            if (conversationPrefs.getNotificationsEnabled()) {
                // Only show QuickReply if we're outside of the app, and they have popups and QuickReply enabled.
                if (!LifecycleHandler.isApplicationVisible() &&
                        intent.getBooleanExtra(EXTRA_POPUP, false) && prefs.getBoolean(SettingsFragment.QUICKREPLY, Build.VERSION.SDK_INT < 24)) {

                    popupIntent = new Intent(context, WayReplyActivity.class);
                    popupIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    popupIntent.putExtra(WayReplyActivity.EXTRA_THREAD_ID, message.getThreadId());
                    startActivity(popupIntent);
                }
            } else {
                // If the conversation is muted, mark this message as "seen". Note that this is
                // different from marking it as "read".
                message.markSeen();
            }
        }

        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
