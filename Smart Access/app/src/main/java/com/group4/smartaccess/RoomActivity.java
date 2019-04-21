package com.group4.smartaccess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class RoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        getSupportActionBar().show();
        setTitle("Enter Room");
        ImageView anim = findViewById(R.id.imageView3);
        Glide.with(this).load(R.drawable.placeholderanim).into(anim);

    }
}
