package com.shidduckbook.Fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.res.Resources;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ABHI on 30-05-2017.
 */

public class Personal_Info_Tab extends Fragment {

    private static final String TAG = "Personal_Info_Tab";
    public TextView monthspin;
    public TextView datespin;
    public TextView yearspin;
    public TextView spinTeshuva;
    EditText input_fname, input_lname, input_email, input_fathername, input_mothersname, input_contact_no, input_country, input_state, input_city;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7;
    Button update_btn;
    String checkBoxString = "";
    ArrayList<String> religionList;
    String dob;
    CharSequence yearArray[], monthArray[], dateArray[], teshuva[];
    String selectedItem = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_personal_info, container, false);
        Resources res = getResources();


        initViews(rootView);

        if (!AppPreferences.getUserId(getActivity()).equalsIgnoreCase("")) {

            input_fname.setText(AppPreferences.getFirstName(getActivity()));
            input_lname.setText(AppPreferences.getLastName(getActivity()));
            input_email.setText(AppPreferences.getEmailId(getActivity()));
            input_fathername.setText(AppPreferences.getFatherName(getActivity()));
            input_mothersname.setText(AppPreferences.getMotherName(getActivity()));
            input_contact_no.setText(AppPreferences.getPhoneNumber(getActivity()));
            input_country.setText(AppPreferences.getCountry(getActivity()));
            input_state.setText(AppPreferences.getState(getActivity()));
            input_city.setText(AppPreferences.getCity(getActivity()));

            spinTeshuva.setText(AppPreferences.getBaalTeshuva(getActivity()));
            yearspin.setText(AppPreferences.getYear(getActivity()));
            monthspin.setText(AppPreferences.getMonth(getActivity()));
            datespin.setText(AppPreferences.getDate(getActivity()));

            String religion = AppPreferences.getReligion(getActivity());
            Log.v(TAG, "Religion selected :- " + religion);
            ArrayList<String> strings = new ArrayList<>(Arrays.asList(religion.split(",")));
            Log.v(TAG, "Religion selected array list:- " + strings.toString());

            for (int i = 0; i < strings.size(); ) {
                System.out.println(strings.get(i));
                Log.v(TAG, "Array List Religion :- " + strings.get(i));

                if (strings.get(i).equals(checkBox1.getText())) {
                    Log.v(TAG, "inside check box 1 :- " + strings.get(i));
                    checkBox1.setChecked(true);
                }
                if (strings.get(i).equals(checkBox2.getText())) {
                    Log.v(TAG, "inside check box 2 :- " + strings.get(i));
                    checkBox2.setChecked(true);
                }
                if (strings.get(i).equals(checkBox3.getText())) {
                    Log.v(TAG, "inside check box 3 :- " + strings.get(i));
                    checkBox3.setChecked(true);
                }
                if (strings.get(i).equals(checkBox4.getText())) {
                    Log.v(TAG, "inside check box 4 :- " + strings.get(i));
                    checkBox4.setChecked(true);
                }
                if (strings.get(i).equals(checkBox5.getText())) {
                    Log.v(TAG, "inside check box 5 :- " + strings.get(i));
                    checkBox5.setChecked(true);
                }
                i++;
            }

        }
        return rootView;
    }

    private void initViews(View rootView) {

        yearArray = (CharSequence[]) getResources().getStringArray(R.array.array_year);
        monthArray = (CharSequence[]) getResources().getStringArray(R.array.array_month);
        dateArray = (CharSequence[]) getResources().getStringArray(R.array.array_date);
        teshuva = (CharSequence[]) getResources().getStringArray(R.array.array_teshuva);

        yearspin = (TextView) rootView.findViewById(R.id.yearspin);
        monthspin = (TextView) rootView.findViewById(R.id.monthspin);
        datespin = (TextView) rootView.findViewById(R.id.datespin);
        spinTeshuva = (TextView) rootView.findViewById(R.id.spinTeshuva);


        spinTeshuva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yearDialog("Select", spinTeshuva, teshuva);
            }
        });

        yearspin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yearDialog("Select Year", yearspin, yearArray);
            }
        });

        monthspin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yearDialog("Select Month", monthspin, monthArray);
            }
        });

        datespin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yearDialog("Select Date", datespin, dateArray);
            }
        });

        input_fname = (EditText) rootView.findViewById(R.id.input_fname);
        input_lname = (EditText) rootView.findViewById(R.id.input_lname);
        input_email = (EditText) rootView.findViewById(R.id.input_email);
        input_fathername = (EditText) rootView.findViewById(R.id.input_fathername);
        input_mothersname = (EditText) rootView.findViewById(R.id.input_mothersname);
        input_contact_no = (EditText) rootView.findViewById(R.id.input_contact_no);
        input_country = (EditText) rootView.findViewById(R.id.input_country);
        input_state = (EditText) rootView.findViewById(R.id.input_state);
        input_city = (EditText) rootView.findViewById(R.id.input_city);

        checkBox1 = (CheckBox) rootView.findViewById(R.id.checkbox1);
        checkBox2 = (CheckBox) rootView.findViewById(R.id.checkbox2);
        checkBox3 = (CheckBox) rootView.findViewById(R.id.checkbox3);
        checkBox4 = (CheckBox) rootView.findViewById(R.id.checkbox4);
        checkBox5 = (CheckBox) rootView.findViewById(R.id.checkbox5);
        checkBox6 = (CheckBox) rootView.findViewById(R.id.checkbox6);
        checkBox7 = (CheckBox) rootView.findViewById(R.id.checkbox7);
        update_btn = (Button) rootView.findViewById(R.id.update_btn);

        religionList = new ArrayList<>();

        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxString = checkBox1.getText().toString();

                if (checkBox1.isChecked()) {
                    religionList.add(checkBoxString);
                    Log.v(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + religionList);
                } else {
                    religionList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + religionList);
                }

            }
        });

        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxString = checkBox2.getText().toString();
                if (checkBox2.isChecked()) {
                    religionList.add(checkBoxString);
                    Log.d(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + religionList);
                } else {
                    religionList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + religionList);
                }
            }
        });

        checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxString = checkBox3.getText().toString();
                if (checkBox3.isChecked()) {
                    religionList.add(checkBoxString);
                    Log.d(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + religionList);
                } else {
                    religionList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + religionList);
                }
            }
        });

        checkBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxString = checkBox4.getText().toString();
                if (checkBox4.isChecked()) {
                    religionList.add(checkBoxString);
                    Log.d(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + religionList);
                } else {
                    religionList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + religionList);
                }
            }
        });

        checkBox5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxString = checkBox5.getText().toString();
                if (checkBox5.isChecked()) {
                    religionList.add(checkBoxString);
                    Log.d(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + religionList);
                } else {
                    religionList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + religionList);
                }
            }
        });

        checkBox6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxString = checkBox6.getText().toString();
                if (checkBox6.isChecked()) {
                    religionList.add(checkBoxString);
                    Log.d(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + religionList);
                } else {
                    religionList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + religionList);
                }
            }
        });

        checkBox7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxString = checkBox7.getText().toString();
                if (checkBox7.isChecked()) {
                    religionList.add(checkBoxString);
                    Log.d(TAG, "Check Box selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + religionList);
                } else {
                    religionList.remove(checkBoxString);
                    Log.v(TAG, "Check Box not selected:- " + checkBoxString);
                    Log.v(TAG, "Array list :- " + religionList);
                }
            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String year = yearspin.getText().toString();
                String month = monthspin.getText().toString();
                String date = datespin.getText().toString();
                String teshuva = spinTeshuva.getText().toString();

                Date date2;
                Calendar cal = Calendar.getInstance();
                int monthInt = 0;

                try {
                    date2 = new SimpleDateFormat("MMMM").parse(month);

                    cal.setTime(date2);

                    Log.v(TAG, "Month in bnumber :- " +cal.get(Calendar.MONTH));

                    monthInt = cal.get(Calendar.MONTH) + 1;

                    Log.v(TAG, "Month Int :- " + monthInt);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                dob = date + "-" + monthInt + "-" + year;

                String religion = religionList.toString();
                Log.v(TAG, "religion list :- " + religion.replace("[", "").replace("]", ""));

                /*
                *
                * {"method":"personal_info","first_name":"ashu","last_name":"kh","email_id":"ashu@gmail.com","phone_number":"male",
                * "father_name":"Father-","mother_name":"mummy","country":"India","state":"MP","city":"Indore","dob":"24-04-1990",
                * "religion":"test,test1","baal_teshuva":"No","user_id":"1"}
                *
                * */

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(AppConstant.KEY_METHOD, AppConstant.METHOD.M_PERSONAL_INFO);
                jsonObject.addProperty(AppConstant.KEY_USER_ID, AppPreferences.getUserId(getActivity()));
                jsonObject.addProperty(AppConstant.KEY_FNAME, input_fname.getText().toString().trim());
                jsonObject.addProperty(AppConstant.KEY_LNAME, input_lname.getText().toString().trim());
                jsonObject.addProperty(AppConstant.KEY_EMAILID, input_email.getText().toString().trim());
                jsonObject.addProperty(AppConstant.KEY_PHONE_NUMBER, input_contact_no.getText().toString().trim());
                jsonObject.addProperty(AppConstant.KEY_FATHERNAME, input_fathername.getText().toString().trim());
                jsonObject.addProperty(AppConstant.KEY_MOTHERSNAME, input_mothersname.getText().toString().trim());
                jsonObject.addProperty(AppConstant.KEY_COUNTRY, input_country.getText().toString().trim());
                jsonObject.addProperty(AppConstant.KEY_STATE, input_state.getText().toString().trim());
                jsonObject.addProperty(AppConstant.KEY_CITY, input_city.getText().toString().trim());
                jsonObject.addProperty(AppConstant.KEY_DOB, dob);
                jsonObject.addProperty(AppConstant.KEY_BAAL_TESHUVA, teshuva);
                jsonObject.addProperty(AppConstant.KEY_RELIGION, religion.replace("[", "").replace("]", ""));

                Log.v(TAG, "Json Request :- " + jsonObject);
                personalInfoApi(jsonObject);
            }
        });
    }

    private String getMonthFullName(int monthNumber)
    {
        String monthName="";

        if(monthNumber>=0 && monthNumber<12)
            try
            {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.MONTH, monthNumber);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM");
                simpleDateFormat.setCalendar(calendar);
                monthName = simpleDateFormat.format(calendar.getTime());
            } catch (Exception e)
            {
                if(e!=null)
                    e.printStackTrace();
            }

        return monthName;
    }

    private void personalInfoApi(JsonObject jsonObject) {

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

                /*
                *  {"message":"Update Successfully","result":[{"first_name":"ashu","last_name":"kh","email_id":"ashu@gmail.com","phone_number":"male",
                *  "father_name":"Father-","mother_name":"mummy","country":"India","state":"MP","city":"Indore","user_id":"1"}],"status":"200"}
                *
                * */

                Log.e(TAG, "Json Response Register:- " + response.body().toString());
                String s = response.body().toString();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("status").equalsIgnoreCase("200")) {

                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                        AppPreferences.setFirstName(getActivity(), jsonObject1.getString("first_name"));
                        AppPreferences.setLastName(getActivity(), jsonObject1.getString("last_name"));
                        AppPreferences.setEmailId(getActivity(), jsonObject1.getString("email_id"));
                        AppPreferences.setFatherName(getActivity(), jsonObject1.getString("father_name"));
                        AppPreferences.setMotherName(getActivity(), jsonObject1.getString("mother_name"));

                        AppPreferences.setPhoneNumber(getActivity(), jsonObject1.getString("phone_number"));
                        AppPreferences.setCountry(getActivity(), jsonObject1.getString("country"));
                        AppPreferences.setState(getActivity(), jsonObject1.getString("state"));
                        AppPreferences.setCity(getActivity(), jsonObject1.getString("city"));
                        AppPreferences.setDob(getActivity(), jsonObject1.getString("dob"));
                        AppPreferences.setReligion(getActivity(), jsonObject1.getString("religion"));
                        AppPreferences.setBaalTeshuva(getActivity(), jsonObject1.getString("baal_teshuva"));

                        String dob = jsonObject1.getString("dob");
                        String[] sep = dob.split("-");
                        Log.v(TAG, "Year :- " + sep[0]);
                        Log.v(TAG, "Month :- " + sep[1]);
                        Log.v(TAG, "Date :- " + sep[2]);
                        AppPreferences.setDate(getActivity(), sep[0]);
                        AppPreferences.setMonth(getActivity(), sep[1]);
                        AppPreferences.setYear(getActivity(), sep[2]);

                        String religion = jsonObject1.getString("religion");
                        Log.v(TAG, "Religion selected :- " + religion);
                        ArrayList<String> strings = new ArrayList<>(Arrays.asList(religion.split(",")));
                        Log.v(TAG, "Religion selected array list:- " + strings.toString());

//                        strings.add(religion.split(","));

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

    private void yearDialog(String textTitle, final TextView textView, final CharSequence[] arrayyear) {

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
}
