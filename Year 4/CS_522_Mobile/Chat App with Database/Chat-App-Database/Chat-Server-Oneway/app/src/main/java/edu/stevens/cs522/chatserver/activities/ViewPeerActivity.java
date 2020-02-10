package edu.stevens.cs522.chatserver.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.List;

import edu.stevens.cs522.chatserver.R;
import edu.stevens.cs522.chatserver.contracts.MessageContract;
import edu.stevens.cs522.chatserver.databases.ChatDbAdapter;
import edu.stevens.cs522.chatserver.entities.Peer;

/**
 * Created by dduggan.
 */

public class  ViewPeerActivity extends Activity {

    public static final String PEER_ID_KEY = "peer-id";

    private ChatDbAdapter chatDbAdapter;

    private SimpleCursorAdapter messagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_peer);

        chatDbAdapter = new ChatDbAdapter(this);
        chatDbAdapter.open();

        long peerId = getIntent().getLongExtra(PEER_ID_KEY, -1);
        if (peerId < 0) {
            throw new IllegalArgumentException("Expected peer id as intent extra");
        }
        Peer thePeer = chatDbAdapter.fetchPeer(peerId);
        Cursor messagesCursor = chatDbAdapter.fetchMessagesFromPeer(thePeer);
        //startManagingCursor(messagesCursor);

        messagesAdapter = new SimpleCursorAdapter(this, R.layout.messages, messagesCursor, new String[]{
                MessageContract.SENDER, MessageContract.MESSAGE_TEXT
        }, new int[] {android.R.id.text1, android.R.id.text2});

        // TODO init the UI
        TextView userName = findViewById(R.id.view_user_name);
        TextView timeStamp = findViewById(R.id.view_timestamp);
        TextView address = findViewById(R.id.view_address);
        ListView messages = findViewById(R.id.view_messages);

        System.out.println(thePeer.id);
        System.out.println(thePeer.address);
        System.out.println(thePeer.timestamp);
        System.out.println(thePeer.name);


        userName.setText(thePeer.name);
        timeStamp.setText(thePeer.timestamp.toString());
        address.setText(thePeer.address.toString());
        messages.setAdapter(messagesAdapter);

    }

}
