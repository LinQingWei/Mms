package com.way.mms.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * <pre>
 *     author: Way Lin
 *     date  : 2017.10.25
 *     desc  :
 * </pre>
 */

public class WayTextView extends AppCompatTextView {
    private final String TAG = "WayTextView";

    private Context mContext;
    private int mType;

    public WayTextView(Context context) {
        this(context, null);
    }

    public WayTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WayTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (!isInEditMode()) {
            init(context, attrs);
        }
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }
}
