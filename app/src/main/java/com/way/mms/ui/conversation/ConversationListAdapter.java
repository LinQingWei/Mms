package com.way.mms.ui.conversation;

import android.view.ViewGroup;

import com.way.mms.data.Conversation;
import com.way.mms.ui.base.BaseActivity;
import com.way.mms.ui.base.RecyclerCursorAdapter;

/**
 * Way Lin, 20171028.
 */

public class ConversationListAdapter extends RecyclerCursorAdapter<ConversationListViewHolder, Conversation> {

    public ConversationListAdapter(BaseActivity activity) {
        super(activity);
    }

    @Override
    public ConversationListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ConversationListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
