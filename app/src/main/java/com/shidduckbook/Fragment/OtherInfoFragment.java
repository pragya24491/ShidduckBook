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
import android.widget.CheckBox;
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

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtherInfoFragment extends Fragment {

    private static final String TAG = "OtherInfoFragment ";
    TextView testSpin;
    EditText input_MyselfDesc, input_PartnerDesc;
    Button update_btn;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, checkBox9, checkBox10;
    CheckBox checkBox11, checkBox12, checkBox13, checkBox14, checkBox15, checkBox16, checkBox17, checkBox18, checkBox19, checkBox20;
    CheckBox checkBox21, checkBox22, checkBox23, checkBox24;
    ArrayList<String> hobbiesList;
    String checkBoxString;
    CharSequence languageArray[];
    String selectedItem = null;

    public OtherInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_other_info, container, false);
        initViews(rootView);

        if (!AppPreferences.getUserId(getActivity()).equalsIgnoreCase("")) {

            input_MyselfDesc.setText(AppPreferences.getOtherInfoSelf(getActivity()));
            input_PartnerDesc.setText(AppPreferences.getOtherInfoPartner(getActivity()));
            testSpin.setText(AppPreferences.getYeshorimaTest(getActivity()));
        }

        return rootView;
    }

    private void initViews(View rootView) {

        languageArray = (CharSequence[]) getResources().getStringArray(R.array.array_test);
        testSpin = (TextView) rootView.findViewById(R.id.spinTest);

        testSpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog("Select", testSpin, languageArray);
            }
        });

        input_MyselfDesc = (EditText) rootView.findViewById(R.id.input_MyselfDesc);
        input_PartnerDesc = (EditText) rootView.findViewById(R.id.input_PartnerDesc);

        update_btn = (Button) rootView.findViewById(R.id.update_btn);
        hobbiesList = new ArrayList<>();

        checkBox1 = (CheckBox) rootView.findViewById(R.id.checkbox1);
        checkBox2 = (CheckBox) rootView.findViewById(R.id.checkbox2);
        checkBox3 = (CheckBox) rootView.findViewById(R.id.checkbox3);
        checkBox4 = (CheckBox) rootView.findViewById(R.id.checkbox4);
        checkBox5 = (CheckBox) rootView.findViewById(R.id.checkbox5);
        checkBox6 = (CheckBox) rootView.findViewById(R.id.checkbox6);
        checkBox7 = (CheckBox) rootView.findViewById(R.id.checkbox7);
        checkBox8 = (CheckBox) rootView.findViewById(R.id.checkbox8);
        checkBox9 = (CheckBox) rootView.findViewById(R.id.checkbox9);
        checkBox10 = (CheckBox) rootView.findViewById(R.id.checkbox10);
        checkBox11 = (CheckBox) rootView.findViewById(R.id.checkbox11);
        checkBox12 = (CheckBox) rootView.findViewById(R.id.checkbox12);
        checkBox13 = (CheckBox) rootView.findViewById(R.id.checkbox13);
        checkBox14 = (CheckBox) rootView.findViewById(R.id.checkbox14);
        checkBox15 = (CheckBox) rootView.findViewById(R.id.checkbox15);
        checkBox16 = (CheckBox) rootView.findViewById(R.id.checkbox16);
        checkBox17 = (CheckBox) rootView.findViewById(R.id.checkbox17);
        checkBox18 = (CheckBox) rootView.findViewById(R.id.checkbox18);
        checkBox19 = (CheckBox) rootView.findViewById(R.id.checkbox19);
        checkBox20 = (CheckBox) rootView.findViewById(R.id.checkbox20);
        checkBox21 = (CheckBox) rootView.findViewById(R.id.checkbox21);
        checkBox22 = (CheckBox) rootView.findViewById(R.id.checkbox22);
        checkBox23 = (CheckBox) rootView.findViewById(R.id.checkbox23);
        checkBox24 = (CheckBox) rootView.findViewById(R.id.checkbox24);

        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkBoxString = checkBox1.getText().toString();
                if (checkBox1.isChecked()) {
                    hobbiesList.add(checkBoxString);
                    Log.d(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                } else {
                    hobbiesList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                }
            }
        });


        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxString = checkBox2.getText().toString();
                if (checkBox2.isChecked()) {
                    hobbiesList.add(checkBoxString);
                    Log.d(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                } else {
                    hobbiesList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                }

            }
        });


        checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxString = checkBox3.getText().toString();
                if (checkBox3.isChecked()) {
                    hobbiesList.add(checkBoxString);
                    Log.d(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                } else {
                    hobbiesList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                }

            }
        });


        checkBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxString = checkBox4.getText().toString();
                if (checkBox4.isChecked()) {
                    hobbiesList.add(checkBoxString);
                    Log.d(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                } else {
                    hobbiesList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                }

            }
        });


        checkBox5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxString = checkBox5.getText().toString();
                if (checkBox5.isChecked()) {
                    hobbiesList.add(checkBoxString);
                    Log.d(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                } else {
                    hobbiesList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                }
            }
        });


        checkBox6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxString = checkBox6.getText().toString();
                if (checkBox6.isChecked()) {
                    hobbiesList.add(checkBoxString);
                    Log.d(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                } else {
                    hobbiesList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                }
            }
        });


        checkBox7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxString = checkBox7.getText().toString();
                if (checkBox7.isChecked()) {
                    hobbiesList.add(checkBoxString);
                    Log.d(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                } else {
                    hobbiesList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                }
            }
        });


        checkBox8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxString = checkBox8.getText().toString();
                if (checkBox8.isChecked()) {
                    hobbiesList.add(checkBoxString);
                    Log.d(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                } else {
                    hobbiesList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                }
            }
        });


        checkBox9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxString = checkBox9.getText().toString();
                if (checkBox9.isChecked()) {
                    hobbiesList.add(checkBoxString);
                    Log.d(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                } else {
                    hobbiesList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                }

            }
        });


        checkBox10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxString = checkBox10.getText().toString();
                if (checkBox10.isChecked()) {
                    hobbiesList.add(checkBoxString);
                    Log.d(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                } else {
                    hobbiesList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                }
            }
        });

        checkBox11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxString = checkBox11.getText().toString();
                if (checkBox11.isChecked()) {
                    hobbiesList.add(checkBoxString);
                    Log.d(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                } else {
                    hobbiesList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                }
            }
        });

        checkBox12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxString = checkBox12.getText().toString();
                if (checkBox12.isChecked()) {
                    hobbiesList.add(checkBoxString);
                    Log.d(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                } else {
                    hobbiesList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                }
            }
        });

        checkBox13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxString = checkBox13.getText().toString();
                if (checkBox13.isChecked()) {
                    hobbiesList.add(checkBoxString);
                    Log.d(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                } else {
                    hobbiesList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                }
            }
        });

        checkBox14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxString = checkBox14.getText().toString();
                if (checkBox14.isChecked()) {
                    hobbiesList.add(checkBoxString);
                    Log.d(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                } else {
                    hobbiesList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                }
            }
        });


        checkBox15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxString = checkBox15.getText().toString();
                if (checkBox15.isChecked()) {
                    hobbiesList.add(checkBoxString);
                    Log.d(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                } else {
                    hobbiesList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                }
            }
        });


        checkBox16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxString = checkBox16.getText().toString();
                if (checkBox16.isChecked()) {
                    hobbiesList.add(checkBoxString);
                    Log.d(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                } else {
                    hobbiesList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                }
            }
        });


        checkBox17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxString = checkBox17.getText().toString();
                if (checkBox17.isChecked()) {
                    hobbiesList.add(checkBoxString);
                    Log.d(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                } else {
                    hobbiesList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                }
            }
        });


        checkBox18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxString = checkBox18.getText().toString();
                if (checkBox18.isChecked()) {
                    hobbiesList.add(checkBoxString);
                    Log.d(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                } else {
                    hobbiesList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                }
            }
        });


        checkBox19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxString = checkBox19.getText().toString();
                if (checkBox19.isChecked()) {
                    hobbiesList.add(checkBoxString);
                    Log.d(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                } else {
                    hobbiesList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                }
            }
        });


        checkBox20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxString = checkBox20.getText().toString();
                if (checkBox20.isChecked()) {
                    hobbiesList.add(checkBoxString);
                    Log.d(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                } else {
                    hobbiesList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                }
            }
        });


        checkBox21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxString = checkBox21.getText().toString();
                if (checkBox21.isChecked()) {
                    hobbiesList.add(checkBoxString);
                    Log.d(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                } else {
                    hobbiesList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                }

            }
        });

        checkBox22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxString = checkBox22.getText().toString();
                if (checkBox22.isChecked()) {
                    hobbiesList.add(checkBoxString);
                    Log.d(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                } else {
                    hobbiesList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                }

            }
        });


        checkBox23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxString = checkBox23.getText().toString();
                if (checkBox23.isChecked()) {
                    hobbiesList.add(checkBoxString);
                    Log.d(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                } else {
                    hobbiesList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                }
            }
        });


        checkBox24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxString = checkBox24.getText().toString();
                if (checkBox24.isChecked()) {
                    hobbiesList.add(checkBoxString);
                    Log.d(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                } else {
                    hobbiesList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + hobbiesList);
                }
            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String yeshorimTest = testSpin.getText().toString();

                Log.v(TAG, "Array list before update :- " + hobbiesList);
                String hobbies = hobbiesList.toString();
                Log.v(TAG, "Hobbies list:- " + hobbies.replace("[", "").replace("]", ""));

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(AppConstant.KEY_METHOD, AppConstant.METHOD.M_OTHER_INFO);
                jsonObject.addProperty(AppConstant.KEY_USER_ID, AppPreferences.getUserId(getActivity()));
                jsonObject.addProperty(AppConstant.KEY_HOBBIES, hobbies.replace("[", "").replace("]", ""));
                jsonObject.addProperty(AppConstant.KEY_YESHORIM_TEST, yeshorimTest);
                jsonObject.addProperty(AppConstant.KEY_DESCRIBE_YOUR_SELF, input_MyselfDesc.getText().toString().trim());
                jsonObject.addProperty(AppConstant.KEY_IN_A_PARTNER, input_PartnerDesc.getText().toString().trim());

                Log.v(TAG, "Json Request :- " + jsonObject);
                otherInfoApi(jsonObject);

            }
        });

    }

    private void dialog(String textTitle, final TextView textView, final CharSequence[] arrayyear) {

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


    private void otherInfoApi(JsonObject jsonObject) {

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

                Log.e(TAG, "Json Response :- " + response.body().toString());
                String s = response.body().toString();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("status").equalsIgnoreCase("200")) {

                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                        AppPreferences.setOtherInfoSelf(getActivity(), jsonObject1.getString("describe_your_Self"));
                        AppPreferences.setOtherInfoPartner(getActivity(), jsonObject1.getString("in_a_partner"));
                        AppPreferences.setYeshorimaTest(getActivity(), jsonObject1.getString("yeshorim_test"));
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