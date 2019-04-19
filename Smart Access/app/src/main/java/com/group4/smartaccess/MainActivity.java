package com.group4.smartaccess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
    }

    public void loginButton(View view){
        Intent intent = new Intent(this, LoginScreenActivity.class);
        startActivity(intent);
    }
    public void registerButton(View view){
        Intent intent = new Intent(this, RegisterScreenActivity.class);
        startActivity(intent);
    }
}
