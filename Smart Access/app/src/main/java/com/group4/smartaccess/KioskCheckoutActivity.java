package com.group4.smartaccess;

import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class KioskCheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiosk_checkout);
        getSupportActionBar().hide();
        returnTimer.start();
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
        startActivity(checkOutSuccess);
        returnTimer.cancel();
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
