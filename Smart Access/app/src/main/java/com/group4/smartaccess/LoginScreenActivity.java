package com.group4.smartaccess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        setTitle("Login");
    }
    public void login (View view){
        Intent consumer = new Intent(this, ConsumerModeActivity.class);
        Intent kiosk = new Intent(this, KioskModeActivity.class);
        EditText email = findViewById(R.id.email);
        String emailEntered = email.getText().toString();
        if(emailEntered.equals(getString(R.string.TestUserEmail))){
            startActivity(consumer);
        }
        else if(emailEntered.equals("kiosk@email.com")){
            startActivity(kiosk);
        }
    }

    public void register (View view){
        Intent register = new Intent(this, RegisterScreenActivity.class);
        startActivity(register);
    }
}
