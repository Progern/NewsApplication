package com.olegmisko.newsapplication.main.Services;

import android.util.Log;

import com.olegmisko.newsapplication.main.Config.AppConstants;
import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationReceivedResult;


public class NewsieNotificationExtenderService extends NotificationExtenderService {


    @Override
    protected boolean onNotificationProcessing(OSNotificationReceivedResult notification) {
        Log.d(AppConstants.LOG_TAG, "Notification received");
        return false;
    }
}
