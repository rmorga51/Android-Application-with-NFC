package com.group4.smartaccess;

import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class KioskCheckInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiosk_check_in);
        getSupportActionBar().hide();
        ImageView anim = findViewById(R.id.imageView2);
        Glide.with(this).load(R.drawable.placeholderanim).into(anim);
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
}
