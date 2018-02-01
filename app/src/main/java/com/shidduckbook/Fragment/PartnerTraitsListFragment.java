package com.shidduckbook.Fragment;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.shidduckbook.Adapter.MyDragDropAdapter;
import com.shidduckbook.R;
import com.shidduckbook.RetrofitClasses.ApiClient;
import com.shidduckbook.RetrofitClasses.MyApiEndpointInterface;
import com.shidduckbook.Util.AppConstant;
import com.shidduckbook.Util.AppPreferences;
import com.shidduckbook.Util.ProgressHUD;
import com.shidduckbook.helper.OnStartDragListener;
import com.shidduckbook.helper.SimpleItemTouchHelperCallback;

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

public class PartnerTraitsListFragment extends RootFragment implements OnStartDragListener {

    private static final String TAG = "PersonalTraits";
    ArrayList<String> stringArrayList;

    private RecyclerView mRecyclerView;
    private ItemTouchHelper mItemTouchHelper;
    MyDragDropAdapter dragDropAdapter;

    private JsonArray partner_preference;
    ArrayList<String> list;
    View rootView;

    public PartnerTraitsListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_partner_traits_list, container, false);

        stringArrayList = new ArrayList<>();

        Bundle bundle = getArguments();
        if (bundle != null){
            stringArrayList = bundle.getStringArrayList("traits_list");
            for (String s :stringArrayList) {
                Log.v(TAG, "List :- "+ s);
            }
        }
        setAdapter(stringArrayList);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                list = dragDropAdapter.getPrefernceList();
                partner_preference = new JsonArray();

                for (int i = 0; i < list.size(); i++) {
                    int pref = i + 1;

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("preference_number", pref);
                    jsonObject.addProperty("traits_name", list.get(i));
                    partner_preference.add(jsonObject);

                    Log.v(TAG, "Json object partner_preference :- " + jsonObject);
                    Log.v(TAG, "Json Array partner_preference :- " + partner_preference);
                }

               /* {"method":"add_personal_preference_traits","user_id":"2", "partner_preference": [{"preference_number": "1",
                        "traits_name": "shy"},{"preference_number": "2", "traits_name": "friendly"}]} */

                JsonObject mainJsonObject = new JsonObject();
                mainJsonObject.addProperty(AppConstant.KEY_METHOD, AppConstant.METHOD.ADD_PARTNER_PREFERENCE_TRAITS);
                mainJsonObject.addProperty(AppConstant.KEY_USER_ID, AppPreferences.getUserId(getActivity()));
                mainJsonObject.add("partner_preference", partner_preference);
                Log.v(TAG, "Request Json Personal Preference :- " + mainJsonObject);

                addPartnerPrefTask(mainJsonObject);

            }
        });

        return rootView;
    }

    private void addPartnerPrefTask(JsonObject mainJsonObject) {

        final ProgressHUD progressHD = ProgressHUD.show(getActivity(), "Please wait...", true, false, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // TODO Auto-generated method stub
            }
        });

        MyApiEndpointInterface apiService =
                ApiClient.getClient().create(MyApiEndpointInterface.class);
        Call<JsonObject> call = apiService.register_logs_api(mainJsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.e(TAG, "Response Json Personal Preference :- " + response.body().toString());
                String s = response.body().toString();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("status").equalsIgnoreCase("200")) {

                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        AppPreferences.setPartnerPreferenceStatus(getActivity(), "1");

                        Snackbar.make(getActivity().findViewById(android.R.id.content),"Prefrences Updated Successfully !" ,Snackbar.LENGTH_LONG).show();

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

    private void setAdapter(ArrayList<String> stringArrayList) {

        for (String s :stringArrayList) {
            Log.v(TAG, "List inside :- "+ s);
        }

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        dragDropAdapter = new MyDragDropAdapter(getActivity(), stringArrayList, this, new MyDragDropAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int view, int position) {

            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(dragDropAdapter);
        dragDropAdapter.notifyDataSetChanged();

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(dragDropAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

}
