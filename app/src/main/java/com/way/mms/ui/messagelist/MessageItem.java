package com.way.mms.ui.messagelist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.google.android.mms.MmsException;

import java.util.regex.Pattern;

/**
 * Way Lin, 20171102.
 * <p>
 * Mostly immutable model for an SMS/MMS message.
 * <p>
 * <p>The only mutable field is the cached formatted message member,
 * the formatting of which is done outside this model in MessageListItem.
 */

public class MessageItem {
    private static String TAG = "MessageItem";

    public enum DeliveryStatus {NONE, INFO, FAILED, PENDING, RECEIVED}

    public static int ATTACHMENT_TYPE_NOT_LOADED = -1;

    final Context mContext;
    public final String mType;
    public final long mMsgId;
    public final int mBoxId;

    public String mDeliveryStatusString;
    public DeliveryStatus mDeliveryStatus;
    public String mReadReportString;
    public boolean mReadReport;
    public boolean mLocked;            // locked to prevent auto-deletion

    public long mDate;
    public String mTimestamp;
    public String mAddress;
    public String mContact;
    public String mBody; // Body of SMS, first text of MMS.
    public String mTextContentType; // ContentType of text of MMS.
    public Pattern mHighlight; // portion of message to highlight (from search)

    // The only non-immutable field.  Not synchronized, as access will
    // only be from the main GUI thread.  Worst case if accessed from
    // another thread is it'll return null and be set again from that
    // thread.
    public CharSequence mCachedFormattedMessage;

    // The last message is cached above in mCachedFormattedMessage. In the latest design, we
    // show "Sending..." in place of the timestamp when a message is being sent. mLastSendingState
    // is used to keep track of the last sending state so that if the current sending state is
    // different, we can clear the message cache so it will get rebuilt and recached.
    public boolean mLastSendingState;

    // Fields for MMS only.
    public Uri mMessageUri;
    public int mMessageType;
    public int mAttachmentType;
    public String mSubject;

    @SuppressLint("NewApi")
    public MessageItem(Context context, String type, final Cursor cursor,
                       final MessageColumns.ColumnsMap columnsMap, Pattern highlight,
                       boolean canBlock) throws MmsException {
        mContext = context;
        mMsgId = cursor.getLong(columnsMap.mColumnMsgId);
        mHighlight = highlight;
        mType = type;

        // Set contact and message body
        mBoxId = cursor.getInt(columnsMap.mColumnSmsType);
    }

    public boolean isMe() {
        return false;
    }
}
