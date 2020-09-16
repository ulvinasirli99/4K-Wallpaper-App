package com.neodevloper.hdwallpaper2021.web.follow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.neodevloper.hdwallpaper2021.R;

public class help extends AppCompatActivity {

    protected WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        webView = findViewById(R.id.helpWeb);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://help.unsplash.com/en/?utm_source=unsplash&utm_medium=referral");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);


    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
        }
    }

}
