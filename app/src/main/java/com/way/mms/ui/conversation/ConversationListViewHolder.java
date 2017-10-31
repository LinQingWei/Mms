package com.way.mms.ui.conversation;

import android.view.View;

import com.way.mms.data.Contact;
import com.way.mms.data.Conversation;
import com.way.mms.ui.base.BaseActivity;
import com.way.mms.ui.base.WayViewHolder;

/**
 * Way Lin, 20171028.
 */

public class ConversationListViewHolder extends WayViewHolder<Conversation>
        implements Contact.UpdateListener {

    protected View root;

    public ConversationListViewHolder(View view, BaseActivity activity) {
        super(view, activity);
        this.root = view;
    }

    @Override
    public void onUpdate(Contact updated) {

    }
}
