package com.group4.smartaccess;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class KioskReservationActivity extends AppCompatActivity implements AsyncResponse{
    // TODO Add request to retrieve value from server to this activity
    final ServerConnect serverConnect = new ServerConnect(this);
    String server_response;
    Intent checkIn;
    TextView textView28;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiosk_reservation);
        getSupportActionBar().hide();
        returnTimer.start();

        textView28 = findViewById(R.id.textView9); // Added to test
        serverConnect.delegate = this;

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
        checkIn = new Intent (this, KioskCheckInActivity.class);
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
                serverConnect.execute("http://smartaccess.openode.io/"); // URL goes here. Change this line to the correct URL
                Log.e("Response:", ""+ server_response);
            }
            else{
                errorMessage.setVisibility(View.VISIBLE);
            }
        }
    }

    public void startActivity(){
        checkIn.putExtra("RESPONSE", server_response); // add the server_response to the intent
        Log.e("Response:", "You made it: "+ server_response);
        startActivity(checkIn);
        returnTimer.cancel();
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

    // receives value from the Async task ServerConnect
    @Override
    public void processFinish(String output) {
        server_response = output;
    }
}
