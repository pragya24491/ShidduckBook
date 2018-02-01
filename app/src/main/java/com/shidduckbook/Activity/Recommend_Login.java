package com.shidduckbook.Activity;

import android.app.ActionBar;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.shidduckbook.R;
import com.shidduckbook.Util.AppConstant;
import com.shidduckbook.Util.Function;

public class Recommend_Login extends AppCompatActivity {
    Button loginButton;
    TextView registertextView;

    LinearLayout addViewLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        initViews();
        setListener();
    }

    private void initViews() {

        loginButton = (Button) findViewById(R.id.login_btn_recommend);
        registertextView = (TextView) findViewById(R.id.register_as_recommend);


      /*  addViewLayout = (LinearLayout) findViewById(R.id.addViewLayout);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,10,10,10);

        // add text view
        final TextView tv = new TextView(this);
        tv.setText("Text 1");
        tv.setTextColor(getResources().getColor(R.color.color_white));
        tv.setPadding(50, 20, 50, 20);
        tv.setGravity(Gravity.CENTER);
       // tv.setBackground(getResources().getDrawable(R.drawable.button_border_blue_oval));
        tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_close_white_24dp, 0);
        tv.setCompoundDrawablePadding(10);
        tv.setLayoutParams(params);

        tv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (tv.getRight() - tv.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {    // for adding click listener for drawable right
//                        if(event.getRawX() >= (tv.getRight() - tv.getTotalPaddingRight())) {                            // for adding click listener for drawable right
                       /*//*   both the above line works for drawable padding   *//*

                        // your action here
                        Toast.makeText(getApplicationContext(), "Cross Clicked !!!", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                return true;
            }
        });

        addViewLayout.addView(tv);

        addViewLayout.setOrientation(LinearLayout.HORIZONTAL);
        addViewLayout.setLayoutParams(params);
        addViewLayout.setGravity(Gravity.CENTER);
*/

    }
    private void setListener() {

        registertextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Recommend_Login.this, Recommend_Register.class);
                startActivity(intent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Recommend_Login.this, Recommend_Home_Activity.class);
                startActivity(intent);
            }
        });

    }

}


