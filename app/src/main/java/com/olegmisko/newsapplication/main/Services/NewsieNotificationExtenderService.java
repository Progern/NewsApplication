package com.olegmisko.newsapplication.main.Services;

import android.util.Log;

import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationReceivedResult;


public class NewsieNotificationExtenderService extends NotificationExtenderService {

    public static final String LOG_TAG = "MY_LOG";

    @Override
    protected boolean onNotificationProcessing(OSNotificationReceivedResult notification) {
        Log.d(LOG_TAG, "Notification received");
        return false;
    }
}
