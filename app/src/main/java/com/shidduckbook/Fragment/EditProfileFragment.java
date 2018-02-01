package com.shidduckbook.Fragment;


import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.shidduckbook.Activity.MainActivity;
import com.shidduckbook.R;
import com.shidduckbook.RetrofitClasses.ApiClient;
import com.shidduckbook.RetrofitClasses.MyApiEndpointInterface;
import com.shidduckbook.Util.AppConstant;
import com.shidduckbook.Util.AppPreferences;
import com.shidduckbook.Util.ProgressHUD;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {

    private static final String TAG = "EditProfileFragment";
    final int CAMERA_CAPTURE = 1;
    final int PIC_CROP = 2;
    CircleImageView userImageView;
    ImageView editImageView;
    Button update_btn;
    RadioGroup radioGroup1, radioGroup2;
    String radioValueImage = "No", radioValuePrivacy = "Only to people i shared my resume with";
    Bitmap thePic;
    private Uri picUri;
    private String Encoded_userimage = "";

    public EditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        setHasOptionsMenu(false);
        initViews(rootView);

        radioValueImage = AppPreferences.getImageExpansionPrivacy(getActivity());
        radioValueImage = AppPreferences.getProfilePicturePrivacy(getActivity());

        if (!AppPreferences.getUserImage(getActivity()).equalsIgnoreCase("")) {

            Picasso.with(getActivity()).load(AppPreferences.getUserImage(getActivity()))
                    .placeholder(R.drawable.pro_icon)
                    .error(R.drawable.pro_icon)
                    .resize(200, 200)
                    .into(userImageView);
        }
        return rootView;
    }

    private void initViews(View rootView) {

        userImageView = (CircleImageView) rootView.findViewById(R.id.userImageView);
        editImageView = (ImageView) rootView.findViewById(R.id.editImageView);
        update_btn = (Button) rootView.findViewById(R.id.update_btn);

        radioGroup1 = (RadioGroup) rootView.findViewById(R.id.radioGroup1);
        radioGroup2 = (RadioGroup) rootView.findViewById(R.id.radioGroup2);

        if (!AppPreferences.getImageExpansionPrivacy(getActivity()).equalsIgnoreCase("")) {

            String imageExp = AppPreferences.getImageExpansionPrivacy(getActivity());
            RadioButton yes = (RadioButton) rootView.findViewById(R.id.radioYes);
            RadioButton no = (RadioButton) rootView.findViewById(R.id.radioNo);
            String yesText = yes.getText().toString();
            String noText = no.getText().toString();
            if (yesText.equalsIgnoreCase(imageExp)) {
                yes.setChecked(true);
            } else {
                no.setChecked(true);
            }
            Log.v(TAG, "Image exp string :- " + imageExp);
            Log.v(TAG, "check id :- " + yesText);
        }

        if (!AppPreferences.getProfilePicturePrivacy(getActivity()).equalsIgnoreCase("")) {

            String proPic = AppPreferences.getProfilePicturePrivacy(getActivity());

            RadioButton radio1 = (RadioButton) rootView.findViewById(R.id.radio1);
            RadioButton radio2 = (RadioButton) rootView.findViewById(R.id.radio2);
            RadioButton radio3 = (RadioButton) rootView.findViewById(R.id.radio3);
            RadioButton radio4 = (RadioButton) rootView.findViewById(R.id.radio4);
            RadioButton radio5 = (RadioButton) rootView.findViewById(R.id.radio5);

            Log.v(TAG, "Profile Picture string :- " + proPic);

            if (radio1.getText().toString().equalsIgnoreCase(proPic)) {
                radio1.setChecked(true);
            } else if (radio2.getText().toString().equalsIgnoreCase(proPic)) {
                radio2.setChecked(true);
            } else if (radio3.getText().toString().equalsIgnoreCase(proPic)) {
                radio3.setChecked(true);
            } else if (radio4.getText().toString().equalsIgnoreCase(proPic)) {
                radio4.setChecked(true);
            } else if (radio5.getText().toString().equalsIgnoreCase(proPic)) {
                radio5.setChecked(true);
            }
        }

        editImageView.setOnClickListener(new View.OnClickListener() {
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
                    Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                if (checkedId == R.id.radioYes) {
                    radioValueImage = "Yes";
                } else {
                    radioValueImage = "No";
                }
            }
        });

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                if (checkedId == R.id.radio1) {
                    radioValuePrivacy = "Only to people i shared my resume with";

                } else if (checkedId == R.id.radio2) {

                    radioValuePrivacy = "Nobody";

                } else if (checkedId == R.id.radio3) {

                    radioValuePrivacy = "Only to people I'm interested in";

                } else if (checkedId == R.id.radio4) {

                    radioValuePrivacy = "Only to that matches my character traits";

                } else if (checkedId == R.id.radio5) {

                    radioValuePrivacy = "Everybody";

                }
            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(AppConstant.KEY_METHOD, AppConstant.METHOD.M_EDIT_PROFILE);
                jsonObject.addProperty(AppConstant.KEY_USER_ID, AppPreferences.getUserId(getActivity()));
                jsonObject.addProperty("user_image", Encoded_userimage);
                jsonObject.addProperty("image_expansion", radioValueImage);
                jsonObject.addProperty("pro_privacy", radioValuePrivacy);

                Log.v(TAG, "Json Request Edit Profile :- " + jsonObject.toString());
                editProfileTask(jsonObject);
            }
        });

    }

    private void editProfileTask(JsonObject jsonObject) {

        final ProgressHUD progressHD = ProgressHUD.show(getActivity(), "Please wait...", true, false, new DialogInterface.OnCancelListener() {
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

                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Profile Updated Successfully !", Snackbar.LENGTH_LONG).show();

                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                        AppPreferences.setUserId(getActivity(), jsonObject1.getString("user_id"));
                        AppPreferences.setUserImage(getActivity(), jsonObject1.getString("user_image"));
                        AppPreferences.setProfilePicturePrivacy(getActivity(), jsonObject1.getString("pro_privacy"));
                        AppPreferences.setImageExpansionPrivacy(getActivity(), jsonObject1.getString("image_expansion"));

                        Picasso.with(getActivity()).load(AppPreferences.getUserImage(getActivity()))
                                .placeholder(R.drawable.pro_icon)
                                .error(R.drawable.pro_icon)
                                .resize(200, 200)
                                .into(userImageView);

                        Picasso.with(getActivity()).load(AppPreferences.getUserImage(getActivity()))
                                .placeholder(R.drawable.pro_icon)
                                .error(R.drawable.pro_icon)
                                .resize(200, 200)
                                .into(MainActivity.userImageView);

                    } else if (jsonObject.get("status").equals("400")) {

                        String msg = jsonObject.getString("message");
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Some error occured !", Snackbar.LENGTH_LONG).show();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
            Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }

    }

}
