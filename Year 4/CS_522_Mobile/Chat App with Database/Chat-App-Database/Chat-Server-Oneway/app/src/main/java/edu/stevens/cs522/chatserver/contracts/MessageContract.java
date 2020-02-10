package edu.stevens.cs522.chatserver.contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * Created by dduggan.
 */

public class MessageContract implements BaseColumns {

    public static final String MESSAGE_TEXT = "message_text";

    public static final String TIMESTAMP = "timestamp";

    public static final String SENDER = "sender";

    public static final String SENDER_ID = "sender_ID";

    public static final String FOREIGN_KEY = "peer_fk";

    // TODO remaining columns in Messages table

    private static int messageTextColumn = -1;
    private static int timestampColumn = -1;
    private static int senderColumn = -1;
    private static int senderIDColumn = - 1;
    private static int fkColumn = -1;

    public static String getMessageText(Cursor cursor) {
        if (messageTextColumn < 0) {
            messageTextColumn = cursor.getColumnIndexOrThrow(MESSAGE_TEXT);
        }
        return cursor.getString(messageTextColumn);
    }

    public static void putMessageText(ContentValues out, String messageText) {
        out.put(MESSAGE_TEXT, messageText);
    }

    // TODO remaining getter and putter operations for other columns
    public static Long getTimestamp(Cursor cursor) {
        if(timestampColumn < 0) {
            timestampColumn = cursor.getColumnIndexOrThrow(TIMESTAMP);
        }
        return cursor.getLong(timestampColumn);
    }

    public static void putTimestamp(ContentValues out, long timestamp) {
        out.put(TIMESTAMP, timestamp);
    }

    public static String getSender(Cursor cursor) {
        if(senderColumn < 0) {
            senderColumn = cursor.getColumnIndexOrThrow(SENDER);
        }
        return cursor.getString(senderColumn);
    }

    public static void putSender(ContentValues out, String sender){
        out.put(SENDER, sender);
    }

    public static long getSenderId(Cursor cursor) {
        if(senderIDColumn < 0) {
            senderIDColumn = cursor.getColumnIndexOrThrow(SENDER_ID);
        }
        return cursor.getLong(senderIDColumn);
    }

    public static void putSenderId(ContentValues out, long senderId){
        out.put(SENDER_ID, senderId);
    }

    public static int getFK(Cursor cursor) {
        if (fkColumn < 0) {
            fkColumn = cursor.getColumnIndexOrThrow(FOREIGN_KEY);
        }
        return cursor.getInt(fkColumn);
    }

    public static void putFK(ContentValues out, int fk) {
        out.put(FOREIGN_KEY, fk);
    }
}
