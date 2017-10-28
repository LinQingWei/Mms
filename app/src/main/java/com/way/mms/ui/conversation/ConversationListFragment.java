package com.way.mms.ui.conversation;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.way.mms.AppBase;
import com.way.mms.R;
import com.way.mms.common.LiveViewManager;
import com.way.mms.common.utils.ColorUtils;
import com.way.mms.data.Conversation;
import com.way.mms.enums.WayPreference;
import com.way.mms.ui.ThemeManager;
import com.way.mms.ui.base.RecyclerCursorAdapter;
import com.way.mms.ui.base.WayFragment;

/**
 * Way Lin, 20171028.
 */

public class ConversationListFragment extends WayFragment implements LoaderManager.LoaderCallbacks<Cursor>,
        RecyclerCursorAdapter.ItemClickListener<Conversation> {
    public static final String TAG = "ConversationListFragment";

    private View mEmptyState;
    private ImageView mEmptyStateIcon;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFab;

    private ConversationListAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private SharedPreferences mPrefs;

    private boolean mViewHasLoaded;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
        setHasOptionsMenu(true);

        mAdapter = new ConversationListAdapter(mActivity);
        mAdapter.setItemClickListener(this);
        mLayoutManager = new LinearLayoutManager(mActivity);

        LiveViewManager.registerView(WayPreference.THEME, this, key -> {
            if (!mViewHasLoaded) {
                return;
            }

            final int tintColor = ThemeManager.getActiveColor();
            mFab.setBackgroundTintList(createFabColorStateList(tintColor, ColorUtils.lighten(tintColor)));
            mFab.getDrawable().setColorFilter(ThemeManager.getTextOnColorPrimary(), PorterDuff.Mode.SRC_ATOP);

            mEmptyStateIcon.setColorFilter(ThemeManager.getTextOnBackgroundPrimary());
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversations, null);
        bindView(view);

        mEmptyStateIcon.setColorFilter(ThemeManager.getTextOnBackgroundPrimary());

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        final int tintColor = ThemeManager.getActiveColor();
        mFab.setBackgroundTintList(createFabColorStateList(tintColor, ColorUtils.lighten(tintColor)));
        mFab.setColorFilter(ThemeManager.getTextOnColorPrimary());

        mViewHasLoaded = true;

        initLoaderManager();

        return view;
    }

    private void bindView(View view) {
        mEmptyState = view.findViewById(R.id.empty_state);
        mEmptyStateIcon = view.findViewById(R.id.empty_state_icon);
        mRecyclerView = view.findViewById(R.id.conversations_list);
        mFab = view.findViewById(R.id.fab);
    }

    private ColorStateList createFabColorStateList(@ColorInt int normal, @ColorInt int pressed) {
        int[] colors = new int[]{pressed, normal, normal};
        int[][] states = new int[3][];
        states[0] = new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.enabled};
        states[2] = new int[]{};

        ColorStateList colorStateList = new ColorStateList(states, colors);
        return colorStateList;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == AppBase.LOADER_CONVERSATIONS) {
            if (mAdapter != null) {
                // Swap the new cursor in.  (The framework will take care of closing the, old cursor once we return.)
                mAdapter.changeCursor(data);
            }

            mEmptyState.setVisibility(data != null && data.getCount() > 0 ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (mAdapter != null && loader.getId() == AppBase.LOADER_CONVERSATIONS) {
            mAdapter.changeCursor(null);
        }
    }

    @Override
    public void onItemClick(Conversation object, View view) {

    }

    @Override
    public void onItemLongClick(Conversation object, View view) {

    }

    private void initLoaderManager() {
        getLoaderManager().restartLoader(AppBase.LOADER_CONVERSATIONS, null, this);
    }
}
