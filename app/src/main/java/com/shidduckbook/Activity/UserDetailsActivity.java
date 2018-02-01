package com.shidduckbook.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.shidduckbook.R;
import com.shidduckbook.RetrofitClasses.ApiClient;
import com.shidduckbook.RetrofitClasses.MyApiEndpointInterface;
import com.shidduckbook.Util.AppConstant;
import com.shidduckbook.Util.AppPreferences;
import com.shidduckbook.Util.ProgressHUD;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailsActivity extends AppCompatActivity {

    private static final String TAG = "UserDetailsActivity";
    String name, age, city, profilepic, user_id, favourite_status, id;
    CircleImageView userImageView;
    TextView nameTextView, cityTextView, ageTextView;
    Button interested_btn, requestPro_btn;
    CheckBox requestCheckBox;
    ImageView helpImageView;
    private LinearLayout interestLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        getSupportActionBar().setHomeButtonEnabled(true);      // enable the button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // the left caret
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        interestLinearLayout = (LinearLayout) findViewById(R.id.interestLinearLayout);

        if (getIntent().getAction().equalsIgnoreCase("MainAdapter")) {
            name = getIntent().getExtras().getString("name");
            age = getIntent().getExtras().getString("age");
            city = getIntent().getExtras().getString("city");
            profilepic = getIntent().getExtras().getString("profilepic");
            user_id = getIntent().getExtras().getString("user_id");
            id = getIntent().getExtras().getString("id");
            favourite_status = getIntent().getExtras().getString("favourite_status");

            Log.v(TAG, "User details :- " + name + " Age :-" + age + " City :-" + city + " Profile Pic" + profilepic + " User id :-" + user_id);

        } else if (getIntent().getAction().equalsIgnoreCase("FavoriteAdapter")) {
            name = getIntent().getExtras().getString("name");
            age = getIntent().getExtras().getString("age");
            city = getIntent().getExtras().getString("city");
            profilepic = getIntent().getExtras().getString("profilepic");
            user_id = getIntent().getExtras().getString("user_id");
            id = getIntent().getExtras().getString("id");
            favourite_status = getIntent().getExtras().getString("favourite_status");

            Log.v(TAG, "User details :- " + name + " Age :-" + age + " City :-" + city + " Profile Pic" + profilepic + " User id :-" + user_id);

        } else if (getIntent().getAction().equalsIgnoreCase("InterestAdapter")) {
            name = getIntent().getExtras().getString("name");
            age = getIntent().getExtras().getString("age");
            city = getIntent().getExtras().getString("city");
            profilepic = getIntent().getExtras().getString("profilepic");
            user_id = getIntent().getExtras().getString("user_id");
            id = getIntent().getExtras().getString("id");
            favourite_status = getIntent().getExtras().getString("favourite_status");

            interestLinearLayout.setVisibility(View.GONE);
            Log.v(TAG, "User details :- " + name + " Age :-" + age + " City :-" + city + " Profile Pic" + profilepic + " User id :-" + user_id);
        }

        initViews();
        setListener();
    }

    private void initViews() {

        nameTextView = (TextView) findViewById(R.id.nameTextView);
        nameTextView.setText(name);
        ageTextView = (TextView) findViewById(R.id.ageTextView);
        ageTextView.setText(age);
        cityTextView = (TextView) findViewById(R.id.cityTextView);
        if (city.equalsIgnoreCase("0")) {
            cityTextView.setText("");
        } else {
            cityTextView.setText(city);
        }
        userImageView = (CircleImageView) findViewById(R.id.userImageView);
        if (!profilepic.equalsIgnoreCase("")) {

            Picasso.with(UserDetailsActivity.this).load(profilepic)
                    .placeholder(R.drawable.pro_icon)
                    .error(R.drawable.pro_icon)
                    .resize(200, 200)
                    .into(userImageView);
        } else {
            userImageView.setImageDrawable(getResources().getDrawable(R.drawable.pro_icon));
        }
        interested_btn = (Button) findViewById(R.id.interested_btn);

        requestPro_btn = (Button) findViewById(R.id.requestPro_btn);


        requestCheckBox = (CheckBox) findViewById(R.id.requestCheckBox);
        helpImageView = (ImageView) findViewById(R.id.helpImageView);

    }

    private void setListener() {

        interested_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(AppConstant.KEY_METHOD, AppConstant.METHOD.M_ADD_INTEREST);
                jsonObject.addProperty(AppConstant.KEY_USER_ID, AppPreferences.getUserId(UserDetailsActivity.this));
                jsonObject.addProperty(AppConstant.KEY_MATCH_USER_ID, id);

                Log.v(TAG, "Json Request My Interest:-" + jsonObject.toString());

                myInterestTask(jsonObject);

            }
        });

        requestPro_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(AppConstant.KEY_METHOD, AppConstant.METHOD.M_REQUEST_PROFILE);
                jsonObject.addProperty(AppConstant.KEY_USER_ID, AppPreferences.getUserId(UserDetailsActivity.this));
                jsonObject.addProperty(AppConstant.KEY_MATCH_USER_ID, user_id);

                Log.v(TAG, "Json Request :-" + jsonObject.toString());

