package com.way.mms.ui.base;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.way.mms.common.utils.CursorUtils;

import java.util.HashMap;

/**
 * Way Lin, 20171028.
 */

public abstract class RecyclerCursorAdapter<VH extends RecyclerView.ViewHolder, DataType>
        extends RecyclerView.Adapter<VH> {
    protected BaseActivity mActivity;
    protected Cursor mCursor;
    protected ItemClickListener<DataType> mItemClickListener;
    protected MultiSelectListener mMultiSelectListener;
    protected HashMap<Long, DataType> mSelectedItems = new HashMap<>();

    public RecyclerCursorAdapter(BaseActivity activity) {
        mActivity = activity;
    }

    public void setItemClickListener(ItemClickListener<DataType> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void setMultiSelectListener(MultiSelectListener multiSelectListener) {
        mMultiSelectListener = multiSelectListener;
    }

    public void changeCursor(Cursor cursor) {
        Cursor old = swapCursor(cursor);
        if (old != null) {
            old.close();
        }
    }

    private Cursor swapCursor(Cursor cursor) {
        if (mCursor == cursor) {
            return null;
        }

        Cursor oldCursor = mCursor;
        mCursor = cursor;
        if (cursor != null) {
            notifyDataSetChanged();
        }
        return oldCursor;
    }

    public Cursor getCursor() {
        return mCursor;
    }

    public int getCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    protected abstract DataType getItem(int position);

    public boolean isInMultiSelectMode() {
        return mSelectedItems.size() > 0;
    }

    public HashMap<Long, DataType> getSelectedItems() {
        return mSelectedItems;
    }

    public void disableMultiSelectMode(boolean requestCallback) {
        if (isInMultiSelectMode()) {
            mSelectedItems.clear();
            notifyDataSetChanged();

            if (requestCallback && mMultiSelectListener != null) {
                mMultiSelectListener.onMultiSelectStateChanged(false);
            }
        }
    }

    public boolean isSelected(long threadId) {
        return mSelectedItems.containsKey(threadId);
    }

    public void setSelected(long threadId, DataType object) {
        if (!mSelectedItems.containsKey(threadId)) {
            mSelectedItems.put(threadId, object);
            notifyDataSetChanged();

            if (mMultiSelectListener != null) {
                mMultiSelectListener.onItemAdded(threadId);

                if (mSelectedItems.size() == 1) {
                    mMultiSelectListener.onMultiSelectStateChanged(true);
                }
            }
        }
    }

    public void setUnselected(long threadId) {
        if (mSelectedItems.containsKey(threadId)) {
            mSelectedItems.remove(threadId);
            notifyDataSetChanged();

            if (mMultiSelectListener != null) {
                mMultiSelectListener.onItemRemoved(threadId);

                if (mSelectedItems.size() == 0) {
                    mMultiSelectListener.onMultiSelectStateChanged(false);
                }
            }
        }
    }

    public void toggleSelection(long threadId, DataType object) {
        if (isSelected(threadId)) {
            setUnselected(threadId);
        } else {
            setSelected(threadId, object);
        }
    }

    @Override
    public int getItemCount() {
        return CursorUtils.isValid(mCursor) ? mCursor.getCount() : 0;
    }

    public interface ItemClickListener<DataType> {
        void onItemClick(DataType object, View view);

        void onItemLongClick(DataType object, View view);
    }

    public interface MultiSelectListener {
        void onMultiSelectStateChanged(boolean enabled);

        void onItemAdded(long id);

        void onItemRemoved(long id);
    }
}
