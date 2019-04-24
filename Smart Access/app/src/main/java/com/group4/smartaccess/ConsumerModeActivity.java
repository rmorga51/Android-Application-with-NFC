package com.group4.smartaccess;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ConsumerModeActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_mode);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public  void onResume(){
        super.onResume();
        getSupportActionBar().hide();
    }



    public void viewReservation (View view){
        Intent reservation = new Intent (this, ReservationActivity.class);
        startActivity(reservation);
    }

    public void viewRoom (View view){
        Intent room = new Intent (this, RoomActivity.class);
        startActivity(room);
    }

    public void viewAccount (View view){
        Intent account = new Intent (this, AccountActivity.class);
        startActivity(account);
    }
}
