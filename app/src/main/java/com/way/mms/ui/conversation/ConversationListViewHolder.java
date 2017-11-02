package com.way.mms.ui.conversation;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;

import com.way.mms.R;
import com.way.mms.data.Contact;
import com.way.mms.data.Conversation;
import com.way.mms.data.ConversationLegacy;
import com.way.mms.ui.ThemeManager;
import com.way.mms.ui.base.BaseActivity;
import com.way.mms.ui.base.WayViewHolder;
import com.way.mms.ui.settings.SettingsFragment;
import com.way.mms.ui.view.AvatarView;
import com.way.mms.ui.view.WayTextView;

/**
 * Way Lin, 20171028.
 */

public class ConversationListViewHolder extends WayViewHolder<Conversation>
        implements Contact.UpdateListener {
    private final SharedPreferences mPrefs;

    protected WayTextView snippetView;
    protected WayTextView fromView;
    protected WayTextView dateView;
    protected ImageView mutedView;
    protected ImageView unreadView;
    protected ImageView errorIndicator;
    protected AvatarView mAvatarView;
    protected ImageView mSelected;

    protected View mRoot;

    public ConversationListViewHolder(View view, BaseActivity activity) {
        super(view, activity);
        mRoot = view;

        mPrefs = mActivity.getPrefs();
        fromView = (WayTextView) view.findViewById(R.id.conversation_list_name);
        snippetView = (WayTextView) view.findViewById(R.id.conversation_list_snippet);
        dateView = (WayTextView) view.findViewById(R.id.conversation_list_date);
        mutedView = (ImageView) view.findViewById(R.id.conversation_list_muted);
        unreadView = (ImageView) view.findViewById(R.id.conversation_list_unread);
        errorIndicator = (ImageView) view.findViewById(R.id.conversation_list_error);
        mAvatarView = (AvatarView) view.findViewById(R.id.conversation_list_avatar);
        mSelected = (ImageView) view.findViewById(R.id.selected);
    }

    @Override
    public void onUpdate(Contact updated) {
        boolean shouldUpdate = true;
        final Drawable drawable;
        final String name;

        if (mData.getRecipients().size() == 1) {
            Contact contact = mData.getRecipients().get(0);
            if (contact.getNumber().equals(updated.getNumber())) {
                drawable = contact.getAvatar(mActivity, null);
                name = contact.getName();

                if (contact.existsInDatabase()) {
                    mAvatarView.assignContactUri(contact.getUri());
                } else {
                    mAvatarView.assignContactFromPhone(contact.getNumber(), true);
                }
            } else {
                // onUpdate was called because *some* contact was loaded, but it wasn't the contact for this
                // conversation, and thus we shouldn't update the UI because we won't be able to set the correct data
                drawable = null;
                name = "";
                shouldUpdate = false;
            }
        } else if (mData.getRecipients().size() > 1) {
            drawable = null;
            name = "" + mData.getRecipients().size();
            mAvatarView.assignContactUri(null);
        } else {
            drawable = null;
            name = "#";
            mAvatarView.assignContactUri(null);
        }

        final ConversationLegacy conversationLegacy = new ConversationLegacy(mActivity, mData.getThreadId());

        if (shouldUpdate) {
            mActivity.runOnUiThread(() -> {
                mAvatarView.setImageDrawable(drawable);
                mAvatarView.setContactName(name);
                fromView.setText(formatMessage(mData, conversationLegacy));
            });
        }
    }

    private CharSequence formatMessage(Conversation conversation, ConversationLegacy conversationLegacy) {
        String from = conversation.getRecipients().formatNames(", ");

        SpannableStringBuilder buf = new SpannableStringBuilder(from);

        final Resources res = mActivity.getResources();
        if (conversation.getMessageCount() > 1 && mPrefs.getBoolean(SettingsFragment.MESSAGE_COUNT, false)) {
            int before = buf.length();
            buf.append(res.getString(R.string.message_count_format, "" + conversation.getMessageCount()));
            buf.setSpan(new ForegroundColorSpan(res.getColor(R.color.grey_light)), before, buf.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        if (conversationLegacy.hasDraft()) {
            buf.append(res.getString(R.string.draft_separator));
            int before = buf.length();
            buf.append(res.getString(R.string.has_draft));
            buf.setSpan(new ForegroundColorSpan(ThemeManager.getActiveColor()), before, buf.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        return buf;
    }
}
