package com.dieam.reactnativepushnotification.modules;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.facebook.react.HeadlessJsTaskService;

import java.util.List;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, AlarmHeadlessJsService.class);
        Application applicationContext = (Application) context.getApplicationContext();
        ContextCompat.startForegroundService(applicationContext, serviceIntent);
        HeadlessJsTaskService.acquireWakeLockNow(context);
    }
}