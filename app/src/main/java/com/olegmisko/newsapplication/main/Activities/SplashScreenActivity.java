package com.olegmisko.newsapplication.main.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.olegmisko.newsapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity {

    @BindView(R.id.applicationLoadingProgressBar) ProgressBar loadProgressBar;
    private static boolean activityStarted;
    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        if (activityStarted
                && getIntent() != null
                && (getIntent().getFlags() & Intent.FLAG_ACTIVITY_REORDER_TO_FRONT) != 0) {
            finish();
            return;
        }
        activityStarted = true;
        loadProgressBar.setProgress(progress);
        startTimer();
    }

    private void startTimer() {
        CountDownTimer mCountDownTimer = new CountDownTimer(2500, 25) {

            @Override
            public void onTick(long millisUntilFinished) {
                progress++;
                loadProgressBar.setProgress(progress);
            }

            @Override
            public void onFinish() {
                progress++;
                loadProgressBar.setProgress(progress);
                Intent startApplication = new Intent(getApplicationContext(), AuthorizationActivity.class);
                startActivity(startApplication);
                overridePendingTransition(R.anim.alpha, R.anim.alpha_out);
            }
        };
        mCountDownTimer.start();
    }
}
