package com.group4.smartaccess;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CheckInSuccessfullyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_successfully);
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

    public void returnHome(View view){
        Intent returnHome = new Intent (this, KioskModeActivity.class);
        startActivity(returnHome);
        returnTimer.cancel();
    }

    CountDownTimer returnTimer = new CountDownTimer(10000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            Intent returnHome = new Intent (CheckInSuccessfullyActivity.this, KioskModeActivity.class);
            startActivity(returnHome);
        }
    };

    @Override
    public void onDestroy(){
        super.onDestroy();
        returnTimer.cancel();
    }
}
