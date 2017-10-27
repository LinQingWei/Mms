package com.way.mms.common.utils;

import android.graphics.Color;

/**
 * <pre>
 *     author: Way Lin
 *     date  : 2017.10.27
 *     desc  :
 * </pre>
 */

public class ColorUtils {
    private static final String TAG = "ColorUtils";

    public static int darken(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.85f;
        color = Color.HSVToColor(hsv);
        return color;
    }
}
