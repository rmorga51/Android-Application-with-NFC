package com.royce.sololeveling;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    final String TITLE = "TITLE";
    final String MESSAGE = "KEY";
    final String P_INTENT = "PINTENT";
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra(TITLE);
        String message = intent.getStringExtra(MESSAGE);
        PendingIntent pendingIntent = (PendingIntent) intent.getParcelableExtra(P_INTENT);

        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb =  notificationHelper.getChannel1Notification(title, message);
        nb.setContentIntent(pendingIntent); // nb is set equal to all of the settings in Helper. This is added to it.
        notificationHelper.getManager().notify(1, nb.build());
    }

    public void resetAlarm(Context context, Intent intent){
        Intent mAIntent = new Intent(context, MainActivity.class);

    }

}