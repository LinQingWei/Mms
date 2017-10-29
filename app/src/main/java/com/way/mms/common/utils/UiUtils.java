package com.way.mms.common.utils;

import android.app.Activity;
import android.graphics.Color;

import com.way.mms.ui.PermissionCheckActivity;

/**
 * <pre>
 *     author: Way Lin
 *     date  : 2017.10.29
 *     desc  :
 * </pre>
 */

public class UiUtils {

    public static void setStatusBarColor(final Activity activity, final int color) {
        if (OsUtil.isAtLeastL()) {
            // To achieve the appearance of an 80% opacity blend against a black background,
            // each color channel is reduced in value by 20%.
            final int blendedRed = (int) Math.floor(0.8 * Color.red(color));
            final int blendedGreen = (int) Math.floor(0.8 * Color.green(color));
            final int blendedBlue = (int) Math.floor(0.8 * Color.blue(color));

            activity.getWindow().setStatusBarColor(
                    Color.rgb(blendedRed, blendedGreen, blendedBlue));
        }
    }

    /**
     * Check if the activity needs to be redirected to permission check
     *
     * @return true if {@link Activity#finish()} was called because redirection was performed
     */
    public static boolean redirectToPermissionCheckIfNeeded(final Activity activity) {
        if (!OsUtil.hasRequiredPermissions()) {
            PermissionCheckActivity.start(activity);
        } else {
            // No redirect performed
            return false;
        }

        // Redirect performed
        activity.finish();
        return true;
    }
}
