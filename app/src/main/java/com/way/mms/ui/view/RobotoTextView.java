package com.way.mms.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.way.mms.R;
import com.way.mms.common.TypefaceManager;

/**
 * Way Lin, 20171025.
 */

public class RobotoTextView extends AppCompatTextView {
    public RobotoTextView(Context context) {
        this(context, null);
    }

    public RobotoTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RobotoTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            initTypeface(this, context, attrs);
        }
    }

    private void initTypeface(TextView textView, Context context, AttributeSet attrs) {
        Typeface typeface = null;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RobotoTextView);
            if (a.hasValue(R.styleable.RobotoTextView_typeface)) {
                int typefaceValue = a.getInt(R.styleable.RobotoTextView_typeface, TypefaceManager.Typefaces.ROBOTO_REGULAR);
                typeface = TypefaceManager.obtainTypeface(context, typefaceValue);
            }

            a.recycle();
        }

        if (typeface == null) {
            typeface = TypefaceManager.obtainTypeface(context, TypefaceManager.Typefaces.DEFAULT_REGULAR);
        }

        textView.setPaintFlags(textView.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        textView.setTypeface(typeface);
    }
}
