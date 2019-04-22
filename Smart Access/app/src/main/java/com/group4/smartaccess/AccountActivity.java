package com.group4.smartaccess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        setTitle("Account Details");
        setContentView(R.layout.activity_account);
    }

    public void signOut (View view){
        Intent signOut = new Intent (this, LoginScreenActivity.class);
        startActivity(signOut);
    }
}
