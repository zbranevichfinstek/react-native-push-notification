package com.dieam.reactnativepushnotification.modules;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;
import javax.annotation.Nullable;

public class AlarmHeadlessJsService extends HeadlessJsTaskService {

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