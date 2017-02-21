package com.olegmisko.newsapplication.main.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.olegmisko.newsapplication.R;
import com.olegmisko.newsapplication.main.Services.DatabaseService;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText usernameField, passwordField;
    private Button registerButton;
    private TextInputLayout usernameInputLayout, passwordInputLayout;
    private RelativeLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        setTitle("Login");
        mainLayout = (RelativeLayout) findViewById(R.id.activity_login2);
        usernameField = (EditText) findViewById(R.id.usernameField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        registerButton = (Button) findViewById(R.id.submitButton);
        usernameInputLayout = (TextInputLayout) findViewById(R.id.input_layout_username);
        passwordInputLayout = (TextInputLayout) findViewById(R.id.input_layout_password);

        usernameField.addTextChangedListener(new CustomTextWatcher(usernameField));
        passwordField.addTextChangedListener(new CustomTextWatcher(passwordField));

        registerButton.setOnClickListener(this);
    }

    private void submitLogin() {
        if (!validateUsername()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }
        DatabaseService.getSharedInstance().initRealm(this);
        boolean wasSuccessfull = DatabaseService.getSharedInstance().checkCredentials(usernameField.getText().toString(), passwordField.getText().toString());
        if (wasSuccessfull) {
            Intent loadMainActivity = new Intent(this, NavigationDrawerActivity.class);
            startActivity(loadMainActivity);
            overridePendingTransition(R.anim.alpha, R.anim.alpha_out);
        } else {
            showSnackBar(wasSuccessfull, mainLayout);
        }

    }

    private boolean validateUsername() {
        if (usernameField.getText().toString().trim().isEmpty()) {
            usernameInputLayout.setError("This field is required");
            requestFocus(usernameField);
            return false;
        } else {
            usernameInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (passwordField.getText().toString().trim().isEmpty()) {
            passwordInputLayout.setError("This field is required");
            requestFocus(passwordField);
            return false;
        } else {
            passwordInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void showSnackBar(boolean successOfRegistration, View view) {
        if (successOfRegistration) {
            Snackbar success = Snackbar.make(view, "Logged in succesfully", Snackbar.LENGTH_LONG);
            success.show();
        } else {
            Snackbar unsuccess = Snackbar.make(view, "Something wen't wrong. Please, try again", Snackbar.LENGTH_LONG);
            unsuccess.show();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.submitButton:
                submitLogin();
                break;
            default:
                break;
        }
    }

    private class CustomTextWatcher implements TextWatcher {

        private View view;

        private CustomTextWatcher(View view) {
            this.view = view;
        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Do nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Meh, do nothing too
        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.usernameField:
                    validateUsername();
                    break;
                case R.id.passwordField:
                    validatePassword();
                    break;
            }
        }
    }
}
