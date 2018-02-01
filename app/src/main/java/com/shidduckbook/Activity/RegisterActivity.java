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

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    Button regiterButton;
    EditText input_fname, input_lname, input_email, input_fathername, input_mothersname, input_password;
    RadioGroup radioGroup;
    String radioButton = "male";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        setListener();
    }

    private void initViews() {

        regiterButton = (Button) findViewById(R.id.register_btn);

        input_fname = (EditText) findViewById(R.id.input_fname);
        input_lname = (EditText) findViewById(R.id.input_lname);
        input_email = (EditText) findViewById(R.id.input_email);
        input_fathername = (EditText) findViewById(R.id.input_fathername);
        input_mothersname = (EditText) findViewById(R.id.input_mothersname);
        input_password = (EditText) findViewById(R.id.input_password);
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

        regiterButton.setOnClickListener(new View.OnClickListener() {
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
                } else if(TextUtils.isEmpty(input_mothersname.getText().toString())){
                    input_mothersname.setError("This field is required");
                } else if(TextUtils.isEmpty(input_password.getText().toString())){
                    input_password.setError("This field is required");
                } else {

                    Intent intent = new Intent(RegisterActivity.this, VerificationActivity.class);
                    intent.setAction("Register");
                    intent.putExtra(AppConstant.KEY_FNAME, input_fname.getText().toString().trim());
                    intent.putExtra(AppConstant.KEY_LNAME, input_lname.getText().toString().trim());
                    intent.putExtra(AppConstant.KEY_EMAILID, input_email.getText().toString().trim());
                    intent.putExtra(AppConstant.KEY_GENDER, radioButton);
                    intent.putExtra(AppConstant.KEY_FATHERNAME, input_fathername.getText().toString().trim());
                    intent.putExtra(AppConstant.KEY_MOTHERSNAME, input_mothersname.getText().toString().trim());
                    intent.putExtra(AppConstant.KEY_PASSWORD, input_password.getText().toString().trim());
                    startActivity(intent);
                    finish();
                }

            }
        });

    }

    /*private void registerationTask(JsonObject jsonObject) {

        final ProgressHUD progressHD = ProgressHUD.show(RegisterActivity.this, "Please wait...", true, false, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // TODO Auto-generated method stub
            }
        });

        MyApiEndpointInterface apiService =
                ApiClient.getClient().create(MyApiEndpointInterface.class);
        Call<JsonObject> call = apiService.register_logs_api(jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.e(TAG, "Json Response Register:- " + response.body().toString());
                String s = response.body().toString();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("status").equalsIgnoreCase("200")) {

                        Snackbar.make(findViewById(android.R.id.content),"Registered Successfully !" ,Snackbar.LENGTH_LONG).show();

                        Intent intent = new Intent(RegisterActivity.this, VerificationActivity.class);
                        startActivity(intent);
                        finish();

                    } else if(jsonObject.get("status").equals("400")) {

                        String msg = jsonObject.getString("message");
                        Snackbar.make(findViewById(android.R.id.content),"Email Id already exists !" ,Snackbar.LENGTH_LONG).show();
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
    }*/
}
