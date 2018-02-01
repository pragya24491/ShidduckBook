package com.shidduckbook.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.shidduckbook.R;
import com.shidduckbook.Util.AppPreferences;
import com.shidduckbook.appintro.AppIntroActivity;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_splash);
        startAction();
    }

    public void startAction() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

              /*  Intent intent = new Intent(SplashActivity.this, vnsdnvsj.class);
                startActivity(intent);
                finish();*/

                if (AppPreferences.getUserId(SplashActivity.this).equalsIgnoreCase("")) {

                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    intent.setAction("");
                    startActivity(intent);
                    finish();
                }

            }
        }, SPLASH_TIME);
    }
}
