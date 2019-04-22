package com.group4.smartaccess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ReservationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        setTitle("Reservation Information");
        setContentView(R.layout.activity_reservation);
    }
}
