package com.shidduckbook.Fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.gson.JsonObject;
import com.shidduckbook.Adapter.TraitsAdapter;
import com.shidduckbook.Model.HomePageModel;
import com.shidduckbook.R;
import com.shidduckbook.RetrofitClasses.ApiClient;
import com.shidduckbook.RetrofitClasses.MyApiEndpointInterface;
import com.shidduckbook.Util.AppConstant;
import com.shidduckbook.Util.AppPreferences;
import com.shidduckbook.Util.DividerItemDecoration;
import com.shidduckbook.Util.Function;
import com.shidduckbook.Util.ProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ABHI on 30-05-2017.
 */

public class Personal_Characterstics_Tab extends RootFragment {

    private static final String TAG = "Personal_Character_Tab";
    public static LinearLayout linearLayout;
    Button update_btn;
    ArrayList<String> traitsList;
    String checkBoxString;
    int checkedCount = 0;
    HomePageModel homePageModel;
    ArrayList<HomePageModel> allTraitsList;
    ArrayList<HomePageModel> selectedList = new ArrayList<>();
    private FragmentManager mFragmentManager;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private TraitsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_personal_characterstics, container, false);

        mFragmentManager = getActivity().getSupportFragmentManager();
        initViews(rootView);

        if (AppPreferences.getPartnerPreferenceStatus(getActivity()).equalsIgnoreCase("1")) {
            Log.v(TAG, "Inside Partner Preference");
//            selectedPersonalTraits();

            if (Function.isConnectingToInternet(getActivity())) {
                selectedPersonalTraits();
            } else {
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Internet not Connected !", Snackbar.LENGTH_LONG).show();
            }

        }
        if (Function.isConnectingToInternet(getActivity())) {
            apiCallingAllTraits();
        } else {
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Internet not Connected !", Snackbar.LENGTH_LONG).show();
        }


