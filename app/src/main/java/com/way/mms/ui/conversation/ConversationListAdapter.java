package com.way.mms.ui.conversation;

import android.view.ViewGroup;

import com.way.mms.data.Conversation;
import com.way.mms.ui.base.RecyclerCursorAdapter;
import com.way.mms.ui.base.WayActivity;

/**
 * Way Lin, 20171028.
 */

public class ConversationListAdapter extends RecyclerCursorAdapter<ConversationListViewHolder, Conversation> {

    public ConversationListAdapter(WayActivity activity) {
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
