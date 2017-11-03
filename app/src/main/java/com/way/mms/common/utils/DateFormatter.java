package com.way.mms.common.utils;

import android.content.Context;
import android.text.format.DateFormat;
import android.text.format.DateUtils;

import java.text.SimpleDateFormat;

/**
 * Way Lin, 20171103.
 */

public class DateFormatter {
    public static String getConversationTimestamp(Context context, long date) {
        if (isSameDay(date)) {
            return accountFor24HourTime(context, new SimpleDateFormat("h:mm a")).format(date);
        } else if (isSameWeek(date)) {
            return DateUtils.formatDateTime(context, date, DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_ABBREV_WEEKDAY);
        } else if (isSameYear(date)) {
            return DateUtils.formatDateTime(context, date, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NO_YEAR | DateUtils.FORMAT_ABBREV_MONTH);
        } else {
            return DateUtils.formatDateTime(context, date, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_MONTH);
        }
    }

    private static boolean isSameDay(long date) {
        SimpleDateFormat formatter = new SimpleDateFormat("D, y");
        return formatter.format(date).equals(formatter.format(System.currentTimeMillis()));
    }

    private static boolean isSameWeek(long date) {
        SimpleDateFormat formatter = new SimpleDateFormat("w, y");
        return formatter.format(date).equals(formatter.format(System.currentTimeMillis()));
    }

    private static boolean isSameYear(long date) {
        SimpleDateFormat formatter = new SimpleDateFormat("y");
        return formatter.format(date).equals(formatter.format(System.currentTimeMillis()));
    }

    public static SimpleDateFormat accountFor24HourTime(Context context, SimpleDateFormat input) { //pass in 12 hour time. If needed, change to 24 hr.
        boolean isUsing24HourTime = DateFormat.is24HourFormat(context);

        if (isUsing24HourTime) {
            return new SimpleDateFormat(input.toPattern().replace('h', 'H').replaceAll(" a", ""));
        } else return input;
    }
}
