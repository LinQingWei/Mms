package com.way.mms.common.google;

/**
 * Callback interface for a background item loaded request.
 * <p>
 * freeme.linqingwei, 20171123.
 */

public interface ItemLoadedCallback<T> {
    /**
     * Called when an item's loading is complete. At most one of {@code result}
     * and {@code exception} should be non-null.
     *
     * @param result the object result, or {@code null} if the request failed or
     *               was cancelled.
     */
    void onItemLoaded(T result, Throwable exception);
}
