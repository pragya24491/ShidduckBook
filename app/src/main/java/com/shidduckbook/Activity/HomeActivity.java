package com.shidduckbook.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shidduckbook.R;
import com.shidduckbook.Util.AppPreferences;

public class HomeActivity extends Activity {

    TextView registerTextView, loginTextView;
    Button boysListButton, girlsListButton, recommendButton, tourButton;
    public static boolean homePage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();

        if(! AppPreferences.getUserId(HomeActivity.this).equalsIgnoreCase("")){
            registerTextView.setVisibility(View.GONE);
            loginTextView.setVisibility(View.GONE);
        }

        setListener();
    }

    private void initViews() {
        registerTextView = (TextView) findViewById(R.id.register);
        loginTextView = (TextView) findViewById(R.id.login);
        boysListButton = (Button) findViewById(R.id.boyslist_btn);
        girlsListButton = (Button) findViewById(R.id.girlslist_btn);
        recommendButton = (Button) findViewById(R.id.recommend_btn);
        tourButton = (Button) findViewById(R.id.tour_btn);
    }

    private void setListener() {

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK/*|Intent.FLAG_ACTIVITY_CLEAR_TASK*/);
                startActivity(intent);
            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(/*Intent.FLAG_ACTIVITY_NEW_TASK|*/Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
//                finish();
            }
        });

        boysListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                homePage = true;
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                intent.setAction("HomeActivity");
                intent.putExtra("gender","male");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        girlsListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                homePage = true;
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                intent.setAction("HomeActivity");
                intent.putExtra("gender","female");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

      recommendButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              homePage = true;
              Intent intent = new Intent(HomeActivity.this, Recommend_Login.class);
              intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              startActivity(intent);

          }
      });

    }

}
