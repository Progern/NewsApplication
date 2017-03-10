package com.olegmisko.newsapplication.main.Services;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.olegmisko.newsapplication.R;
import com.olegmisko.newsapplication.main.Activities.NavigationDrawerActivity;
import com.olegmisko.newsapplication.main.Config.AppConstants;
import com.olegmisko.newsapplication.main.Models.News;
import com.olegmisko.newsapplication.main.Models.NewsList;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckNewsService extends Service {

    public static final String LOG_TAG = "MY_LOG";
    private static final long REPEAT_TIME = 1000 * 14400;
    private Timer timer;
    private NotificationManager notificationManager;
    private Target target;

    public CheckNewsService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer ();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Log.d(LOG_TAG, "onCreate service");
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TimerTask hourlyTask = new TimerTask () {
            @Override
            public void run () {
                getNewsAndSendNotification();
            }
        };
        timer.schedule(hourlyTask, 0L, REPEAT_TIME);
        Log.d(LOG_TAG, "onStartCommand service");
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void getNewsAndSendNotification() {
        getNewsListFromSource(getRandomSource(getRandomNumber(5)));

    }

    /* Performs HTTP-requests to the endpoint-server
     * and fetches data from source name */
    private void getNewsListFromSource(final String source) {
        Call<NewsList> newsCall = NetworkService.API.GETLatesNewsList(source, AppConstants.TOP, AppConstants.API_KEY);
        newsCall.enqueue(new Callback<NewsList>() {
            @Override
            public void onResponse(Call<NewsList> call, Response<NewsList> response) {
                if (response.isSuccessful()) {
                    News currentNews = response.body().getNewsList().get(getRandomNumber(9));
                    createAndSendNotification(currentNews.getTitle(), currentNews.getShortDescription(), currentNews.getImageUrl());
                    Log.d("MY_LOG", "Response is successful");
                } else {
                    Log.d("MY_LOG", "Response is unsuccessful");
                }
            }

            @Override
            public void onFailure(Call<NewsList> call, Throwable t) {
                Log.d("MY_LOG", "Response failed");
            }
        });
    }

    private int getRandomNumber(int max) {
        return (int) Math.floor(Math.random() * max);
    }

    private String getRandomSource(int number) {
        switch (number) {
            case 0:
                return AppConstants.CNN;
            case 1:
                return AppConstants.BBC_SOURCE;
            case 2:
                return AppConstants.T3N;
            case 3:
                return AppConstants.GOOGLE;
            case 4:
                return AppConstants.IGN;

        }
        return null;
    }

    private void createAndSendNotification(String title, String body, String image) {
        /* Creating a basic notification, this will be shown in status bar*/
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.news_icon)
                        .setContentTitle(title)
                       //.setLargeIcon(getBitmapFromURL(image))
                        .setContentText(body);
        Notification notification = mBuilder.build();

        Intent intent = new Intent(this, NavigationDrawerActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        /* Notification will disappear after click */
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.contentIntent = pendingIntent;

        /* Sending notification */
        notificationManager.notify(1, notification);
    }

    private Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            return null;
        }
    }

}
