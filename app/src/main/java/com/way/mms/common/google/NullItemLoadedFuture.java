package com.way.mms.common.google;

import android.net.Uri;

/**
 * @link PduFuture for a pdu that is available now.
 *
 * freeme.linqingwei, 20171123.
 */

public class NullItemLoadedFuture implements ItemLoadedFuture {
    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public void setIsDone(boolean done) {

    }

    @Override
    public void cancel(Uri uri) {
        // The callback has already been made, so there's nothing to cancel.
    }
}
