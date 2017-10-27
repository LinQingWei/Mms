package com.way.mms.ui.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.way.mms.R;
import com.way.mms.common.FontManager;
import com.way.mms.common.LiveViewManager;
import com.way.mms.common.utils.TextUtils;
import com.way.mms.enums.WayPreference;
import com.way.mms.ui.ThemeManager;
import com.way.mms.ui.settings.SettingsFragment;

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
    private int mType = FontManager.TEXT_TYPE_PRIMARY;
    private boolean mOnColorBackground;

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

        if (attrs != null) {
            final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WayTextView);
            mType = array.getInt(R.styleable.WayTextView_type, FontManager.TEXT_TYPE_PRIMARY);
            array.recycle();
        }

        setTextColor(FontManager.getTextColor(mContext, mType));
        setText(getText());

        setType(mType);
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;

        // Register for theme updates if we're text that changes color dynamically.
        if (mType == FontManager.TEXT_TYPE_CATEGORY) {
            LiveViewManager.registerView(WayPreference.THEME, this, key ->
                    setTextColor(FontManager.getTextColor(mContext, mType)));
        }

        LiveViewManager.registerView(WayPreference.FONT_FAMILY, this, key -> {
            setTypeface(FontManager.getFont(mContext, type));
        });

        LiveViewManager.registerView(WayPreference.FONT_WEIGHT, this, key -> {
            setTypeface(FontManager.getFont(mContext, type));
        });

        LiveViewManager.registerView(WayPreference.FONT_SIZE, this, key -> {
            setTextSize(TypedValue.COMPLEX_UNIT_SP, FontManager.getTextSize(mContext, mType));
        });

        LiveViewManager.registerView(WayPreference.BACKGROUND, this, key -> {
            setTextColor(FontManager.getTextColor(mContext, mType));
        });

        LiveViewManager.registerView(WayPreference.TEXT_FORMATTING, this, key -> {
            setText(getText(), BufferType.NORMAL);
        });
    }

    public void setOnColorBackground(boolean onColorBackground) {
        if (onColorBackground != mOnColorBackground) {
            mOnColorBackground = onColorBackground;

            if (onColorBackground) {
                if (mType == FontManager.TEXT_TYPE_PRIMARY) {
                    setTextColor(ThemeManager.getTextOnColorPrimary());
                    setLinkTextColor(ThemeManager.getTextOnColorPrimary());
                } else if (mType == FontManager.TEXT_TYPE_SECONDARY ||
                        mType == FontManager.TEXT_TYPE_TERTIARY) {
                    setTextColor(ThemeManager.getTextOnColorSecondary());
                }
            } else {
                if (mType == FontManager.TEXT_TYPE_PRIMARY) {
                    setTextColor(ThemeManager.getTextOnBackgroundPrimary());
                    setLinkTextColor(ThemeManager.getActiveColor());
                } else if (mType == FontManager.TEXT_TYPE_SECONDARY ||
                        mType == FontManager.TEXT_TYPE_TERTIARY) {
                    setTextColor(ThemeManager.getTextOnBackgroundSecondary());
                }
            }
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        if (mType == FontManager.TEXT_TYPE_DIALOG_BUTTON) {
            text = text.toString().toUpperCase();
        }

        if (prefs.getBoolean(SettingsFragment.MARKDOWN_ENABLED, false)) {
            text = TextUtils.styleText(text);
            if (text == null || text.length() <= 0 || Build.VERSION.SDK_INT >= 19) {
                super.setText(text, BufferType.EDITABLE);
                return;
            }

            SpannableStringBuilder builder = new SpannableStringBuilder(text);
            super.setText(builder, BufferType.EDITABLE);
        } else {
            super.setText(text, BufferType.NORMAL);
        }
    }
}
