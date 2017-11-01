package com.way.mms.data;

import android.net.Uri;
import android.provider.Telephony.Threads;

/**
 * Way Lin, 20171028.
 * An interface for finding information about conversations and/or creating new ones.
 */

public class Conversation {
    private static final String TAG = "Mms/conv";
    private static final boolean DEBUG = false;
    private static final boolean DELETEDEBUG = false;

    public static final Uri sAllThreadsUri =
            Threads.CONTENT_URI.buildUpon().appendQueryParameter("simple", "true").build();

    public static final String[] ALL_THREADS_PROJECTION = {
            Threads._ID, Threads.DATE, Threads.MESSAGE_COUNT, Threads.RECIPIENT_IDS,
            Threads.SNIPPET, Threads.SNIPPET_CHARSET, Threads.READ, Threads.ERROR,
            Threads.HAS_ATTACHMENT
    };

    public static final String[] UNREAD_PROJECTION = {
            Threads._ID,
            Threads.READ
    };

    public static final String UNREAD_SELECTION = "(read=0 OR seen=0)";
    public static final String FAILED_SELECTION = "error != 0";

    public static final String[] SEEN_PROJECTION = new String[]{"seen"};

    public static final int ID = 0;
    public static final int DATE = 1;
    public static final int MESSAGE_COUNT = 2;
    public static final int RECIPIENT_IDS = 3;
    public static final int SNIPPET = 4;
    public static final int SNIPPET_CS = 5;
    public static final int READ = 6;
    public static final int ERROR = 7;
    public static final int HAS_ATTACHMENT = 8;
}
