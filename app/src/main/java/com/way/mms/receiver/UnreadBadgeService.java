package com.way.mms.receiver;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Way Lin, 20171102.
 */

public class UnreadBadgeService extends IntentService {
    private static final String TAG = "UnreadBadgeService";
    public static final String UNREAD_COUNT_UPDATED = "com.way.mms.intent.action.UNREAD_COUNT_UPDATED";

    public UnreadBadgeService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    public static void update(Context context) {
        Intent intent = new Intent(context, UnreadBadgeService.class);
        intent.setAction(UNREAD_COUNT_UPDATED);
        context.startService(intent);
    }
}
