package com.way.mms.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.way.mms.R;
import com.way.mms.common.utils.OsUtil;

/**
 * <pre>
 *     author: Way Lin
 *     date  : 2017.11.04
 *     desc  :
 * </pre>
 */

public class WayLinearLayout extends LinearLayout {
    private final String TAG = "Mms:WayLinearLayout";

    private int mBackgroundTint = 0xFFFFFFFF;

    public WayLinearLayout(Context context) {
        this(context, null);
    }

    public WayLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WayLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WayLinearLayout);

            for (int i = 0; i < a.length(); i++) {
                int attr = a.getIndex(i);

                switch (attr) {
                    case R.styleable.WayLinearLayout_backgroundTint:
                        mBackgroundTint = a.getInt(i, 0xFFFFFFFF);
                        break;
                }
            }

            if (OsUtil.isAtLeastJB()) {
                setBackground(getBackground());
            } else {
                setBackgroundDrawable(getBackground());
            }

            a.recycle();
        }
    }

    public void setBackgroundTint(int backgroundTint) {
        if (mBackgroundTint != backgroundTint) {
            mBackgroundTint = backgroundTint;

            if (OsUtil.isAtLeastJB()) {
                setBackground(getBackground());
            } else {
                setBackgroundDrawable(getBackground());
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void setBackground(Drawable background) {
        background.mutate().setColorFilter(mBackgroundTint, PorterDuff.Mode.MULTIPLY);
        super.setBackground(background);
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        background.mutate().setColorFilter(mBackgroundTint, PorterDuff.Mode.MULTIPLY);
        super.setBackgroundDrawable(background);
    }
}
