package com.group4.smartaccess;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class KioskModeActivity extends AppCompatActivity {

    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiosk_mode);
        getSupportActionBar().hide();
        count = 0;

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

    public void checkIn(View view){
        Intent checkIn = new Intent (this, KioskCheckInActivity.class);
        startActivity(checkIn);
    }

    public void checkOut(View view){
        Intent checkOut = new Intent (this, KioskCheckoutActivity.class);
        startActivity(checkOut);
    }

    public void logOut(View view){
        Intent logIn = new Intent (this, LoginScreenActivity.class);
        count++;
        if(count == 3){
            startActivity(logIn);
        }
    }


}
