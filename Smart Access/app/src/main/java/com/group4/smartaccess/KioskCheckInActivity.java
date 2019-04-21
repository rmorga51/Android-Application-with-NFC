package com.group4.smartaccess;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class KioskCheckInActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback {
// TODO Add request to retrieve value from server to this activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiosk_check_in);
        getSupportActionBar().hide();
        ImageView anim = findViewById(R.id.imageView2);
        Glide.with(this).load(R.drawable.placeholderanim).into(anim);
        returnTimer.start();
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
    public NdefMessage createNdefMessage(NfcEvent event) {
        String msg = ""; // this will hold the
        return null;
    }
}
