package com.way.mms.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.way.mms.R;
import com.way.mms.common.LiveViewManager;
import com.way.mms.enums.WayPreference;
import com.way.mms.ui.base.WayActivity;
import com.way.mms.ui.conversation.ConversationListFragment;
import com.way.mms.ui.dialog.DefaultSmsHelper;
import com.way.mms.ui.settings.SettingsFragment;
import com.way.mms.ui.welcome.WelcomeActivity;

public class MainActivity extends WayActivity {
    private final String TAG = "MainActivity";

    private View mRoot;
    private ConversationListFragment mConversationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launchWelcomeActivity();
        onNewIntent(getIntent());

        setContentView(R.layout.activity_fragment);
        setTitle(R.string.title_conversation_list);
        initView();

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
            // User has already seen the welcome screen
            return;
        }

        WelcomeActivity.startForResult(this, WelcomeActivity.class, WelcomeActivity.WELCOME_REQUEST_CODE);
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
            new DefaultSmsHelper(this, R.string.not_default_first).showIfNotDefault(mRoot);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // onNewIntent doesn't change the result of getIntent() by default, so here we set it since
        // that makes the most sense.
        setIntent(intent);
    }
}
