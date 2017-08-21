package com.olegmisko.newsapplication.main.Activities;

import android.os.Bundle;

import com.olegmisko.newsapplication.R;

public class LoginActivity extends OnboardingActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Login");
        setOnboardingIcon(R.drawable.login_screen);
        setSubmitButtonText(R.string.login);
    }

    @Override
    protected void submitAction() {
        registerOrLogin("Logged in successful", false);
    }

}
