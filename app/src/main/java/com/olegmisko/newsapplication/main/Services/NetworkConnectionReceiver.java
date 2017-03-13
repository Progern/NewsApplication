package com.olegmisko.newsapplication.main.Services;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.olegmisko.newsapplication.main.Config.AppConstants;


public class NetworkConnectionReceiver extends BroadcastReceiver {

    private LocalBroadcastManager broadcastManager;
    private Intent intent;



    @Override
    public void onReceive(Context context, Intent intent) {
        broadcastManager = LocalBroadcastManager.getInstance(context);
        onNetworkStateChanged();

    }

    private void onNetworkStateChanged() {
        if (NetworkConnectionService.checkInternetConnection()) {
            Log.d(AppConstants.LOG_TAG, "Connected");
            sendNetworkConnectionAppearedMessage();
        } else {
            Log.d(AppConstants.LOG_TAG, "Disconnected");
            sendNoNetworkConnectionMessage();

        }
    }

    private void sendNoNetworkConnectionMessage() {
        intent = new Intent(AppConstants.BROADCAST_ACTION_NO_INTERNET_CONNECTION);
        broadcastManager.sendBroadcast(intent);
    }

    private void sendNetworkConnectionAppearedMessage() {
        intent = new Intent(AppConstants.BROADCAST_ACTION_INTERNET_CONNECTION_APPEARED);
        broadcastManager.sendBroadcast(intent);
    }

}
