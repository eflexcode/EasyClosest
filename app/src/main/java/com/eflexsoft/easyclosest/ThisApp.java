package com.eflexsoft.easyclosest;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class ThisApp extends Application {

    public static String notificationChannelName = "Daily check closet notification";
    public static String notificationChannelId = "EasyClosetChannel1";

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel("EasyClosetChannel1"
                    , notificationChannelName
                    , NotificationManager.IMPORTANCE_HIGH);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
    }
}
