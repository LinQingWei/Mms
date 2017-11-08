package com.way.mms.transaction;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Patterns;

import com.google.android.mms.pdu_alt.CharacterSets;
import com.google.android.mms.pdu_alt.EncodedStringValue;
import com.google.android.mms.pdu_alt.PduPersister;
import com.way.mms.MmsConfig;
import com.way.mms.R;
import com.way.mms.data.Message;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Way Lin, 20171028.
 */

public class SmsHelper {
    public static final Uri DRAFTS_CONTENT_PROVIDER = Uri.parse("content://sms/draft");
    public static final Uri RECEIVED_MESSAGE_CONTENT_PROVIDER = Uri.parse("content://sms/inbox");
    public static final Uri CONVERSATIONS_CONTENT_PROVIDER = Uri.parse("content://mms-sms/conversations?simple=true");

    public static final String sortDateDesc = "date DESC";
    public static final String sortDateAsc = "date ASC";

    public static final byte UNREAD = 0;
    public static final byte READ = 1;

    // Columns for SMS content providers
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_THREAD_ID = "thread_id";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_RECIPIENT = "recipient_ids";
    public static final String COLUMN_PERSON = "person";
    public static final String COLUMN_SNIPPET = "snippet";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_DATE_NORMALIZED = "normalized_date";
    public static final String COLUMN_DATE_SENT = "date_sent";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_ERROR = "error";
    public static final String COLUMN_READ = "read";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_MMS = "ct_t";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_SUB = "sub";
    public static final String COLUMN_MSG_BOX = "msg_box";
    public static final String COLUMN_SUBJECT = "subject";
    public static final String COLUMN_BODY = "body";
    public static final String COLUMN_SEEN = "seen";

    public static final String READ_SELECTION = COLUMN_READ + " = " + READ;
    public static final String UNREAD_SELECTION = COLUMN_READ + " = " + UNREAD;
    public static final String UNSEEN_SELECTION = COLUMN_SEEN + " = " + UNREAD;
    public static final String FAILED_SELECTION = COLUMN_TYPE + " = " + Message.FAILED;

    /**
     * MMS address parsing data structures
     */
    // allowable phone number separators
    private static final char[] NUMERIC_CHARS_SUGAR = {
            '-', '.', ',', '(', ')', ' ', '/', '\\', '*', '#', '+'
    };

    private static String[] sNoSubjectStrings;

    /**
     * Add incoming SMS to inbox
     *
     * @param context
     * @param address Address of sender
     * @param body    Body of incoming SMS message
     * @param time    Time that incoming SMS message was sent at
     */
    public static Uri addMessageToInbox(Context context, String address, String body, long time) {

        ContentResolver contentResolver = context.getContentResolver();
        ContentValues cv = new ContentValues();

        cv.put("address", address);
        cv.put("body", body);
        cv.put("date_sent", time);

        return contentResolver.insert(RECEIVED_MESSAGE_CONTENT_PROVIDER, cv);
    }

    public static String extractEncStrFromCursor(Cursor cursor,
                                                 int columnRawBytes, int columnCharset) {
        String rawBytes = cursor.getString(columnRawBytes);
        int charset = cursor.getInt(columnCharset);

        if (TextUtils.isEmpty(rawBytes)) {
            return "";
        } else if (charset == CharacterSets.ANY_CHARSET) {
            return rawBytes;
        } else {
            return new EncodedStringValue(charset, PduPersister.getBytes(rawBytes)).getString();
        }
    }

    /**
     * cleanseMmsSubject will take a subject that's says, "<Subject: no subject>", and return
     * a null string. Otherwise it will return the original subject string.
     *
     * @param context a regular context so the function can grab string resources
     * @param subject the raw subject
     * @return
     */
    public static String cleanseMmsSubject(Context context, String subject) {
        if (TextUtils.isEmpty(subject)) {
            return subject;
        }
        if (sNoSubjectStrings == null) {
            sNoSubjectStrings = context.getResources().getStringArray(R.array.empty_subject_strings);
        }

        final int len = sNoSubjectStrings.length;
        for (int i = 0; i < len; i++) {
            if (subject.equalsIgnoreCase(sNoSubjectStrings[i])) {
                return null;
            }
        }
        return subject;
    }

