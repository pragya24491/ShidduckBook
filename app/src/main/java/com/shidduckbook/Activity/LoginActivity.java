package com.shidduckbook.Activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.shidduckbook.Model.HomePageModel;
import com.shidduckbook.R;
import com.shidduckbook.RetrofitClasses.ApiClient;
import com.shidduckbook.RetrofitClasses.MyApiEndpointInterface;
import com.shidduckbook.Util.AppConstant;
import com.shidduckbook.Util.AppPreferences;
import com.shidduckbook.Util.Function;
import com.shidduckbook.Util.ProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    Button loginButton;
    TextView registertextView, forgotPasswordtextview;
    EditText emailEdittext, passwordEditText;
    HomePageModel homePageModel;
    ArrayList<HomePageModel> arrayListPersonal ;
    ArrayList<HomePageModel> arrayListPartner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        setListener();
    }

    private void initViews() {

        loginButton = (Button) findViewById(R.id.login_btn);
        registertextView = (TextView) findViewById(R.id.register);
        forgotPasswordtextview = (TextView) findViewById(R.id.forgotpassword);
        emailEdittext = (EditText) findViewById(R.id.input_email);
        passwordEditText = (EditText) findViewById(R.id.input_password);

    }

    private void setListener() {

        registertextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        forgotPasswordtextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//                startActivity(intent);

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(emailEdittext.getText().toString())) {
                    emailEdittext.setError("This field is required");
                } else if (TextUtils.isEmpty(passwordEditText.getText().toString())) {
                    passwordEditText.setError("This field is required");
                } else {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty(AppConstant.KEY_METHOD, AppConstant.METHOD.M_LOGIN);
                    jsonObject.addProperty(AppConstant.KEY_EMAILID, emailEdittext.getText().toString().trim());
                    jsonObject.addProperty(AppConstant.KEY_PASSWORD, passwordEditText.getText().toString().trim());

                    Log.v(TAG, "Json Request Login :- " + jsonObject.toString());

                    if (Function.isConnectingToInternet(LoginActivity.this)) {
                        loginTask(jsonObject);
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), "Internet not connected !", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void loginTask(JsonObject jsonObject) {

        final ProgressHUD progressHD = ProgressHUD.show(LoginActivity.this, "Please wait...", true, false, new DialogInterface.OnCancelListener() {
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

                Log.v(TAG, "Json Response Login :- " + response.body().toString());
                String s = response.body().toString();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    homePageModel = new HomePageModel();
                    if (jsonObject.getString("status").equalsIgnoreCase("200")) {

                        JSONArray jsonArray = jsonObject.getJSONArray("result");

                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                        AppPreferences.setUserId(LoginActivity.this, jsonObject1.getString("user_id"));
                        AppPreferences.setFirstName(LoginActivity.this, jsonObject1.getString("first_name"));
                        AppPreferences.setLastName(LoginActivity.this, jsonObject1.getString("last_name"));
                        AppPreferences.setEmailId(LoginActivity.this, jsonObject1.getString("email_id"));
                        AppPreferences.setFatherName(LoginActivity.this, jsonObject1.getString("father_name"));
                        AppPreferences.setMotherName(LoginActivity.this, jsonObject1.getString("mother_name"));
                        AppPreferences.setGender(LoginActivity.this, jsonObject1.getString("gender"));
                        AppPreferences.setUserImage(LoginActivity.this, jsonObject1.getString("profile_pic"));

                        AppPreferences.setVerifiedUser(LoginActivity.this, "1");


                        /*JSONArray resultPersPrefArray = jsonObject.getJSONArray("resultPersPref");

                        for (int i = 0; i < resultPersPrefArray.length(); i++) {

                            arrayListPersonal = new ArrayList<HomePageModel>();

                            JSONObject json = jsonArray.getJSONObject(i);

                            String id = json.getString("id");
                            String preference_number = json.getString("preference_number");
                            String traits_name = json.getString("traits_name");

                            homePageModel.setId(id);
                            homePageModel.setName(traits_name);
                            homePageModel.setPref_number(preference_number);
                            arrayListPersonal.add(homePageModel);
                        }

                        JSONArray resultPartPrefArray = jsonObject.getJSONArray("resultPartPref");

                        for (int i = 0; i < resultPartPrefArray.length(); i++) {

                            arrayListPartner = new ArrayList<HomePageModel>();

                            JSONObject json = jsonArray.getJSONObject(i);

                            String id = json.getString("id");
                            String preference_number = json.getString("preference_number");
                            String traits_name = json.getString("traits_name");

                            homePageModel.setId(id);
                            homePageModel.setName(traits_name);
                            homePageModel.setPref_number(preference_number);
                            arrayListPartner.add(homePageModel);
                        }

                        for (HomePageModel homePageModel : arrayListPartner){

                            Log.v(TAG, "Partner pref list :-" + homePageModel.getName() + " And Pref numbner :- "+ homePageModel.getPref_number());

                        }

                        for (HomePageModel homePageModel : arrayListPersonal) {

                            Log.v(TAG, "Personal pref list :-" + homePageModel.getName() + " And Pref numbner :- " + homePageModel.getPref_number());

                        }
*/

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setAction("");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                    } else if (jsonObject.get("status").equals("400")) {

                        String msg = jsonObject.getString("message");
                        Snackbar.make(findViewById(android.R.id.content), "Wrong Email or Password !", Snackbar.LENGTH_LONG).show();
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
    public void onBackPressed() {
        super.onBackPressed();
    }
}
