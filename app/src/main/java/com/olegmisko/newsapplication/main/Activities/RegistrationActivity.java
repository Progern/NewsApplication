package com.olegmisko.newsapplication.main.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.olegmisko.newsapplication.R;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setTitle("Register");
    }
}
