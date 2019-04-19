package com.group4.smartaccess;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AccountFragment extends Fragment {

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        getActivity().setTitle("Account Details");
        // Inflate the layout for this fragment
        View account = inflater.inflate(R.layout.fragment_account, container, false);
        final View signOut = account.findViewById(R.id.textView30);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut(v);
            }
        });
        return account;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    public void signOut (View view){
        Intent signOut = new Intent (getActivity(), LoginScreenActivity.class);
        getActivity().startActivity(signOut);
    }
}
