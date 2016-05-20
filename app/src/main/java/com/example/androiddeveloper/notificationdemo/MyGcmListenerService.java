package com.example.androiddeveloper.notificationdemo;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import java.util.ArrayList;


public class MyGcmListenerService extends GcmListenerService {
    public static final String TAG = "From Service";
    String Title;
    String message;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        Title = data.getString("title");
        message = data.getString("message");
        Log.d(TAG, "onMessageReceived: Branch " + Title);
        Log.d(TAG, "onMessageReceived: Message " + message);

//        Intent intent = new Intent(MyGcmListenerService.this,MainActivity.class);
//        intent.putExtra("message", message);
//        startActivity(intent);
        notifyUser(Title,message);

    }



    public void notifyUser(String Title , String message) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext());

        notification.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("GTU paper")
                .setContentTitle("" + Title)
                .setContentText(message)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent)
                .setContentInfo("Info");

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(10, notification.build());
    }
}
