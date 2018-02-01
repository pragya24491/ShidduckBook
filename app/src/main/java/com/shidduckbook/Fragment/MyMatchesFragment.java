package com.shidduckbook.Fragment;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.shidduckbook.Adapter.MatchesAdapter;
import com.shidduckbook.Model.HomePageModel;
import com.shidduckbook.R;
import com.shidduckbook.RetrofitClasses.ApiClient;
import com.shidduckbook.RetrofitClasses.MyApiEndpointInterface;
import com.shidduckbook.Util.AppConstant;
import com.shidduckbook.Util.AppPreferences;
import com.shidduckbook.Util.DividerItemDecoration;
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
public class MyMatchesFragment extends Fragment {

    private static final String TAG = "MatchesFragment";
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    MatchesAdapter adapter;
    View rootView;
    ArrayList<HomePageModel> arrayList = new ArrayList<>();
    HomePageModel homeModel;
    TextView textView;

    public MyMatchesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
        textView = (TextView) rootView.findViewById(R.id.textView);
        setHasOptionsMenu(false);
        myMatchesApi();
//        setAdapter(arrayList);
        return rootView;
    }

    private void myMatchesApi() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(AppConstant.KEY_METHOD, AppConstant.METHOD.M_MY_MATCHES);
        jsonObject.addProperty(AppConstant.KEY_USER_ID, AppPreferences.getUserId(getActivity()));
        Log.e(TAG, "Json Request Matches :- " + jsonObject);
        myMatchesTask(jsonObject);

    }

    private void myMatchesTask(JsonObject jsonObject) {

        final ProgressHUD progressHD = ProgressHUD.show(getActivity(), "Please wait...", true, false, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // TODO Auto-generated method stub
            }
        });

        MyApiEndpointInterface apiService =
                ApiClient.getClient().create(MyApiEndpointInterface.class);
        Call<JsonObject> call = apiService.my_matched_API(jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.e(TAG, "Json Response Matches :- " + response.body().toString());
                String s = response.body().toString();
                try {
                    JSONObject jsonObject = new JSONObject(s);

                    if (jsonObject.getString("status").equalsIgnoreCase("200")) {
                        JSONArray resultArray = jsonObject.getJSONArray("result");

                        for (int i = 0; i < resultArray.length(); i++) {
                            homeModel = new HomePageModel();

                            JSONObject jsonObject1 = resultArray.getJSONObject(i);

                            String name = jsonObject1.getString("name");
                            String location = jsonObject1.getString("location");
                            String age = jsonObject1.getString("age");
                            String profile_pic = jsonObject1.getString("profile_pic");
                            String userId = jsonObject1.getString("user_id");
//                            String status = jsonObject1.getString("status");

                            homeModel.setName(name);
                            homeModel.setCity(location);
                            homeModel.setAge(age);
                            homeModel.setProfilepic(profile_pic);
                            homeModel.setUserId(userId);
//                            homeModel.setStatus(status);

                            arrayList.add(homeModel);
                        }

                        if (! arrayList.isEmpty()) {

                            textView.setVisibility(View.GONE);
                            setAdapter(arrayList);
                        } else {
                            textView.setVisibility(View.VISIBLE);
                            textView.setText("You have no Matches yet");
                        }

                    } else if (jsonObject.get("status").equals("400")) {
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("You have no Matches yet");

                        String msg = jsonObject.getString("message");
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "No Matches yet !", Snackbar.LENGTH_LONG).show();

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
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Some error found !", Snackbar.LENGTH_LONG).show();
                progressHD.dismiss();
            }
        });
    }

    private void setAdapter(ArrayList<HomePageModel> homeArrayList) {

        adapter = new MatchesAdapter(getActivity(), homeArrayList);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
