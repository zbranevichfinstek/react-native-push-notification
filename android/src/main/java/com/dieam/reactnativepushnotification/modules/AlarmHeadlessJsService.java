package com.dieam.reactnativepushnotification.modules;

import static com.dieam.reactnativepushnotification.modules.RNPushNotification.LOG_TAG;

import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;
import javax.annotation.Nullable;

public class AlarmHeadlessJsService extends HeadlessJsTaskService {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "AlarmHeadlessJsService onStartCommand 1");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder builder = new Notification.Builder(this, "BuzzerWakeUp")
                    .setContentTitle("Foreground Service (Show it to the developer)")
                    .setContentText("Foreground Running")
                    .setAutoCancel(true);
            Notification notification = builder.build();
            startForeground(1, notification);
            Log.d(LOG_TAG, "AlarmHeadlessJsService onStartCommand 2 O");
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "BuzzerWakeUp")
                    .setContentTitle("Foreground Service (Show it to the developer)")
                    .setContentText("Foreground Running")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);
            Notification notification = builder.build();
            startForeground(1, notification);
            Log.d(LOG_TAG, "AlarmHeadlessJsService onStartCommand 2 none");
        }

        return super.onStartCommand(intent, flags, startId);
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