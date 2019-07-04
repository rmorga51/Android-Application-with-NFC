package com.group4.smartaccess;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
// NFC must be active on this page to send data to the door reader
public class RoomActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback, AsyncResponse, SendNDEF {

    String payload;
    String passCode;
    String guestId;
    String msg;
    String server_response;
    NdefMessage transactionID = null;
    AccessRoom accessRoom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        getSupportActionBar().show();
        setTitle("Enter Room");
        ImageView anim = findViewById(R.id.imageView3);
        Glide.with(this).load(R.drawable.dooranimation3).into(anim);

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            passCode = extras.getString("PASS_CODE"); // should contain the room key
            guestId = extras.getString("GUEST_ID"); // should contain the guest id
        }


        // Include nfc functions
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null){
            Toast.makeText(this,"You must enable NFC", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "NFC is enabled", Toast.LENGTH_SHORT).show();
        }
        msg = guestId + " " + passCode; // guest id and passcode with a space as a delimiter

        // Set callback
        nfcAdapter.setNdefPushMessageCallback(this,this);


    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) { // should detect nfc event
        // Create some NDEF records
        accessRoom = new AccessRoom(RoomActivity.this);
        //accessRoom.execute("http://smartaccess.openode.io/api/org.example.basic.openRoom", payload); // URL goes here. Change this line to the correct URL, second parameter is the payload
        accessRoom.execute("http://169.254.43.142:3000/api/org.example.basic.openRoom", payload); // URL goes here. Change this line to the correct URL, second parameter is the payload
        /*NdefRecord record1 = NdefRecord.createMime("application/vnd.com.royce.nfcapp_04", msg.getBytes()); // mimetype may have to be changed
        NdefMessage ndef = new NdefMessage(record1);*/
        while(transactionID == null){}
        return transactionID;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        //super.onNewIntent(intent);
        if(intent.getAction().equals(NfcAdapter.ACTION_NDEF_DISCOVERED)){ // only use this if the intent is nfc
            super.onNewIntent(intent);
        }
/*        else{
            setIntent(intent);
        }*/
        //setIntent(intent);
    }

    void processIntent(Intent intent){

        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        // Only one message sent during the beam
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        // Record 0 contains the MIME type, record 1 is the AAr, if present
        payload = new String(msg.getRecords()[0].getPayload()); // holds the actual nfc payload



    }

    @Override
    public void processFinish(String output) {
        // TODO: Add either the pass code or transaction id

        //transactionID = output;
        /*transactionID =
        Intent intent = new Intent(this, RoomActivity.class);
        *//*intent.putExtra("PASS_CODE", passCode); // null for now
        intent.putExtra("GUEST_ID", guestID);*//*
        startActivity(intent);*/
    }

    @Override
    public void sendMessage(NdefMessage output1) {
        transactionID = output1;
    }
}
