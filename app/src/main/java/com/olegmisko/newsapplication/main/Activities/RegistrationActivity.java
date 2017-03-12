package com.olegmisko.newsapplication.main.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.olegmisko.newsapplication.R;
import com.olegmisko.newsapplication.main.Services.DatabaseService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.usernameField)
    EditText usernameField;
    @BindView(R.id.passwordField)
    EditText passwordField;
    @BindView(R.id.input_layout_username)
    TextInputLayout usernameInputLayout;
    @BindView(R.id.input_layout_password)
    TextInputLayout passwordInputLayout;
    @BindView(R.id.activity_registration)
    RelativeLayout mainLayout;
    @BindView(R.id.submitButton)
    Button registerButton;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setTitle("Registration");
        ButterKnife.bind(this);
        DatabaseService.getSharedInstance().initRealm(this);
        preferences = getSharedPreferences("MainPref", MODE_PRIVATE);
        usernameField.addTextChangedListener(new CustomTextWatcher(usernameField));
        passwordField.addTextChangedListener(new CustomTextWatcher(passwordField));

        registerButton.setOnClickListener(this);
    }

    private void submitRegistration() {
        if (!validateUsername()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        if (performRegistration(usernameField.getText().toString(), passwordField.getText().toString())) {
            loadApplication();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("Logged_in", true);
            editor.apply();
        } else {
            showSnackBar(false, mainLayout);
        }
    }

    /* Write into database and check if it was succesfull */
    private boolean performRegistration(String username, String password) {
        DatabaseService.getSharedInstance().writeToDataBase(username, password);
        return DatabaseService.getSharedInstance().checkCredentials(username, password);
    }

    private void loadApplication() {
        Intent loadMainActivity = new Intent(this, NavigationDrawerActivity.class);
        startActivity(loadMainActivity);
        overridePendingTransition(R.anim.alpha, R.anim.alpha_out);
    }

    /* Watches the username field to be filled */
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

    /* Watches the password field to be filled */
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
            Snackbar success = Snackbar.make(view, "Registered successfully", Snackbar.LENGTH_LONG);
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
                submitRegistration();
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
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        /* If the fields were filled and cleaned there will be
         * a text, saying "These field are required*/
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
