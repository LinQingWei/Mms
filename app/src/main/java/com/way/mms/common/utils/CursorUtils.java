package com.way.mms.common.utils;

import android.database.Cursor;

/**
 * Way Lin, 20171103.
 */

public class CursorUtils {
    public static final String TAG = "CursorUtils";

    /**
     * Returns true if the cursor is non-null and not closed.
     *
     * @param cursor
     * @return
     */
    public static boolean isValid(Cursor cursor) {
        return cursor != null && !cursor.isClosed();
    }
}
