package com.neodevloper.hdwallpaper2021.web.follow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.neodevloper.hdwallpaper2021.R;

public class facebook extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);


        webView = findViewById(R.id.facebookL);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.facebook.com/unsplash/?utm_source=unsplash&utm_medium=referral");
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
