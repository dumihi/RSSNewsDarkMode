package com.example.rssnews;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

public class NewsDetailActivity extends AppCompatActivity {
WebView myWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_detail);

        myWebview = (WebView) findViewById(R.id.webviewNews);

        Intent myIntent= getIntent();

        String url = myIntent.getStringExtra("duongDan");
        Toast.makeText(this,url, Toast.LENGTH_SHORT).show();

        myWebview.loadUrl(url);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {

                WebSettingsCompat.setForceDark(myWebview.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
            }

        }
        myWebview.setWebViewClient(new WebViewClient());


    }
}
