package edu.stevens.cs522.chatserver.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import edu.stevens.cs522.chatserver.contracts.MessageContract;

/**
 * Created by dduggan.
 */

public class Message implements Parcelable, Persistable {

    public long id;

    public String messageText;

    public Date timestamp;

    public String sender;

    public long senderId;

    public Message() {
    }

    public Message(Cursor cursor) {
        // TODO
        id = cursor.getLong(cursor.getColumnIndex(MessageContract._ID));
        messageText = cursor.getString(cursor.getColumnIndex(MessageContract.MESSAGE_TEXT));
        timestamp = new Date (cursor.getLong(cursor.getColumnIndex(MessageContract.TIMESTAMP)));
        sender = cursor.getString(cursor.getColumnIndex(MessageContract.SENDER));
        senderId = cursor.getLong(cursor.getColumnIndex(MessageContract.FOREIGN_KEY));
    }

    public Message(Parcel in) {
        // TODO
        id = in.readLong();
        messageText = in.readString();
        timestamp = (java.util.Date) in.readSerializable();
        sender = in.readString();
        senderId = in.readLong();
    }

    @Override
    public void writeToProvider(ContentValues out) {
        // TODO
        MessageContract.putId(out, id);
        MessageContract.putMessageText(out, messageText);
        MessageContract.putTimestamp(out, timestamp.getTime());
        MessageContract.putSender(out, sender);
        MessageContract.putFK(out, (int) senderId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO
        dest.writeLong(id);
        dest.writeString(messageText);
        dest.writeSerializable(timestamp);
        dest.writeString(sender);
        dest.writeLong(senderId);
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {

        @Override
        public Message createFromParcel(Parcel source) {
            // TODO
            return new Message(source);
        } private static int nameColumn = -1;
        private static int timestampColumn = -1;
        private static int addressColumn = -1;
        private static int IDColumn = -1;



        public static String getName(Cursor cursor) {
            if (nameColumn < 0) {
                nameColumn = cursor.getColumnIndexOrThrow(NAME);
            }
            return cursor.getString(nameColumn);
        }

        public static void putName(ContentValues out, String name) {
            out.put(NAME, name);
        }

        public static Long getTimestamp(Cursor cursor) {
            if(timestampColumn < 0) {
                timestampColumn = cursor.getColumnIndexOrThrow(TIMESTAMP);
            }
            return cursor.getLong(timestampColumn);
        }

        public static void putTimestamp(ContentValues out, long timestamp) {
            out.put(TIMESTAMP, timestamp);
        }

        public static String getAddress(Cursor cursor) {
            if(addressColumn < 0) {
                addressColumn = cursor.getColumnIndexOrThrow(ADDRESS);
            }
            return cursor.getString(addressColumn);
        }

        public static void putAddres(ContentValues out, String address) {
            out.put(ADDRESS, address);
        }
        public static long getId(Cursor cursor) {
            if (IDColumn < 0) {
                IDColumn = cursor.getColumnIndexOrThrow(_ID);
            }
            return cursor.getInt(IDColumn);
        }

        public static void putId(ContentValues out, long id) {
            out.put(_ID, id);
        }

    }


    @Override
        public Message[] newArray(int size) {
            // TODO
            return new Message[size];
        }

    };

}

