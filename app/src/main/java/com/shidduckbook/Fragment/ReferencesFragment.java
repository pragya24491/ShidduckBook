package com.shidduckbook.Fragment;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonArray;
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
public class ReferencesFragment extends Fragment {

    private static final String TAG = "ReferencesFragment";
    EditText input_name_ref1, input_email_ref1, input_phoneno_ref1, input_relation_ref1;
    EditText input_name_ref2, input_email_ref2, input_phoneno_ref2, input_relation_ref2;
    EditText input_name_ref3, input_email_ref3, input_phoneno_ref3, input_relation_ref3;
    EditText input_name_ref4, input_email_ref4, input_phoneno_ref4, input_relation_ref4;
    EditText input_name_ref5, input_email_ref5, input_phoneno_ref5, input_relation_ref5;
    EditText input_name_ref6, input_email_ref6, input_phoneno_ref6, input_relation_ref6;
    EditText input_Mechutonim, input_otherinfo, input_otherinfo1;
    Button update_btn;
    JsonArray referenceDetails;

    public ReferencesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_references, container, false);


        initViews(rootView);
        return rootView;
    }

    private void initViews(View rootView) {

        input_name_ref1 = (EditText) rootView.findViewById(R.id.input_name_ref1);
        input_email_ref1 = (EditText) rootView.findViewById(R.id.input_email_ref1);
        input_phoneno_ref1 = (EditText) rootView.findViewById(R.id.input_phoneno_ref1);
        input_relation_ref1 = (EditText) rootView.findViewById(R.id.input_relation_ref1);

        input_name_ref2 = (EditText) rootView.findViewById(R.id.input_name_ref2);
        input_email_ref2 = (EditText) rootView.findViewById(R.id.input_email_ref2);
        input_phoneno_ref2 = (EditText) rootView.findViewById(R.id.input_phoneno_ref2);
        input_relation_ref2 = (EditText) rootView.findViewById(R.id.input_relation_ref2);

        input_name_ref3 = (EditText) rootView.findViewById(R.id.input_name_ref3);
        input_email_ref3 = (EditText) rootView.findViewById(R.id.input_email_ref3);
        input_phoneno_ref3 = (EditText) rootView.findViewById(R.id.input_phoneno_ref3);
        input_relation_ref3 = (EditText) rootView.findViewById(R.id.input_relation_ref3);

        input_name_ref4 = (EditText) rootView.findViewById(R.id.input_name_ref4);
        input_email_ref4 = (EditText) rootView.findViewById(R.id.input_email_ref4);
        input_phoneno_ref4 = (EditText) rootView.findViewById(R.id.input_phoneno_ref4);
        input_relation_ref4 = (EditText) rootView.findViewById(R.id.input_relation_ref4);


        input_name_ref5 = (EditText) rootView.findViewById(R.id.input_name_ref5);
        input_email_ref5 = (EditText) rootView.findViewById(R.id.input_email_ref5);
        input_phoneno_ref5 = (EditText) rootView.findViewById(R.id.input_phoneno_ref5);
        input_relation_ref5 = (EditText) rootView.findViewById(R.id.input_relation_ref5);

        input_name_ref6 = (EditText) rootView.findViewById(R.id.input_name_ref6);
        input_email_ref6 = (EditText) rootView.findViewById(R.id.input_email_ref6);
        input_phoneno_ref6 = (EditText) rootView.findViewById(R.id.input_phoneno_ref6);
        input_relation_ref6 = (EditText) rootView.findViewById(R.id.input_relation_ref6);

        input_Mechutonim = (EditText) rootView.findViewById(R.id.input_Mechutonim);
        input_otherinfo = (EditText) rootView.findViewById(R.id.input_otherinfo);
        input_otherinfo1 = (EditText) rootView.findViewById(R.id.input_otherinfo1);

        update_btn = (Button) rootView.findViewById(R.id.update_btn);

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                referenceDetails = new JsonArray();
                addReferenceDetails(referenceDetails);

                Log.v(TAG, "Reference Details array :- " + referenceDetails.toString());

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(AppConstant.KEY_METHOD, AppConstant.METHOD.M_YOUR_REFERENCE);
                jsonObject.addProperty(AppConstant.KEY_USER_ID, AppPreferences.getUserId(getActivity()));
                jsonObject.add("referenceDetails", referenceDetails);
                jsonObject.addProperty("parent_mechutonim", input_Mechutonim.getText().toString().trim());
                jsonObject.addProperty("other_references", input_otherinfo.getText().toString().trim());
                jsonObject.addProperty("additional_information", input_otherinfo1.getText().toString().trim());

                Log.v(TAG, "Json Request Reference Profile :- " + jsonObject.toString());
                referencesTask(jsonObject);
            }
        });

    }

    private void referencesTask(JsonObject jsonObject) {

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

                Log.e(TAG, "Json Response References :- " + response.body().toString());
                String s = response.body().toString();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("status").equalsIgnoreCase("200")) {

                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                        AppPreferences.setReferenceName1 (getActivity(), input_name_ref1.getText().toString().trim());
                        AppPreferences.setReferenceName2(getActivity(), input_name_ref2.getText().toString().trim());
                        AppPreferences.setAdditionalReferenceName1(getActivity(), input_name_ref3.getText().toString().trim());
                        AppPreferences.setAdditionalReferenceName2(getActivity(), input_name_ref4.getText().toString().trim());
                        AppPreferences.setReferenceFriendName1(getActivity(), input_name_ref5.getText().toString().trim());
                        AppPreferences.setReferenceFriendName2(getActivity(), input_name_ref6.getText().toString().trim());

                        AppPreferences.setReferencePhone1(getActivity(), input_phoneno_ref1.getText().toString().trim());
                        AppPreferences.setReferencePhone2(getActivity(), input_phoneno_ref2.getText().toString().trim());
                        AppPreferences.setAdditionalReferencePhone1(getActivity(), input_phoneno_ref3.getText().toString().trim());
                        AppPreferences.setAdditionalReferencePhone2(getActivity(), input_phoneno_ref4.getText().toString().trim());
                        AppPreferences.setReferenceFriendPhone1(getActivity(), input_phoneno_ref5.getText().toString().trim());
                        AppPreferences.setReferenceFriendPhone2(getActivity(), input_phoneno_ref6.getText().toString().trim());

                        AppPreferences.setReferenceEmail1(getActivity(), input_email_ref1.getText().toString().trim());
                        AppPreferences.setReferenceEmail2(getActivity(), input_email_ref2.getText().toString().trim());
                        AppPreferences.setAdditionalReferenceEmail1(getActivity(), input_email_ref3.getText().toString().trim());
                        AppPreferences.setAdditionalReferenceEmail2(getActivity(), input_email_ref4.getText().toString().trim());
                        AppPreferences.setReferenceFriendEmail1(getActivity(), input_email_ref5.getText().toString().trim());
                        AppPreferences.setReferenceFriendEmail2(getActivity(), input_email_ref6.getText().toString().trim());

                        AppPreferences.setReferenceRelation1(getActivity(), input_relation_ref1.getText().toString().trim());
                        AppPreferences.setReferenceRelation2(getActivity(), input_relation_ref2.getText().toString().trim());
                        AppPreferences.setAdditionalReferenceRelation1(getActivity(), input_relation_ref3.getText().toString().trim());
                        AppPreferences.setAdditionalReferenceRelation2(getActivity(), input_relation_ref4.getText().toString().trim());
                        AppPreferences.setReferenceFriendRelation1(getActivity(), input_relation_ref5.getText().toString().trim());
                        AppPreferences.setReferenceFriendRelation2(getActivity(), input_relation_ref6.getText().toString().trim());

                        Snackbar.make(getActivity().findViewById(android.R.id.content),"Data Updated Successfully !" ,Snackbar.LENGTH_LONG).show();

                    } else if(jsonObject.get("status").equals("400")) {

                        Snackbar.make(getActivity().findViewById(android.R.id.content),"Some error occurs !" ,Snackbar.LENGTH_LONG).show();

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

    private void addReferenceDetails(JsonArray referenceDetails) {

        JsonObject jsonObject_ref1 = new JsonObject();
        jsonObject_ref1.addProperty("name", input_name_ref1.getText().toString().trim());
        jsonObject_ref1.addProperty("email", input_email_ref1.getText().toString().trim() );
        jsonObject_ref1.addProperty("phone", input_phoneno_ref1.getText().toString().trim());
        jsonObject_ref1.addProperty("relation_to_me", input_relation_ref1.getText().toString().trim());
        jsonObject_ref1.addProperty("reference_key", "reference_1");

        JsonObject jsonObject_ref2 = new JsonObject();
        jsonObject_ref2.addProperty("name", input_name_ref2.getText().toString().trim());
        jsonObject_ref2.addProperty("email", input_email_ref2.getText().toString().trim());
        jsonObject_ref2.addProperty("phone", input_phoneno_ref2.getText().toString().trim());
        jsonObject_ref2.addProperty("relation_to_me", input_relation_ref2.getText().toString().trim());
        jsonObject_ref2.addProperty("reference_key", "reference_2");

        JsonObject jsonObject_ref3 = new JsonObject();
        jsonObject_ref3.addProperty("name", input_name_ref3.getText().toString().trim());
        jsonObject_ref3.addProperty("email", input_email_ref3.getText().toString().trim());
        jsonObject_ref3.addProperty("phone", input_phoneno_ref3.getText().toString().trim());
        jsonObject_ref3.addProperty("relation_to_me", input_relation_ref3.getText().toString().trim());
        jsonObject_ref3.addProperty("reference_key", "additional_reference_1");

        JsonObject jsonObject_ref4 = new JsonObject();
        jsonObject_ref4.addProperty("name", input_name_ref4.getText().toString().trim());
        jsonObject_ref4.addProperty("email", input_email_ref4.getText().toString().trim());
        jsonObject_ref4.addProperty("phone", input_phoneno_ref4.getText().toString().trim());
        jsonObject_ref4.addProperty("relation_to_me", input_relation_ref4.getText().toString().trim());
        jsonObject_ref4.addProperty("reference_key", "additional_reference_2");

        JsonObject jsonObject_ref5 = new JsonObject();
        jsonObject_ref5.addProperty("name", input_name_ref5.getText().toString().trim());
        jsonObject_ref5.addProperty("email", input_email_ref5.getText().toString().trim());
        jsonObject_ref5.addProperty("phone", input_phoneno_ref5.getText().toString().trim());
        jsonObject_ref5.addProperty("relation_to_me", input_relation_ref5.getText().toString().trim());
        jsonObject_ref5.addProperty("reference_key", "reference_friend_1");

        JsonObject jsonObject_ref6 = new JsonObject();
        jsonObject_ref6.addProperty("name", input_name_ref6.getText().toString().trim());
        jsonObject_ref6.addProperty("email", input_email_ref6.getText().toString().trim());
        jsonObject_ref6.addProperty("phone", input_phoneno_ref6.getText().toString().trim());
        jsonObject_ref6.addProperty("relation_to_me", input_relation_ref6.getText().toString().trim());
        jsonObject_ref6.addProperty("reference_key", "reference_friend_2");

        referenceDetails.add(jsonObject_ref1);
        referenceDetails.add(jsonObject_ref2);
        referenceDetails.add(jsonObject_ref3);
        referenceDetails.add(jsonObject_ref4);
        referenceDetails.add(jsonObject_ref5);
        referenceDetails.add(jsonObject_ref6);

    }
}
