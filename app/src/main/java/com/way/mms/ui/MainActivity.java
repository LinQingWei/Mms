package com.way.mms.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.way.mms.R;
import com.way.mms.common.LiveViewManager;
import com.way.mms.common.utils.OsUtil;
import com.way.mms.common.utils.UiUtils;
import com.way.mms.enums.WayPreference;
import com.way.mms.ui.base.BaseActivity;
import com.way.mms.ui.conversation.ConversationListFragment;
import com.way.mms.ui.dialog.DefaultSmsHelper;
import com.way.mms.ui.settings.SettingsFragment;
import com.way.mms.ui.welcome.WelcomeActivity;

public class MainActivity extends BaseActivity {
    private final String TAG = "MainActivity";
    private static final String EXTRA_SHOW_IF_NOT_DEFAULT = "extra_show_if_not_default";

    private View mRoot;
    private ConversationListFragment mConversationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launchWelcomeActivity();
        Intent intent = getIntent();
        onNewIntent(intent);

        setContentView(R.layout.activity_fragment);
        setTitle(R.string.title_conversation_list);
        initView();

        handleIntent(intent);

        FragmentManager fm = getSupportFragmentManager();
        mConversationList = (ConversationListFragment) fm.findFragmentByTag(ConversationListFragment.TAG);
        if (mConversationList == null) {
            mConversationList = new ConversationListFragment();
        }

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content_frame, mConversationList, ConversationListFragment.TAG);
        ft.commit();

        LiveViewManager.registerView(WayPreference.BACKGROUND, this, key -> {
            // Update the background color. This code is important during the welcome screen setup, when the activity
            // in the ThemeManager isn't the MainActivity
            mRoot.setBackgroundColor(ThemeManager.getBackgroundColor());
        });
    }

    private void launchWelcomeActivity() {
        if (mPrefs.getBoolean(SettingsFragment.WELCOME_SEEN, false)) {
            if (UiUtils.redirectToPermissionCheckIfNeeded(this)) {
                finish();
            }

            // User has already seen the welcome screen
            return;
        }

        WelcomeActivity.startForResult(this, WelcomeActivity.class, WelcomeActivity.WELCOME_REQUEST_CODE);
        if (!OsUtil.hasRequiredPermissions()) {
            finish();
        }
    }

    private void initView() {
        mRoot = findViewById(R.id.root);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        menu.clear();

        showBackButton(false);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == WelcomeActivity.WELCOME_REQUEST_CODE) {
            showIfNotDefault();
        }
    }

    private void showIfNotDefault() {
        new DefaultSmsHelper(this, R.string.not_default_first).showIfNotDefault(mRoot);
    }

    private void handleIntent(Intent intent) {
        if (intent.getBooleanExtra(EXTRA_SHOW_IF_NOT_DEFAULT, true)) {
            showIfNotDefault();
        }
    }

    public static void start(Context context, boolean show) {
        final Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_SHOW_IF_NOT_DEFAULT, show);
        context.startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // onNewIntent doesn't change the result of getIntent() by default, so here we set it since
        // that makes the most sense.
        setIntent(intent);
    }
}
