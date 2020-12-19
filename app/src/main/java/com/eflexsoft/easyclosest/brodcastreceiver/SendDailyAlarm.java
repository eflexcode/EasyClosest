package com.eflexsoft.easyclosest.brodcastreceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationManagerCompat;

import com.eflexsoft.easyclosest.MainActivity;
import com.eflexsoft.easyclosest.R;
import com.eflexsoft.easyclosest.ThisApp;

public class SendDailyAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent1 = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 45, intent1, 0);
        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= 26) {
            builder = new Notification.Builder(context, ThisApp.notificationChannelId)
                    .setSmallIcon(R.drawable.ic_wardrobe_notify)
                    .setContentTitle("About your closet")
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setStyle(new Notification.BigTextStyle().bigText("Hey there remember to check your Daily outfit. find out what which outfit you'ved planed for today"))
                    .setContentText("Hey there remember to check your Daily outfit. find out what which outfit you'ved planed for today")
                    .addAction(R.drawable.ic_wardrobe_notify, "Check", pendingIntent);

        } else {
            builder = new Notification.Builder(context)
                    .setSmallIcon(R.drawable.ic_wardrobe_notify)
                    .setContentTitle("About your closet")
                    .setContentText("Hey there remember to check your Daily outfit. find out what which outfit you'ved planed for today")
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setStyle(new Notification.BigTextStyle().bigText("Hey there remember to check your Daily outfit. find out what which outfit you'ved planed for today"))
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setSound(uri);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                builder.setColor(Color.parseColor("#00BCA1"));
                builder.addAction(R.drawable.ic_wardrobe_notify, "Check", pendingIntent);
            }
        }
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(3, builder.build());
    }
}