    /**
     * Is the specified address an email address?
     *
     * @param address the input address to test
     * @return true if address is an email address; false otherwise.
     * @hide
     */
    public static boolean isEmailAddress(String address) {
        if (TextUtils.isEmpty(address)) {
            return false;
        }

        String s = extractAddrSpec(address);
        Matcher match = Patterns.EMAIL_ADDRESS.matcher(s);
        return match.matches();
    }

    /**
     * Regex pattern for names and email addresses.
     * <ul>
     * <li><em>mailbox</em> = {@code name-addr}</li>
     * <li><em>name-addr</em> = {@code [display-name] angle-addr}</li>
     * <li><em>angle-addr</em> = {@code [CFWS] "<" addr-spec ">" [CFWS]}</li>
     * </ul>
     *
     * @hide
     */
    public static final Pattern NAME_ADDR_EMAIL_PATTERN =
            Pattern.compile("\\s*(\"[^\"]*\"|[^<>\"]+)\\s*<([^<>]+)>\\s*");

    /**
     * Helper method to extract email address from address string.
     *
     * @hide
     */
    public static String extractAddrSpec(String address) {
        Matcher match = NAME_ADDR_EMAIL_PATTERN.matcher(address);

        if (match.matches()) {
            return match.group(2);
        }
        return address;
    }


    // An alias (or commonly called "nickname") is:
    // Nickname must begin with a letter.
    // Only letters a-z, numbers 0-9, or . are allowed in Nickname field.
    public static boolean isAlias(String string) {
        if (!MmsConfig.isAliasEnabled()) {
            return false;
        }

        int len = string == null ? 0 : string.length();

        if (len < MmsConfig.getAliasMinChars() || len > MmsConfig.getAliasMaxChars()) {
            return false;
        }

        if (!Character.isLetter(string.charAt(0))) {    // Nickname begins with a letter
            return false;
        }
        for (int i = 1; i < len; i++) {
            char c = string.charAt(i);
            if (!(Character.isLetterOrDigit(c) || c == '.')) {
                return false;
            }
        }

        return true;
    }

    private static HashMap numericSugarMap = new HashMap(NUMERIC_CHARS_SUGAR.length);

    /**
     * Given a phone number, return the string without syntactic sugar, meaning parens,
     * spaces, slashes, dots, dashes, etc. If the input string contains non-numeric
     * non-punctuation characters, return null.
     */
    private static String parsePhoneNumberForMms(String address) {
        StringBuilder builder = new StringBuilder();
        int len = address.length();

        for (int i = 0; i < len; i++) {
            char c = address.charAt(i);

            // accept the first '+' in the address
            if (c == '+' && builder.length() == 0) {
                builder.append(c);
                continue;
            }

            if (Character.isDigit(c)) {
                builder.append(c);
                continue;
            }

            if (numericSugarMap.get(c) == null) {
                return null;
            }
        }
        return builder.toString();
    }

    /**
     * parse the input address to be a valid MMS address.
     * - if the address is an email address, leave it as is.
     * - if the address can be parsed into a valid MMS phone number, return the parsed number.
     * - if the address is a compliant alias address, leave it as is.
     */
    public static String parseMmsAddress(String address) {
        // if it's a valid Email address, use that.
        if (isEmailAddress(address)) {
            return address;
        }

        // if we are able to parse the address to a MMS compliant phone number, take that.
        String retVal = parsePhoneNumberForMms(address);
        if (retVal != null && retVal.length() != 0) {
            return retVal;
        }

        // if it's an alias compliant address, use that.
        if (isAlias(address)) {
            return address;
        }

        // it's not a valid MMS address, return null
        return null;
    }
}
