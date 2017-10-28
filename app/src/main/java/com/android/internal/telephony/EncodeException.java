package com.android.internal.telephony;

/**
 * Way Lin, 20171024.
 * <p>
 * {@hide}
 */
@SuppressWarnings("serial")
public class EncodeException extends Exception {
    public EncodeException() {
        super();
    }

    public EncodeException(String s) {
        super(s);
    }

    public EncodeException(char c) {
        super("Unencodable char: '" + c + "'");
    }
}

