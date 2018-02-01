package com.shidduckbook.Activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.google.gson.JsonObject;
import com.shidduckbook.R;
import com.shidduckbook.RetrofitClasses.ApiClient;
import com.shidduckbook.RetrofitClasses.MyApiEndpointInterface;
import com.shidduckbook.Util.AppConstant;
import com.shidduckbook.Util.AppPreferences;
import com.shidduckbook.Util.ProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_New_User extends AppCompatActivity {

    private static final String TAG = "Add_New_User";
    EditText input_fname, input_lname, input_email, input_fathername, input_age, input_city;
    RadioGroup radioGroup;
    String radioButton = "male";
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__new__user);
        getSupportActionBar().setHomeButtonEnabled(true);      // enable the button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // the left caret
        getSupportActionBar().setDisplayShowHomeEnabled(true); // the icon

        initViews();
        setListener();
    }

    private void initViews() {

        submitButton = (Button) findViewById(R.id.submit_Btn);

        input_fname = (EditText) findViewById(R.id.input_fname);
        input_lname = (EditText) findViewById(R.id.input_lname);
        input_email = (EditText) findViewById(R.id.input_email);
        input_fathername = (EditText) findViewById(R.id.input_fathername);
        input_age = (EditText) findViewById(R.id.input_age);
        input_city = (EditText) findViewById(R.id.input_city);
        radioGroup = (RadioGroup) findViewById(R.id.radioGrp);

    }

    private void setListener() {

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.femaleButton){
                    radioButton = "female";
                    Log.v(TAG, "Radio Button Selected :- " + radioButton);
                } else if(checkedId == R.id.maleButton) {
                    radioButton = "male";
                    Log.v(TAG, "Radio Button Selected else :- " + radioButton);
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(input_fname.getText().toString())){
                    input_fname.setError("This field is required");
                } else if(TextUtils.isEmpty(input_lname.getText().toString())){
                    input_lname.setError("This field is required");
                } else if(TextUtils.isEmpty(input_email.getText().toString())){
                    input_email.setError("This field is required");
                }else if(TextUtils.isEmpty(input_fathername.getText().toString())){
                    input_fathername.setError("This field is required");
                } else if(TextUtils.isEmpty(input_age.getText().toString())){
                    input_age.setError("This field is required");
                } else if(TextUtils.isEmpty(input_city.getText().toString())){
                    input_city.setError("This field is required");
                } else {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty(AppConstant.KEY_METHOD, AppConstant.METHOD.M_ADD_OTHERUSER);
                    jsonObject.addProperty(AppConstant.KEY_USER_ID, AppPreferences.getUserId(Add_New_User.this));

                    jsonObject.addProperty(AppConstant.KEY_FNAME, input_fname.getText().toString().trim());
                    jsonObject.addProperty(AppConstant.KEY_LNAME, input_lname.getText().toString().trim());
                    jsonObject.addProperty(AppConstant.KEY_EMAIL, input_email.getText().toString().trim());
                    jsonObject.addProperty(AppConstant.KEY_GENDER, radioButton);
                    jsonObject.addProperty(AppConstant.KEY_FATHERNAME, input_fathername.getText().toString().trim());
                    jsonObject.addProperty(AppConstant.KEY_CITY, input_city.getText().toString().trim());
                    jsonObject.addProperty(AppConstant.KEY_AGE, input_age.getText().toString().trim());

                    Log.v(TAG, "Json Request Add user :- " + jsonObject.toString());
                    addUserTask(jsonObject);
                }
            }
        });
    }

    private void addUserTask(JsonObject jsonObject) {

        final ProgressHUD progressHD = ProgressHUD.show(Add_New_User.this, "Please wait...", true, false, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // TODO Auto-generated method stub
            }
        });

        MyApiEndpointInterface apiService =
                ApiClient.getClient().create(MyApiEndpointInterface.class);
        Call<JsonObject> call = apiService.general_api(jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.e(TAG, "Json Response Register:- " + response.body().toString());
                String s = response.body().toString();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("status").equalsIgnoreCase("200")) {

                        Snackbar.make(findViewById(android.R.id.content),"Data Added Successfully !" ,Snackbar.LENGTH_LONG).show();

                        Intent intent = new Intent(Add_New_User.this, MainActivity.class);
                        intent.setAction("");
                        startActivity(intent);
                        finish();

                    } else if(jsonObject.get("status").equals("400")) {

                        String msg = jsonObject.getString("message");
                        Snackbar.make(findViewById(android.R.id.content),"User Already Exixts ! Please use another Email id." ,Snackbar.LENGTH_LONG).show();
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
