package com.olegmisko.newsapplication.main.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.olegmisko.newsapplication.R;
import com.olegmisko.newsapplication.main.Services.CustomWebViewClient;

public class WebViewActivity extends Activity {

    private WebView mainWebView;
    private String urlToLoad;
    private CustomWebViewClient mWebViewClient;
    private ProgressBar loadPageProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mainWebView = (WebView) findViewById(R.id.mainWebView);
        loadPageProgress = (ProgressBar) findViewById(R.id.loadPageProgress);
        mWebViewClient = new CustomWebViewClient();
        mainWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                Log.d("MY_LOG", "Progress " + progress);
                loadPageProgress.setProgress(progress);
            }
        });
        mainWebView.getSettings().setJavaScriptEnabled(true);
        mainWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mainWebView.setWebViewClient(mWebViewClient);
        hideStatusBar();
        Intent intent = getIntent();
        urlToLoad = intent.getStringExtra("URL");
        if (urlToLoad != null) {
            mainWebView.loadUrl(urlToLoad);
        }
    }

    private void hideStatusBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }


}
