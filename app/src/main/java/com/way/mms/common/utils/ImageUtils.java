package com.way.mms.common.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

/**
 * Way Lin, 20171102.
 */

public class ImageUtils {

    public static Bitmap getCircleBitmap(Bitmap bitmap, int radius) {

        Bitmap sbmp;

        if (bitmap.getWidth() != radius || bitmap.getHeight() != radius) {
            float smallest = Math.min(bitmap.getWidth(), bitmap.getHeight());
            float factor = smallest / radius;
            sbmp = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() / factor), (int) (bitmap.getHeight() / factor), false);
        } else {
            sbmp = bitmap;
        }

        Bitmap output = Bitmap.createBitmap(radius, radius, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xffa19774;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, radius, radius);

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        canvas.drawCircle(radius / 2, radius / 2, radius / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(sbmp, rect, rect, paint);

        return output;
    }
}
