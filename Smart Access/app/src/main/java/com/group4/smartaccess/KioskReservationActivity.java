package com.group4.smartaccess;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class KioskReservationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiosk_reservation);
        getSupportActionBar().hide();
        returnTimer.start();
        TextView errorMessage = findViewById(R.id.textView10);
        errorMessage.setVisibility(View.INVISIBLE);

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

    public void submit(View view){
        TextView textView28 = findViewById(R.id.textView9); // Added to test
        Intent checkIn = new Intent (this, KioskCheckInActivity.class);
        EditText reservationNum = findViewById(R.id.reservation);
        String reservationEntered = reservationNum.getText().toString();
        String reservationCheck = getResources().getString(R.string.ReservationNumber);
        TextView errorMessage = findViewById(R.id.textView10);
        if(reservationEntered.isEmpty()){
            errorMessage.setVisibility(View.VISIBLE);
        }
        else{
            if(Integer.parseInt(reservationEntered) == Integer.parseInt(reservationCheck)){
                // send reservation number to the server
                final ServerConnect serverConnect = new ServerConnect(); // Added to test. Add textview to constructor to test
                serverConnect.execute("http://smartaccess.openode.io/"); // URL goes here. Change this line to the correct URL
                startActivity(checkIn);
                returnTimer.cancel();
            }
            else{
                errorMessage.setVisibility(View.VISIBLE);
            }
        }
    }

    CountDownTimer returnTimer = new CountDownTimer(30000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            Intent returnHome = new Intent (KioskReservationActivity.this, KioskModeActivity.class);
            startActivity(returnHome);
        }
    };

    @Override
    public void onDestroy(){
        super.onDestroy();
        returnTimer.cancel();
    }



}
