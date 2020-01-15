package com.royce.sololeveling;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int missionID = intent.getIntExtra("ID", 0);
        String missionName = intent.getStringExtra("KEY");

        Bundle bundle = new Bundle();
        bundle.putInt("ID", missionID);
        bundle.putString("KEY", missionName);
        MissionScreen missionScreen = new MissionScreen();
    }
}
