package com.dieam.reactnativepushnotification.modules;


import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.facebook.react.HeadlessJsTaskService;

import java.util.List;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String EXTRA_ALARM_ID = "james.alarmio.EXTRA_ALARM_ID";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("TAG", "Получено исключение");

        /**
         This part will be called every time network connection is changed
         e.g. Connected -> Not Connected
         **/
//        if (!isAppOnForeground((context))) {
            /**
             We will start our service and send extra info about
             network connections
             **/
            boolean hasInternet = false;//isNetworkAvailable(context);
            Intent serviceIntent = new Intent(context, MyTaskService.class);
            serviceIntent.putExtra("hasInternet", hasInternet);
            context.startService(serviceIntent);
            HeadlessJsTaskService.acquireWakeLockNow(context);
//        }
    }

    public static Class<?> getLaunchActivity(Context context) {
        String activity;

        activity = getMainActivityClassName(context);

        Class<?> launchActivityClass = getClassForName(activity);

        return launchActivityClass;
    }

    private static String getMainActivityClassName(Context context) {
        String packageName = context.getApplicationContext().getPackageName();
        Intent launchIntent =
                context.getApplicationContext().getPackageManager().getLaunchIntentForPackage(packageName);

        if (launchIntent == null || launchIntent.getComponent() == null) {
            return null;
        }

        return launchIntent.getComponent().getClassName();
    }

    private static Class<?> getClassForName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private boolean isAppOnForeground(Context context) {
        /**
         We need to check if app is in foreground otherwise the app will crash.
         http://stackoverflow.com/questions/8489993/check-android-application-is-in-foreground-or-not
         **/
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses =
                activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance ==
                    ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND &&
                    appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }


}