package com.olegmisko.newsapplication.main.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.olegmisko.newsapplication.R;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MY_LOG";
    private ProgressBar loadProgressBar;
    private CountDownTimer mCountDownTimer;
    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        loadProgressBar = (ProgressBar) findViewById(R.id.applicationLoadingProgressBar);
        loadProgressBar.setProgress(progress);
        startTimer();
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(5000, 50) {

            @Override
            public void onTick(long millisUntilFinished) {
                progress++;
                loadProgressBar.setProgress(progress);
            }

            @Override
            public void onFinish() {
                progress++;
                loadProgressBar.setProgress(progress);
                Intent startApplication = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
                startActivity(startApplication);
            }
        };
        mCountDownTimer.start();
    }
}
