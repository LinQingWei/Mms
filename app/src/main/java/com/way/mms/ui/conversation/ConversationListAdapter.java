package com.way.mms.ui.conversation;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.way.mms.R;
import com.way.mms.common.ConversationPrefsHelper;
import com.way.mms.common.FontManager;
import com.way.mms.common.LiveViewManager;
import com.way.mms.common.WayPreferences;
import com.way.mms.common.emoji.EmojiRegistry;
import com.way.mms.common.utils.DateFormatter;
import com.way.mms.data.Contact;
import com.way.mms.data.Conversation;
import com.way.mms.enums.WayPreference;
import com.way.mms.ui.ThemeManager;
import com.way.mms.ui.base.BaseActivity;
import com.way.mms.ui.base.RecyclerCursorAdapter;
import com.way.mms.ui.settings.SettingsFragment;

/**
 * Way Lin, 20171028.
 */

public class ConversationListAdapter extends RecyclerCursorAdapter<ConversationListViewHolder, Conversation> {
    private static final String TAG = "Mms:ConversationListAdapter";
    private final SharedPreferences mPrefs;

    public ConversationListAdapter(BaseActivity activity) {
        super(activity);
        mPrefs = activity.getPrefs();
    }

    @Override
    public ConversationListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        View view = inflater.inflate(R.layout.list_item_conversation, null);

        final ConversationListViewHolder holder = new ConversationListViewHolder(view, mActivity);
        holder.mutedView.setImageResource(R.drawable.ic_notifications_muted);
        holder.unreadView.setImageResource(R.drawable.ic_unread_indicator);
        holder.errorIndicator.setImageResource(R.drawable.ic_error);

        LiveViewManager.registerView(WayPreference.THEME, this, key -> {
            holder.mutedView.setColorFilter(ThemeManager.getActiveColor());
            holder.unreadView.setColorFilter(ThemeManager.getActiveColor());
            holder.errorIndicator.setColorFilter(ThemeManager.getActiveColor());
        });

        LiveViewManager.registerView(WayPreference.BACKGROUND, this, key -> {
            holder.mRoot.setBackgroundDrawable(ThemeManager.getRippleBackground());
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ConversationListViewHolder holder, int position) {
        final Conversation conversation = getItem(position);

        holder.mData = conversation;
        holder.mActivity = mActivity;
        holder.mClickListener = mItemClickListener;
        holder.mRoot.setOnClickListener(holder);
        holder.mRoot.setOnLongClickListener(holder);

        holder.mutedView.setVisibility(new ConversationPrefsHelper(mActivity, conversation.getThreadId())
                .getNotificationsEnabled() ? View.GONE : View.VISIBLE);

        holder.errorIndicator.setVisibility(conversation.hasError() ? View.VISIBLE : View.GONE);

        final boolean hasUnreadMessages = conversation.hasUnreadMessages();
        if (hasUnreadMessages) {
            holder.unreadView.setVisibility(View.VISIBLE);
            holder.snippetView.setTextColor(ThemeManager.getTextOnBackgroundPrimary());
            holder.dateView.setTextColor(ThemeManager.getActiveColor());
            holder.fromView.setType(FontManager.TEXT_TYPE_PRIMARY_BOLD);
            holder.snippetView.setMaxLines(5);
        } else {
            holder.unreadView.setVisibility(View.GONE);
            holder.snippetView.setTextColor(ThemeManager.getTextOnBackgroundSecondary());
            holder.dateView.setTextColor(ThemeManager.getTextOnBackgroundSecondary());
            holder.fromView.setType(FontManager.TEXT_TYPE_PRIMARY);
            holder.snippetView.setMaxLines(1);
        }

        LiveViewManager.registerView(WayPreference.THEME, this, key -> {
            holder.dateView.setTextColor(hasUnreadMessages ? ThemeManager.getActiveColor() : ThemeManager.getTextOnBackgroundSecondary());
        });

        if (isInMultiSelectMode()) {
            holder.mSelected.setVisibility(View.VISIBLE);
            if (isSelected(conversation.getThreadId())) {
                holder.mSelected.setImageResource(R.drawable.ic_selected);
                holder.mSelected.setColorFilter(ThemeManager.getActiveColor());
                holder.mSelected.setAlpha(1f);
            } else {
                holder.mSelected.setImageResource(R.drawable.ic_unselected);
                holder.mSelected.setColorFilter(ThemeManager.getTextOnBackgroundSecondary());
                holder.mSelected.setAlpha(0.5f);
            }
        } else {
            holder.mSelected.setVisibility(View.GONE);
        }

        LiveViewManager.registerView(WayPreference.HIDE_AVATAR_CONVERSATIONS, this, key -> {
            holder.mAvatarView.setVisibility(WayPreferences.getBoolean(WayPreference.HIDE_AVATAR_CONVERSATIONS) ? View.GONE : View.VISIBLE);
        });

        // Date
        holder.dateView.setText(DateFormatter.getConversationTimestamp(mActivity, conversation.getDate()));

        // Subject
        String emojiSnippet = conversation.getSnippet();
        if (mPrefs.getBoolean(SettingsFragment.AUTO_EMOJI, false)) {
            emojiSnippet = EmojiRegistry.parseEmojis(emojiSnippet);
        }
        holder.snippetView.setText(emojiSnippet);

        Contact.addListener(holder);

        // Update the avatar and name
        holder.onUpdate(conversation.getRecipients().size() == 1 ? conversation.getRecipients().get(0) : null);
    }

    @Override
    protected Conversation getItem(int position) {
        mCursor.moveToPosition(position);
        return Conversation.from(mActivity, mCursor);
    }
}