/*
        update_btn = (Button) rootView.findViewById(R.id.update_btn);

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNextFragment();

            }
        });*/

        return rootView;
    }

    private void initViews(View rootView) {

        linearLayout = (LinearLayout) rootView.findViewById(R.id.linearLayout);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        update_btn = (Button) rootView.findViewById(R.id.update_btn);

        traitsList = new ArrayList<>();

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkedCount = adapter.getSelectedCount();
                traitsList = adapter.getSelectedTraitsName();
                String traits = traitsList.toString();

                Log.v(TAG, "Checked count :- " + checkedCount);
                Log.v(TAG, "Traits list:- " + traits.replace("[", "").replace("]", ""));

                if (checkedCount < 10) {

                    Snackbar.make(getActivity().findViewById(android.R.id.content), "Please select atleast 10 traits !", Snackbar.LENGTH_LONG).show();

                } else {

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty(AppConstant.KEY_METHOD, AppConstant.METHOD.M_PERSONAL_TRAITS);
                    jsonObject.addProperty(AppConstant.KEY_USER_ID, AppPreferences.getUserId(getActivity()));
                    jsonObject.addProperty(AppConstant.KEY_PERSONAL_TRAITS, traits.replace("[", "").replace("]", ""));

                    Log.v(TAG, "Json Request :- " + jsonObject);
                    personalTraitsApi(jsonObject);
                }
            }
        });
    }

    private void selectedPersonalTraits() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(AppConstant.KEY_METHOD, AppConstant.METHOD.MY_PERSONAL_TRAITS);
        jsonObject.addProperty(AppConstant.KEY_USER_ID, AppPreferences.getUserId(getActivity()));
        Log.v(TAG, "Request Json My Personal Traits :- " + jsonObject);
        SelectedTraitsTask(jsonObject);
    }

    private void SelectedTraitsTask(JsonObject jsonObject) {

       /* final ProgressHUD progressHD = ProgressHUD.show(getActivity(), "Please wait...", true, false, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // TODO Auto-generated method stub
            }
        });*/

        MyApiEndpointInterface apiService =
                ApiClient.getClient().create(MyApiEndpointInterface.class);
        Call<JsonObject> call = apiService.user_notes_Api(jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.e(TAG, "Response Json user_notes_Api Personal Preference :- " + response.body().toString());
                String s = response.body().toString();
                try {
                    JSONObject jsonObject = new JSONObject(s);

                    if (jsonObject.getString("status").equalsIgnoreCase("200")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("result");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            homePageModel = new HomePageModel();

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String id = jsonObject1.getString("preference_number");
                            String name = jsonObject1.getString("traits_name");
                            homePageModel.setId(id);
                            homePageModel.setName(name);
                            homePageModel.setChckStatus(true);

                            selectedList.add(homePageModel);
                            Log.v(TAG, "Json selected Personal  traitsList :- " + selectedList.get(i).getName().toString());
                        }
//                        setAdapter(allTraitsList);

                    } else if (jsonObject.get("status").equals("400")) {

//                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Some error occurs !", Snackbar.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                progressHD.dismiss();
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "on Failure error :-" + Log.getStackTraceString(t));
//                progressHD.dismiss();
            }
        });
    }

    private void apiCallingAllTraits() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(AppConstant.KEY_METHOD, AppConstant.METHOD.ALL_PREFERENCE_TRAITS);
        Log.v(TAG, "Request Json Personal Preference :- " + jsonObject);

        getAllTraitsListTask(jsonObject);

    }

    private void getAllTraitsListTask(JsonObject jsonObject) {

        final ProgressHUD progressHD = ProgressHUD.show(getActivity(), "Please wait...", true, false, new DialogInterface.OnCancelListener() {
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

                Log.e(TAG, "Response Json Personal Preference :- " + response.body().toString());
                String s = response.body().toString();
                try {
                    allTraitsList = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(s);

                    if (jsonObject.getString("status").equalsIgnoreCase("200")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("result");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            homePageModel = new HomePageModel();

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String id = jsonObject1.getString("id");
                            String name = jsonObject1.getString("name");
                            homePageModel.setId(id);
                            homePageModel.setName(name);

                            allTraitsList.add(homePageModel);
//                            Log.v(TAG, "Json all traitsList :- " + allTraitsList.toString());
                        }
                        ArrayList<HomePageModel> getSelectedList = getCheckedTraitsDetails(allTraitsList);
                        setAdapter(getSelectedList);

                    } else if (jsonObject.get("status").equals("400")) {

                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Some error occurs !", Snackbar.LENGTH_LONG).show();

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

    private ArrayList<HomePageModel> getCheckedTraitsDetails(ArrayList<HomePageModel> allTraitsList) {

        if (selectedList != null) {

            for (int i = 0; i < allTraitsList.size(); i++) {
                String traits = allTraitsList.get(i).getName();
                for (int j = 0; j < selectedList.size(); j++) {
                    if (traits.equalsIgnoreCase(selectedList.get(j).getName())) {
                        Log.v(TAG, "Traits selected name :;- " + traits);
                        allTraitsList.get(i).setChckStatus(true);
                    }
                }
            }
            return allTraitsList;
        }
        return allTraitsList;
    }

    private void setAdapter(final ArrayList<HomePageModel> allTraitsList) {

        adapter = new TraitsAdapter(getActivity(), allTraitsList, selectedList, new TraitsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int view, int position, HomePageModel homePageModel) {

            }
        });

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void personalTraitsApi(JsonObject jsonObject) {

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
                    JSONArray result = jsonObject.getJSONArray("result");
                    if (jsonObject.getString("status").equalsIgnoreCase("200")) {

                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Data Updated Successfully !", Snackbar.LENGTH_SHORT).show();
                        callNextFragment(traitsList);

                    } else if (jsonObject.get("status").equals("400")) {

                        Snackbar.make(getActivity().findViewById(android.R.id.content), "User does not exist !", Snackbar.LENGTH_SHORT).show();
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

    private void callNextFragment(ArrayList<String> List) {
//        linearLayout.setVisibility(View.GONE);
/*
        PersonalTraitsListFragment personalTraitsListFragment = new PersonalTraitsListFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_mainLayout, personalTraitsListFragment);
        transaction.addToBackStack(null);
        transaction.commit();*/

        Log.v(TAG, "Traits List :- " + traitsList);

        RootFragment argumentFragment = new PersonalTraitsListFragment();
        Bundle data = new Bundle();//Use bundle to pass data
//        data.putSerializable("traits_list", traitsList);
        data.putStringArrayList("traits_list", traitsList);
        argumentFragment.setArguments(data);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        getActivity().getSupportFragmentManager().popBackStack();
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.replace(R.id.fragment_Layout, argumentFragment).commit();
    }

    private void callNextFragment() {

//        FragmentTransaction fragmentTransaction;
        RootFragment argumentFragment = new PersonalTraitsListFragment();
/*
        Bundle data = new Bundle();//Use bundle to pass data
//        data.putSerializable("traits_list", traitsList);
        data.putStringArrayList("traits_list", traitsList);
        argumentFragment.setArguments(data);*/

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        getActivity().getSupportFragmentManager().popBackStack();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragment_Layout, argumentFragment).commit();

    }

    @Override
    public boolean onBackPressed() {
        return super.onBackPressed();
    }
}