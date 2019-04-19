package com.group4.smartaccess;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.net.URI;


public class RoomFragment extends Fragment {

    public RoomFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        getActivity().setTitle("Enter Room");
        View rooms = inflater.inflate(R.layout.fragment_room, container, false);
        ImageView anim = rooms.findViewById(R.id.imageView3);
        Glide.with(getActivity()).load(R.drawable.placeholderanim).into(anim);
        // Inflate the layout for this fragment
        return rooms;
    }

}
