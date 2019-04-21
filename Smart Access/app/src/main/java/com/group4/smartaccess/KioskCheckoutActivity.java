package com.group4.smartaccess;

import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class KioskCheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiosk_checkout);
        getSupportActionBar().hide();
        returnTimer.start();
        TextView errorMessage = findViewById(R.id.textView11);
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

    public void checkOutSuccess (View view){
        Intent checkOutSuccess = new Intent (this, CheckOutSuccessfulActivity.class);
        TextView errorMessage = findViewById(R.id.textView11);
        EditText lastName = findViewById(R.id.editText10);
        String nameEntered = lastName.getText().toString();
        EditText roomNum = findViewById(R.id.editText9);
        String roomEntered = roomNum.getText().toString();
        if(roomEntered.isEmpty()){
            errorMessage.setVisibility(View.VISIBLE);
        }
        else if(Integer.parseInt(roomEntered) == Integer.parseInt(getResources().getString(R.string.RoomNumber)) && nameEntered.equals(getString(R.string.LastName))){
            startActivity(checkOutSuccess);
            returnTimer.cancel();
        }
        else{
            errorMessage.setVisibility(View.VISIBLE);
        }

    }

    CountDownTimer returnTimer = new CountDownTimer(30000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            Intent returnHome = new Intent (KioskCheckoutActivity.this, KioskModeActivity.class);
            startActivity(returnHome);
        }
    };

    @Override
    public void onDestroy(){
        super.onDestroy();
        returnTimer.cancel();
    }
}
