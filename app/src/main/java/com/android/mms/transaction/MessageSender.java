package com.android.mms.transaction;

import com.google.android.mms.MmsException;

/**
 * Way Lin, 20171102.
 */

public interface MessageSender {
    public static final String RECIPIENTS_SEPARATOR = ";";

    /**
     * Send the message through MMS or SMS protocol.
     *
     * @param token The token to identify the sending progress.
     * @return True if the message was sent through MMS or false if it was
     * sent through SMS.
     * @throws MmsException Error occurred while sending the message.
     */
    boolean sendMessage(long token) throws Throwable;
}
