package com.olegmisko.newsapplication.main.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.olegmisko.newsapplication.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button loginButton, signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("");
        loginButton = (Button) findViewById(R.id.loginButton);
        signUpButton = (Button) findViewById(R.id.signup_button);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                // TODO: Handle Login action
                break;
            case R.id.signup_button:
                // TODO: Handle Sign Up action
                break;
            default:
                break;
        }
    }
}
