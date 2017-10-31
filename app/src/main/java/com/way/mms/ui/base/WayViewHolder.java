package com.way.mms.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Way Lin, 20171028.
 */

public abstract class WayViewHolder<DataType> extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener {
    public RecyclerCursorAdapter.ItemClickListener<DataType> mClickListener;
    public DataType mData;
    public BaseActivity mActivity;

    public WayViewHolder(View itemView, BaseActivity activity) {
        super(itemView);
        mActivity = activity;
    }

    @Override
    public void onClick(View v) {
        if (mClickListener != null) {
            mClickListener.onItemClick(mData, v);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mClickListener != null) {
            mClickListener.onItemLongClick(mData, v);
        }

        return true;
    }
}
