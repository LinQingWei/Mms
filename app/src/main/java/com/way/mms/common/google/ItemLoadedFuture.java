package com.way.mms.common.google;

import android.net.Uri;

/**
 * Interface for querying the state of a pending item loading request.
 * <p>
 * freeme.linqingwei, 20171123.
 */

public interface ItemLoadedFuture {
    /**
     * Returns whether the associated task has invoked its callback. Note that
     * in some implementations this value only indicates whether the load
     * request was satisfied synchronously via a cache rather than
     * asynchronously.
     */
    boolean isDone();

    void setIsDone(boolean done);

    void cancel(Uri uri);
}
