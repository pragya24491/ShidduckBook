package com.shidduckbook.Fragment;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.shidduckbook.R;
import com.shidduckbook.RetrofitClasses.ApiClient;
import com.shidduckbook.RetrofitClasses.MyApiEndpointInterface;
import com.shidduckbook.Util.AppConstant;
import com.shidduckbook.Util.AppPreferences;
import com.shidduckbook.Util.ProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class EducationalFragment extends Fragment {

    private static final String TAG = "EducationalFragment";
    TextView motherTongueSpin, otherlangSpin, otherlangSpin1;
    EditText input_elementaryschool, input_highschool, input_yeshiva, editText_description;
    Button update_btn;
    CharSequence languageArray[];
    String selectedItem = null;

    public EducationalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_educational, container, false);
        initViews(rootView);


        if (!AppPreferences.getUserId(getActivity()).equalsIgnoreCase("")) {

            input_elementaryschool.setText(AppPreferences.getElementarySchool(getActivity()));
            input_yeshiva.setText(AppPreferences.getYeshiva(getActivity()));
            input_highschool.setText(AppPreferences.getHighSchool(getActivity()));
            editText_description.setText(AppPreferences.getDescription(getActivity()));
            motherTongueSpin.setText(AppPreferences.getMotherTongue(getActivity()));
            otherlangSpin.setText(AppPreferences.getOtherLanguage(getActivity()));
            otherlangSpin1.setText(AppPreferences.getOtherLanguage1(getActivity()));
        }
        return rootView;
    }

    private void initViews(View rootView) {

        languageArray = (CharSequence[]) getResources().getStringArray(R.array.array_language);
        motherTongueSpin = (TextView) rootView.findViewById(R.id.motherTongueSpin);
        otherlangSpin = (TextView) rootView.findViewById(R.id.otherlangSpin);
        otherlangSpin1 = (TextView) rootView.findViewById(R.id.otherlangSpin1);

        motherTongueSpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                languageDialog("Select Language", motherTongueSpin, languageArray);
            }
        });

        otherlangSpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                languageDialog("Select Language", otherlangSpin, languageArray);

            }
        });

        otherlangSpin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                languageDialog("Select Language", otherlangSpin1, languageArray);

            }
        });

        input_elementaryschool = (EditText) rootView.findViewById(R.id.input_elementaryschool);
        input_highschool = (EditText) rootView.findViewById(R.id.input_highschool);
        input_yeshiva = (EditText) rootView.findViewById(R.id.input_yeshiva);
        editText_description = (EditText) rootView.findViewById(R.id.editText_description);
        update_btn = (Button) rootView.findViewById(R.id.update_btn);

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String lang1 = motherTongueSpin.getText().toString();
                String lang2 = otherlangSpin.getText().toString();
                String lang3 = otherlangSpin1.getText().toString();

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(AppConstant.KEY_METHOD, AppConstant.METHOD.M_PEROSNAL_EDUCATION);
                jsonObject.addProperty(AppConstant.KEY_USER_ID, AppPreferences.getUserId(getActivity()));

                jsonObject.addProperty(AppConstant.KEY_ELEMENTARY_SCHOOL, input_elementaryschool.getText().toString().trim());
                jsonObject.addProperty(AppConstant.KEY_HIGH_SCHOOL, input_highschool.getText().toString().trim());
                jsonObject.addProperty(AppConstant.KEY_YESHIVA, input_yeshiva.getText().toString().trim());
                jsonObject.addProperty(AppConstant.KEY_DESCRIPTION, editText_description.getText().toString().trim());
                jsonObject.addProperty(AppConstant.KEY_MOTHER_TONGUE, lang1);
                jsonObject.addProperty(AppConstant.KEY_OTHER_LANGUAGE, lang2);
                jsonObject.addProperty(AppConstant.KEY_OTHER_LANGUAGE1, lang3);

                Log.v(TAG, "Json Request :- " + jsonObject);
                educationalInfoApi(jsonObject);
            }
        });

    }

    private void languageDialog(String textTitle, final TextView textView, final CharSequence[] arrayyear) {

        int index = -1;
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity(), R.style.RadioDialogTheme);

        TextView title = new TextView(getActivity());
// You Can Customise your Title here
        title.setText(textTitle);
        title.setBackgroundColor(Color.TRANSPARENT);
        title.setPadding(10, 20, 10, 20);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(getResources().getColor(R.color.colorPrimary));
        title.setTextSize(20);
        title.setTypeface(Typeface.DEFAULT_BOLD);

        alert.setCustomTitle(title);
        alert.setCancelable(false);
        alert.setSingleChoiceItems(arrayyear, index, new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v(TAG, "Selected Position :- " + arrayyear[which]);
                        selectedItem = arrayyear[which].toString();
                        Log.v(TAG, "Selected Position :- " + selectedItem);

                    }
                });

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG, "Out Side Last Seen Selected Option :-" + selectedItem);
                textView.setText(selectedItem);
                dialogInterface.dismiss();

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
        Button negButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button posiButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);

        if (negButton != null) {
            negButton.setTextColor(getResources().getColor(R.color.colorPrimary));
            negButton.setTypeface(Typeface.DEFAULT_BOLD);
        }
        if (posiButton != null) {
            posiButton.setTextColor(getResources().getColor(R.color.colorPrimary));
            posiButton.setTypeface(Typeface.DEFAULT_BOLD);
        }

//        alert.show();
    }

    private void educationalInfoApi(JsonObject jsonObject) {

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

                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                        AppPreferences.setElementarySchool(getActivity(), jsonObject1.getString("elementary_school"));
                        AppPreferences.setHighSchool(getActivity(), jsonObject1.getString("high_school"));
                        AppPreferences.setYeshiva(getActivity(), jsonObject1.getString("yeshiva"));
                        AppPreferences.setDescription(getActivity(), jsonObject1.getString("description"));
                        AppPreferences.setMotherTongue(getActivity(), jsonObject1.getString("mother_tongue"));

                        AppPreferences.setOtherLanguage(getActivity(), jsonObject1.getString("other_language"));
                        AppPreferences.setOtherLanguage1(getActivity(), jsonObject1.getString("other_language1"));

                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Data Updated Successfully !", Snackbar.LENGTH_LONG).show();

                    } else if (jsonObject.get("status").equals("400")) {

                        Snackbar.make(getActivity().findViewById(android.R.id.content), "User does not exist !", Snackbar.LENGTH_LONG).show();
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
