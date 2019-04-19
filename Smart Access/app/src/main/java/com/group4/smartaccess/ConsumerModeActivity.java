package com.group4.smartaccess;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class ConsumerModeActivity extends AppCompatActivity {


    final Fragment roomFragment = new RoomFragment();
    final Fragment reservationFragment = new ReservationFragment();
    final Fragment accountFragment = new AccountFragment();
    final FragmentManager manager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_mode);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
    @Override
    public  void onResume(){
        super.onResume();
        getSupportActionBar().hide();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_reservations:
                    manager.beginTransaction().replace(R.id.consumer_container, reservationFragment, "reserve").commit();
                    return true;
                case R.id.navigation_room:
                    manager.beginTransaction().replace(R.id.consumer_container, roomFragment, "room").commit();
                    return true;
                case R.id.navigation_account:
                    manager.beginTransaction().replace(R.id.consumer_container, accountFragment, "account").commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        if (item.getItemId() == android.R.id.home){
          onBackPressed();
          return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(manager.getBackStackEntryCount() > 0){
            manager.popBackStack();
        }
        else {
            super.onBackPressed();
            Intent consumer = new Intent (this, ConsumerModeActivity.class);
            startActivity(consumer);
        }
    }
}
