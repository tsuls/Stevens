package edu.stevens.cs522.chatserver.contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import java.net.InetAddress;

/**
 * Created by dduggan.
 */

public class PeerContract implements BaseColumns {

    // TODO define column names, getters for cursors, setters for contentvalues

    public static final String NAME = "Name";

    public static final String TIMESTAMP = "Timestamp";

    public static final String ADDRESS = "IP_Address";

    private static int nameColumn = -1;
    private static int timestampColumn = -1;
    private static int addressColumn = -1;



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
}
