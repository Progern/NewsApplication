package com.olegmisko.newsapplication.main.Services;


import android.content.Context;
import android.content.Intent;

import com.olegmisko.newsapplication.main.Activities.WebViewActivity;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

public class NotificationHandlerService implements OneSignal.NotificationOpenedHandler {

    private Context context;

    public NotificationHandlerService (Context context) {
        this.context = context;
    }


    @Override
    public void notificationOpened(OSNotificationOpenResult result) {
        if (result.notification.payload.launchURL != null) {
            Intent loadNews = new Intent(context, WebViewActivity.class);
            loadNews.putExtra("URL", result.notification.payload.launchURL);
            loadNews.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           context.startActivity(loadNews);
        }
    }
}
