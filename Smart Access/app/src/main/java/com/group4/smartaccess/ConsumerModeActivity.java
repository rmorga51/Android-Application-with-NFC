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
import android.widget.Button;
import android.widget.TextView;

public class ConsumerModeActivity extends AppCompatActivity implements AsyncResponse {


    String server_response;
    Button button17;
    GetDoorKey getDoorKey;
    String guestID;
    String roomNum;
    String transactionID;
    String passCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_mode);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getDoorKey.delegate = this;
        button17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDoorKey = new GetDoorKey(ConsumerModeActivity.this);
                //getDoorKey.execute("http://smartaccess.openode.io/api/org.example.basic.Guest/9208"); // sends ID
                getDoorKey.execute("http://169.254.43.142:3000/api/org.example.basic.Guest/9208");
            }
        });

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

    @Override
    public void processFinish(String output) {
        // TODO: Add either the pass code or transaction id
        guestID = output.substring(47,51); // contains guest id
        passCode = output.substring(222,227); // contains pass code
        server_response = output;
        Intent intent = new Intent(this, RoomActivity.class);
        intent.putExtra("PASS_CODE", passCode); // null for now
        intent.putExtra("GUEST_ID", guestID);
        startActivity(intent);
    }
}
