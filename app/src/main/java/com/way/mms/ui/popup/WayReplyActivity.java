package com.way.mms.ui.popup;

import com.way.mms.ui.base.WayPopupActivity;

/**
 * <pre>
 *     author: Way Lin
 *     date  : 2017.11.04
 *     desc  :
 * </pre>
 */

public class WayReplyActivity extends WayPopupActivity {

    // Intent extras for configuring a QuickReplyActivity intent
    public static final String EXTRA_THREAD_ID = "thread_id";
    public static final String EXTRA_SHOW_KEYBOARD = "open_keyboard";

    private static long sThreadId;

    @Override
    protected int getLayoutResource() {
        return 0;
    }
}
