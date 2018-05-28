package com.myalarm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

public class AlarmService extends IntentService {


    private NotificationManager mNotificationManager;

    public AlarmService() {

        super("AlarmService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        showNotification("Alarm is ringing");
    }

    private void showNotification(String message) {
        mNotificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle("Alarm")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentText(message);

        builder.setContentIntent(pendingIntent);
        mNotificationManager.notify(1, builder.build());

    }
}
