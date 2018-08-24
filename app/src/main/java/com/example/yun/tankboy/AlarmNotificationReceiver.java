package com.example.yun.tankboy;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class AlarmNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Intent main_intent = new Intent(context, MainActivity.class);
        main_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pintent = PendingIntent.getActivity(
                context, 100,
                main_intent, PendingIntent.FLAG_UPDATE_CURRENT
        );

        Bundle b = intent.getBundleExtra("tips");
        for(String key : b.keySet()){

            String value = b.getString(key);

            builder.setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(key)
                    .setContentText(value)
                    .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                    .setContentInfo("Info")
                    .setContentIntent(pintent);

            NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(randomRange(0,65536),builder.build());
        }

    }

    private int randomRange(int a,int b){
        return (int)(Math.random() * (b - a + 1)) + a;
    }
}