package com.example.tabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

public class SplashActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int secondsDelayed = 3;
        new Handler().postDelayed(new Runnable() {
                public void run() {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                }
        }, secondsDelayed * 1000);
    }
}