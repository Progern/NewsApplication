package com.olegmisko.newsapplication.main.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.olegmisko.newsapplication.R;
import com.olegmisko.newsapplication.main.Services.DatabaseService;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ivan on 8/21/2017.
 */

public abstract class OnboardingActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.usernameField)
    protected EditText usernameField;
    @BindView(R.id.passwordField)
    protected EditText passwordField;
    @BindView(R.id.input_layout_username)
    protected TextInputLayout usernameInputLayout;
    @BindView(R.id.input_layout_password)
    protected TextInputLayout passwordInputLayout;
    @BindView(R.id.activity_registration)
    protected RelativeLayout mainLayout;
    @BindView(R.id.submitButton)
    protected Button submitButton;
    @BindView(R.id.onboarding_icon)
    protected ImageView onboardingIcon;

    protected SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        DatabaseService.initRealm(this);
        preferences = getSharedPreferences("MainPref", MODE_PRIVATE);
        usernameField.addTextChangedListener(new CustomTextWatcher(usernameField));
        passwordField.addTextChangedListener(new CustomTextWatcher(passwordField));
        submitButton.setOnClickListener(this);
    }

    protected abstract void submitAction();

    protected void registerOrLogin(String snackBarMessage, boolean isRegister) {
        if (!validateUsername()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        if (isRegister) {
            DatabaseService.writeToDataBase(usernameField.getText().toString(), passwordField.getText().toString());
        }

        if (checkCredentials(usernameField.getText().toString(), passwordField.getText().toString())) {
            loadApplication();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("Logged_in", true);
            editor.apply();
        } else {
            showSnackBar(snackBarMessage, false, mainLayout);
        }
    }

    protected void setOnboardingIcon(@DrawableRes int iconRes) {
        onboardingIcon.setBackgroundResource(iconRes);
    }

    protected void setSubmitButtonText(@StringRes int stringRes) {
        submitButton.setText(stringRes);
    }

    /* Checks the typed in credentials with user database */
    private boolean checkCredentials(String username, String password) {
        return DatabaseService.checkCredentials(username, password);
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

    private void showSnackBar(String snackBarMessage, boolean successOfRegistration, View view) {
        if (successOfRegistration) {
            Snackbar success = Snackbar.make(view, snackBarMessage, Snackbar.LENGTH_LONG);
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
                submitAction();
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
