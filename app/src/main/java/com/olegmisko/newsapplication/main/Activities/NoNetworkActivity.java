package com.olegmisko.newsapplication.main.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.olegmisko.newsapplication.R;
import com.olegmisko.newsapplication.main.Services.NetworkConnectionService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoNetworkActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.try_again_text)
    TextView tryAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_network);
        ButterKnife.bind(this);
        tryAgain.setOnClickListener(this);
    }

    private void loadApplicationOnNetworkAppearance() {
        Intent startApplication = new Intent(getApplicationContext(), AuthorizationActivity.class);
        startActivity(startApplication);
        overridePendingTransition(R.anim.alpha, R.anim.alpha_out);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.try_again_text:
                if (NetworkConnectionService.getInstance().checkInternetConnection()) {
                    loadApplicationOnNetworkAppearance();
                }
        }
    }
}
