package com.way.mms.ui.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Toast;

import com.way.mms.LogTag;
import com.way.mms.R;
import com.way.mms.common.LiveViewManager;
import com.way.mms.common.WayPreferences;
import com.way.mms.common.utils.ColorUtils;
import com.way.mms.common.utils.MLog;
import com.way.mms.common.utils.UiUtils;
import com.way.mms.enums.WayPreference;
import com.way.mms.ui.ThemeManager;
import com.way.mms.ui.view.WayTextView;

import java.util.ArrayList;

/**
 * <pre>
 *     author: Way Lin
 *     date  : 2017.10.24
 *     desc  :
 * </pre>
 */

public abstract class WayActivity extends AppCompatActivity {
    protected static final String TAG = LogTag.TAG_VIEW;
    private final String mComponentName = (getClass().getSimpleName() + " @" + String.valueOf(hashCode()) + "  ");

    private Toolbar mToolbar;
    private WayTextView mTitle;
    private ImageView mOverflowButton;
    private Menu mMenu;
    private ProgressDialog mProgressDialog;
    private Bitmap mRecentsIcon;

    protected Resources mRes;
    protected SharedPreferences mPrefs;

    private static boolean mStatusTintEnabled = true;
    private static boolean mNavigationTintEnabled = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        MLog.d(TAG, mComponentName + "lifecycle onCreate");
        super.onCreate(savedInstanceState);
        mRes = getResources();
        // set the preferences if they haven't been set. this method takes care of that logic for us
        getPrefs();
        if (UiUtils.redirectToPermissionCheckIfNeeded(this)) {
            return;
        }

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);

        LiveViewManager.registerView(WayPreference.TINTED_STATUS, this, key -> {
            mStatusTintEnabled = WayPreferences.getBoolean(WayPreference.TINTED_STATUS) &&
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
        });

        LiveViewManager.registerView(WayPreference.TINTED_NAV, this, key -> {
            mNavigationTintEnabled = WayPreferences.getBoolean(WayPreference.TINTED_NAV) &&
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
        });

        if (Build.VERSION.SDK_INT >= 21) {
            mRecentsIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            LiveViewManager.registerView(WayPreference.THEME, this, key -> {
                ActivityManager.TaskDescription taskDesc = new ActivityManager.TaskDescription(getString(R.string.app_name), mRecentsIcon, ThemeManager.getActiveColor());
                setTaskDescription(taskDesc);
            });
        }
    }

    @Override
    protected void onStart() {
        MLog.d(TAG, mComponentName + "lifecycle onStart");
        super.onStart();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        MLog.d(TAG, this.mComponentName + "lifecycle onNewIntent");
        super.onNewIntent(intent);
    }

    @Override
    protected void onRestart() {
        MLog.d(TAG, this.mComponentName + "lifecycle onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        MLog.d(TAG, this.mComponentName + "lifecycle onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        MLog.d(TAG, this.mComponentName + "lifecycle onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        MLog.d(TAG, this.mComponentName + "lifecycle onStop");
        super.onStop();
    }

    @Override
    public void onLowMemory() {
        MLog.d(TAG, this.mComponentName + "lifecycle onLowMemory");
        super.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        MLog.d(TAG, this.mComponentName + "lifecycle onDestroy");
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        MLog.d(TAG, this.mComponentName + "lifecycle interaction-onBackPressed");
        super.onBackPressed();
    }

    /**
     * Reloads the toolbar and it's view references.
     * <p>
     * This is called every time the content view of the activity is set, since the
     * toolbar is now a part of the activity layout.
     * <p>
     * TODO: If someone ever wants to manage the Toolbar dynamically instead of keeping it in their
     * TODO  layout file, we can add an alternate way of setting the toolbar programmatically.
     */
    private void reloadToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        if (mToolbar == null) {
            throw new RuntimeException("Toolbar not found in WayActivity layout.");
        } else {
            mToolbar.setPopupTheme(R.style.PopupTheme);
            mTitle = (WayTextView) mToolbar.findViewById(R.id.toolbar_title);
            setSupportActionBar(mToolbar);
        }

        LiveViewManager.registerView(WayPreference.THEME, this, key -> {
            mToolbar.setBackgroundColor(ThemeManager.getActiveColor());

            if (mStatusTintEnabled) {
                getWindow().setStatusBarColor(ColorUtils.darken(ThemeManager.getActiveColor()));
            }
            if (mNavigationTintEnabled) {
                getWindow().setNavigationBarColor(ColorUtils.darken(ThemeManager.getActiveColor()));
            }
        });

        LiveViewManager.registerView(WayPreference.BACKGROUND, this, key -> {
            setTheme(getThemeRes());
            switch (ThemeManager.getTheme()) {
                case LIGHT:
                    mToolbar.setPopupTheme(R.style.PopupThemeLight);
                    break;

                case DARK:
                case BLACK:
                    mToolbar.setPopupTheme(R.style.PopupTheme);
                    break;
            }
            ((WayTextView) findViewById(R.id.toolbar_title)).setTextColor(ThemeManager.getTextOnColorPrimary());
        });
    }

    protected void showBackButton(boolean show) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(show);
    }

    public void showProgressDialog() {
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        mProgressDialog.hide();
    }

    public SharedPreferences getPrefs() {
        if (mPrefs == null) {
            mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        }
        return mPrefs;
    }

    public void colorMenuIcons(Menu menu, int color) {

        // Toolbar navigation icon
        Drawable navigationIcon = mToolbar.getNavigationIcon();
        if (navigationIcon != null) {
            navigationIcon.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            mToolbar.setNavigationIcon(navigationIcon);
        }

        // Overflow icon
        colorOverflowButtonWhenReady(color);

        // Other icons
        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            Drawable newIcon = menuItem.getIcon();
            if (newIcon != null) {
                newIcon.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                menuItem.setIcon(newIcon);
            }
        }
    }

    private void colorOverflowButtonWhenReady(final int color) {
        if (mOverflowButton != null) {
            // We already have the overflow button, so just color it.
            Drawable icon = mOverflowButton.getDrawable();
            icon.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            // Have to clear the image drawable first or else it won't take effect
            mOverflowButton.setImageDrawable(null);
            mOverflowButton.setImageDrawable(icon);

        } else {
            // Otherwise, find the overflow button by searching for the content description.
            final String overflowDesc = getString(R.string.abc_action_menu_overflow_description);
            final ViewGroup decor = (ViewGroup) getWindow().getDecorView();
            decor.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    decor.getViewTreeObserver().removeOnPreDrawListener(this);

                    final ArrayList<View> views = new ArrayList<>();
                    decor.findViewsWithText(views, overflowDesc,
                            View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);

                    if (views.isEmpty()) {
                        Log.w(TAG, "no views");
                    } else {
                        if (views.get(0) instanceof ImageView) {
                            mOverflowButton = (ImageView) views.get(0);
                            colorOverflowButtonWhenReady(color);
                        } else {
                            Log.w(TAG, "overflow button isn't an imageview");
                        }
                    }
                    return false;
                }
            });
        }
    }

    public Menu getMenu() {
        return mMenu;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Save a reference to the menu so that we can quickly access menu icons later.
        mMenu = menu;
        colorMenuIcons(mMenu, ThemeManager.getTextOnColorPrimary());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        reloadToolbar();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        reloadToolbar();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        reloadToolbar();
    }

    /**
     * Sets the title of the activity, displayed on the toolbar
     * <p>
     * Make sure this is only called AFTER setContentView, or else the Toolbar
     * is likely not initialized yet and this method will do nothing
     *
     * @param title title of activity
     */
    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);

        if (mTitle != null) {
            mTitle.setText(title);
        }
    }

    public WayTextView getTitleTextView() {
        return mTitle;
    }

    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    protected int getThemeRes() {
        switch (ThemeManager.getTheme()) {
            case DARK:
                return R.style.AppThemeDark;

            case BLACK:
                return R.style.AppThemeDarkAmoled;
        }

        return R.style.AppThemeLight;
    }

    public void makeToast(@StringRes int message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public static void start(Activity activity, Class<?> cls) {
        final Intent intent = new Intent(activity, cls);
        start(activity, intent);
    }

    public static void start(final Activity activity, final Intent intent) {
        try {
            activity.startActivity(intent);
        } catch (ActivityNotFoundException ane) {
            MLog.e(TAG, "start activity failed >> " + ane
                    + " intent: " + (intent != null ? intent.getAction() : "is null"));
        } catch (SecurityException se) {
            if (UiUtils.redirectToPermissionCheckIfNeeded(activity)) {
                MLog.e(TAG, "startActivityForResult fail for " + intent.getAction(), se);
            }
        }
    }

    public static void startForResult(Activity activity, Class<?> cls, int requestCode) {
        final Intent intent = new Intent(activity, cls);
        startForResult(activity, intent, requestCode);

    }

    public static void startForResult(final Activity activity, final Intent intent, final int requestCode) {
        try {
            activity.startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException ane) {
            MLog.e(TAG, "start activity failed >> " + ane
                    + " intent: " + (intent != null ? intent.getAction() : "is null")
                    + " requestCode: " + requestCode);
        } catch (SecurityException se) {
            if (UiUtils.redirectToPermissionCheckIfNeeded(activity)) {
                MLog.e(TAG, "startActivityForResult fail for " + intent.getAction(), se);
            }
        }
    }
}