//                requestProfileTask(jsonObject);
            }
        });

        requestCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.v(TAG, "check box Checked");
                } else {
                    Log.v(TAG, "check box Checked");
                }
            }
        });

        helpImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                helpDialog();
            }
        });

    }

    public void helpDialog() {

        final Dialog markerDialog = new Dialog(UserDetailsActivity.this);
        markerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = markerDialog.getWindow();
//        window.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corner_dialog));
        window.setGravity(Gravity.CENTER);

        markerDialog.setContentView(R.layout.logout_popup);
        final Button btn_done = (Button) markerDialog.findViewById(R.id.done_btn);
        final Button btn_cancel = (Button) markerDialog.findViewById(R.id.cancel_btn);

        final TextView text2 = (TextView) markerDialog.findViewById(R.id.texttwo);
        text2.setText(getResources().getString(R.string.popup_string));
        //String[] ITEMS = getResources().getStringArray(R.array.country);

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                btn_cancel.setBackgroundResource(R.drawable.buttonshape);
//                btn_done.setBackgroundResource(R.drawable.buttonshape_two);

                markerDialog.dismiss();


            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                btn_cancel.setBackgroundResource(R.drawable.buttonshape_two);
//                btn_done.setBackgroundResource(R.drawable.buttonshape);
                markerDialog.dismiss();
            }
        });

        markerDialog.setCanceledOnTouchOutside(false);
        markerDialog.show();
    }

    private void myInterestTask(JsonObject jsonObject) {

        final ProgressHUD progressHD = ProgressHUD.show(UserDetailsActivity.this, "Please wait...", true, false, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // TODO Auto-generated method stub
            }
        });

        MyApiEndpointInterface apiService =
                ApiClient.getClient().create(MyApiEndpointInterface.class);
        Call<JsonObject> call = apiService.my_matched_API(jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.e(TAG, "Json Response I am Interested in:- " + response.body().toString());
                String s = response.body().toString();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("status").equalsIgnoreCase("200")) {

                        Snackbar.make(findViewById(android.R.id.content), "Request sent to " + name + " !", Snackbar.LENGTH_LONG).show();


                    } else if (jsonObject.get("status").equals("400")) {

                        String msg = jsonObject.getString("message");
                        Snackbar.make(findViewById(android.R.id.content), "Already sent Request !", Snackbar.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progressHD.dismiss();
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "on Failure error :-" + Log.getStackTraceString(t));
                progressHD.dismiss();
            }
        });


    }

    private void requestProfileTask(JsonObject jsonObject) {

        final ProgressHUD progressHD = ProgressHUD.show(UserDetailsActivity.this, "Please wait...", true, false, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // TODO Auto-generated method stub
            }
        });

        MyApiEndpointInterface apiService =
                ApiClient.getClient().create(MyApiEndpointInterface.class);
        Call<JsonObject> call = apiService.my_matched_API(jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.e(TAG, "Json Response Request Resume :- " + response.body().toString());
                String s = response.body().toString();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("status").equalsIgnoreCase("200")) {

                        Snackbar.make(findViewById(android.R.id.content), "Data Added Successfully !!!", Snackbar.LENGTH_LONG).show();

                        Intent intent = new Intent(UserDetailsActivity.this, MainActivity.class);
                        intent.setAction("");
                        startActivity(intent);
                        finish();

                    } else if (jsonObject.get("status").equals("400")) {

                        String msg = jsonObject.getString("message");
                        Snackbar.make(findViewById(android.R.id.content), "Already added this user !!!", Snackbar.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progressHD.dismiss();
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "on Failure error :-" + Log.getStackTraceString(t));
                progressHD.dismiss();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
