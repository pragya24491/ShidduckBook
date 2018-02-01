package com.shidduckbook.Activity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.shidduckbook.R;
import com.shidduckbook.RetrofitClasses.ApiClient;
import com.shidduckbook.RetrofitClasses.MyApiEndpointInterface;
import com.shidduckbook.Util.AppConstant;
import com.shidduckbook.Util.ProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationActivity extends AppCompatActivity {

    private static final String TAG = "VerificationActivity";
    CircleImageView userImageView;
    Button uploadImageButton;
    final int CAMERA_CAPTURE = 1;
    final int PIC_CROP = 2;
    Bitmap thePic;
    private Uri picUri;
    private String Encoded_userimage = "";
    private String input_fname, input_lname, input_email, input_fathername, input_mothersname, input_password, radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);      // enable the button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // the left caret
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(getIntent().getAction().equalsIgnoreCase("Register")) {

            input_fname = getIntent().getExtras().getString(AppConstant.KEY_FNAME);
            input_lname = getIntent().getExtras().getString(AppConstant.KEY_LNAME);
            input_email = getIntent().getExtras().getString(AppConstant.KEY_EMAILID);
            radioButton = getIntent().getExtras().getString(AppConstant.KEY_GENDER);
            input_fathername = getIntent().getExtras().getString(AppConstant.KEY_FATHERNAME);
            input_mothersname = getIntent().getExtras().getString(AppConstant.KEY_MOTHERSNAME);
            input_password = getIntent().getExtras().getString(AppConstant.KEY_PASSWORD);
        }

        initViews();
    }

    private void initViews() {

        userImageView = (CircleImageView) findViewById(R.id.userImageView);
        uploadImageButton = (Button) findViewById(R.id.uploadImageButton);

        userImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    //use standard intent to capture an files

                    Intent galleryIntent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //we will handle the returned data in onActivityResult
                    startActivityForResult(galleryIntent, CAMERA_CAPTURE);
                } catch (ActivityNotFoundException anfe) {
                    //display an error message
                    String errorMessage = "Whoops - your device doesn't support capturing images!";
                    Toast toast = Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (! Encoded_userimage.equalsIgnoreCase("")) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty(AppConstant.KEY_METHOD, AppConstant.METHOD.M_REGISTRATION);
                    jsonObject.addProperty(AppConstant.KEY_FNAME, input_fname);
                    jsonObject.addProperty(AppConstant.KEY_LNAME, input_lname);
                    jsonObject.addProperty(AppConstant.KEY_EMAILID, input_email);
                    jsonObject.addProperty(AppConstant.KEY_GENDER, radioButton);
                    jsonObject.addProperty(AppConstant.KEY_FATHERNAME, input_fathername);
                    jsonObject.addProperty(AppConstant.KEY_MOTHERSNAME, input_mothersname);
                    jsonObject.addProperty(AppConstant.KEY_PASSWORD, input_password);
                    jsonObject.addProperty(AppConstant.KEY_USER_IMAGE, Encoded_userimage);

                    Log.v(TAG, "Json Request Register:- " + jsonObject.toString());
                    registerationTask(jsonObject);
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Please upload image !!!", Snackbar.LENGTH_LONG).show();                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_CAPTURE) {
                picUri = data.getData();
                performCrop();
            }//user is returning from cropping the files
            else if (requestCode == PIC_CROP) {
//get the returned data
                Bundle extras = data.getExtras();
//get the cropped bitmap
                thePic = extras.getParcelable("data");
                Log.d("pictureimage", String.valueOf(thePic));

                userImageView.setImageBitmap(thePic);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                thePic.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();

                Encoded_userimage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                Log.d(TAG, "Picture Image :-" + Encoded_userimage);
            }
        }
    }

    private void performCrop() {

        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate files type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);

        } catch (ActivityNotFoundException anfe) {
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void registerationTask(JsonObject jsonObject) {

        final ProgressHUD progressHD = ProgressHUD.show(VerificationActivity.this, "Please wait...", true, false, new DialogInterface.OnCancelListener() {
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

                        Snackbar.make(findViewById(android.R.id.content),"Registered Successfully !!!" ,Snackbar.LENGTH_LONG).show();

                        Intent intent = new Intent(VerificationActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                    } else if(jsonObject.get("status").equals("400")) {

                        String msg = jsonObject.getString("message");
                        Snackbar.make(findViewById(android.R.id.content),"Email Id already exists !!!" ,Snackbar.LENGTH_LONG).show();
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
}
