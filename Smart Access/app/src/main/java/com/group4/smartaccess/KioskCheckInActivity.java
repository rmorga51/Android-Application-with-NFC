package com.group4.smartaccess;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class KioskCheckInActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback {
// TODO Add request to retrieve value from server to this activity

    String payload;
    String msg;
    TextView textView;
    final SendReservation sendReservation = new SendReservation(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiosk_check_in);
        getSupportActionBar().hide();
        ImageView anim = findViewById(R.id.imageView2);
        Glide.with(this).load(R.drawable.kioskanimation).into(anim);
        returnTimer.start();
        // retrieve extra here
        textView = findViewById(R.id.textView28);
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            msg = extras.getString("RESPONSE"); // should contain the server_response
        }
        //TODO add NFC here
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null){
            Toast.makeText(this,"You must enable NFC", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "NFC is enabled", Toast.LENGTH_SHORT).show();
        }

        // Set callback
        nfcAdapter.setNdefPushMessageCallback(this,this);
    }

    @Override
    public void onResume(){
        super.onResume();
        if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())){ // runs after onNewIntent
            processIntent(getIntent());
        }
        FullScreencall();
    }

    public void FullScreencall() {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
    }

    //For frontend testing purposes only
    public void testCheckIn(View view){
        Intent checkInSuccess = new Intent (this, CheckInSuccessfullyActivity.class);
        startActivity(checkInSuccess);
        returnTimer.cancel();
    }

    CountDownTimer returnTimer = new CountDownTimer(30000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            Intent returnHome = new Intent (KioskCheckInActivity.this, KioskModeActivity.class);
            startActivity(returnHome);
        }
    };

    @Override
    public void onDestroy(){
        super.onDestroy();
        returnTimer.cancel();
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) { // detects an nfc event
        // Create some NDEF records
        NdefRecord record1 = NdefRecord.createMime("application/vnd.com.royce.nfcapp_04", msg.getBytes()); // mimetype may have to be changed
        NdefMessage ndef = new NdefMessage(record1);
        return ndef;
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
        sendReservation.execute("http://smartaccess.openode.io/api/org.example.basic.checkIn", payload); // URL goes here. Change this line to the correct URL, second parameter is the payload
        //sendReservation.execute("http://169.254.43.142:3000/api/org.example.basic.checkIn", payload); // URL goes here. Change this line to the correct URL, second parameter is the payload



    }

    public void updateView(){
        // USE THIS METHOD TO UPDATE SCREEN AFTER CHECK-IN OCCURS
        Intent checkInSuccess = new Intent (this, CheckInSuccessfullyActivity.class);
        startActivity(checkInSuccess);
        returnTimer.cancel();
    }


}
