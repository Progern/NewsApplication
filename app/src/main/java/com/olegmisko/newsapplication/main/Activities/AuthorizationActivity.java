package com.olegmisko.newsapplication.main.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.olegmisko.newsapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuthorizationActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.loginButton) Button loginButton;
    @BindView(R.id.signup_button) Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Authorization");
        ButterKnife.bind(this);
        checkLoginState();
        loginButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                loadRegistrationOrLogin(LoginActivity.class);
                break;
            case R.id.signup_button:
                loadRegistrationOrLogin(RegistrationActivity.class);
                break;
            default:
                break;
        }
    }

    /* Loads login/sign up Activity */
    private void loadRegistrationOrLogin(Class classToLoad) {
        Intent loadRegistration = new Intent(getApplicationContext(), classToLoad);
        startActivity(loadRegistration);
        overridePendingTransition(R.anim.alpha, R.anim.alpha_out);
    }

    /* Loads the application in case that the user is logged in */
    private void loadApplication() {
        Intent loadMainActivity = new Intent(this, NavigationDrawerActivity.class);
        startActivity(loadMainActivity);
        overridePendingTransition(R.anim.alpha, R.anim.alpha_out);
    }

    /* Loads the login state of user from shared preferences
     * If the user is already logged in, current activity is skipped */
    private void checkLoginState() {
        SharedPreferences preferences = getSharedPreferences("MainPref", MODE_PRIVATE);
        if (preferences.getBoolean("Logged_in", false)) {
            loadApplication();
        }
    }
}
