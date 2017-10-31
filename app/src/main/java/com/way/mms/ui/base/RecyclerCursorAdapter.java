package com.way.mms.ui.base;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Way Lin, 20171028.
 */

public abstract class RecyclerCursorAdapter<VH extends RecyclerView.ViewHolder, DataType>
        extends RecyclerView.Adapter<VH> {
    protected BaseActivity mActivity;
    protected Cursor mCursor;
    protected ItemClickListener<DataType> mItemClickListener;

    public RecyclerCursorAdapter(BaseActivity activity) {
        mActivity = activity;
    }

    public void setItemClickListener(ItemClickListener<DataType> itemClickListener) {
        mItemClickListener = itemClickListener;
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

    public interface ItemClickListener<DataType> {
        void onItemClick(DataType object, View view);

        void onItemLongClick(DataType object, View view);
    }
}
