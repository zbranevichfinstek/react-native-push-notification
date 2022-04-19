package com.dieam.reactnativepushnotification.modules;

import static com.dieam.reactnativepushnotification.modules.RNPushNotification.LOG_TAG;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;

import javax.annotation.Nullable;

public class AlarmHeadlessJsService extends HeadlessJsTaskService {

    public static final int SERVICE_NOTIFICATION_ID = 92901;
    private static final String CHANNEL_ID = "RN_BACKGROUND_ACTIONS_CHANNEL";

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground();
    }

    private void startForeground() {
        Log.d(LOG_TAG, "AlarmHeadlessJsService onStartCommand start");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = "Background Service";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
            channel.setLightColor(Color.BLUE);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(channel);

            Notification.Builder builder = new Notification.Builder(this, CHANNEL_ID)
                    .setOngoing(true)
                    .setContentTitle("Foreground Service")
                    .setContentText("Foreground Running")
                    .setCategory(Notification.CATEGORY_SERVICE)
                    ;
            Notification notification = builder.build();
            startForeground(SERVICE_NOTIFICATION_ID, notification);
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Foreground Service")
                    .setContentText("Foreground Running")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);
            Notification notification = builder.build();
            startForeground(SERVICE_NOTIFICATION_ID, notification);
        }

        Log.d(LOG_TAG, "AlarmHeadlessJsService onStartCommand done");
    }

    @Override
    protected @Nullable HeadlessJsTaskConfig getTaskConfig(Intent intent) {
        return new HeadlessJsTaskConfig(
                "AlarmHeadlessJsTask",
                Arguments.createMap(), // extras
                120_000, // timeout for the task
                true // optional: defines whether or not  the task is allowed in foreground. Default is false
        );
    }
}