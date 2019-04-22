package com.group4.smartaccess;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class ReservationActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback{

    String msg;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        setTitle("Reservation Information");
        setContentView(R.layout.activity_reservation);

        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null){
            Toast.makeText(this,"You must enable NFC", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "NFC is enabled", Toast.LENGTH_SHORT).show();
        }
        textView = findViewById(R.id.textView37);
        msg = textView.getText().toString(); // holds the reservation number, may also hold another value

        // Set callback
        nfcAdapter.setNdefPushMessageCallback(this,this);
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) { // detects an nfc event
        // Create some NDEF records
        NdefRecord record1 = NdefRecord.createMime("application/vnd.com.royce.nfcapp_04", msg.getBytes()); // mimetype may have to be changed
        NdefMessage ndef = new NdefMessage(record1);
        return ndef;
    }

    @Override
    public void onResume(){
        super.onResume();
        if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())){ // runs after onNewIntent
            processIntent(getIntent());
        }
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
        String payload = new String(msg.getRecords()[0].getPayload()); // holds the actual nfc payload
        //textView.setText(payload);


    }
}
