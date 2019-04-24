package com.group4.smartaccess;

import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
// NFC must be active on this page to send data to the door reader
public class RoomActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback {
    String passCode;
    String guestId;
    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        getSupportActionBar().show();
        setTitle("Enter Room");
        ImageView anim = findViewById(R.id.imageView3);
        Glide.with(this).load(R.drawable.dooranimation2).into(anim);

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
    public NdefMessage createNdefMessage(NfcEvent event) {
        return null;
    }
}
