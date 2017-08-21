package com.olegmisko.newsapplication.main.Activities;

import android.os.Bundle;

import com.olegmisko.newsapplication.R;

public class RegistrationActivity extends OnboardingActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Registration");
        setOnboardingIcon(R.drawable.registration_icon);
        setSubmitButtonText(R.string.register);
    }

    @Override
    protected void submitAction() {
        registerOrLogin("Registered successfully", true);
    }

}
