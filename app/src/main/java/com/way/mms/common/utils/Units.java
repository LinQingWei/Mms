package com.way.mms.common.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Way Lin, 20171102.
 */

public class Units {
    /**
     * Converts dp to pixels.
     */
    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}
