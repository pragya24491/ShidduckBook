package com.shidduckbook.Fragment;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.shidduckbook.Adapter.ArchiveAdapter;
import com.shidduckbook.Model.HomePageModel;
import com.shidduckbook.R;
import com.shidduckbook.RetrofitClasses.ApiClient;
import com.shidduckbook.RetrofitClasses.MyApiEndpointInterface;
import com.shidduckbook.Util.AppConstant;
import com.shidduckbook.Util.AppPreferences;
import com.shidduckbook.Util.DividerItemDecoration;
import com.shidduckbook.Util.ProgressHUD;
import com.shidduckbook.helper.OnStartDragListener;
import com.shidduckbook.helper.SimpleItemTouchHelperCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import atownsend.swipeopenhelper.SwipeOpenItemTouchHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArchiveFragment extends Fragment implements OnStartDragListener, ArchiveAdapter.ButtonCallbacks {

    private static final String TAG = "ArchiveFragment";
    RecyclerView mRecyclerView;
    ArchiveAdapter archiveAdapter;
    HomePageModel homeModel;
    ArrayList<HomePageModel> archiveArrayList = new ArrayList<HomePageModel>();
    View rootView;
    private ItemTouchHelper mItemTouchHelper;

    public ArchiveFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(false);
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_archive, container, false);

        myArchiveApi();
//        setAdapter(archiveArrayList);
        return rootView;
    }

    private void myArchiveApi() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(AppConstant.KEY_METHOD, AppConstant.METHOD.M_ARCHIVE);
        jsonObject.addProperty(AppConstant.KEY_USER_ID, AppPreferences.getUserId(getActivity()));
        Log.e(TAG, "Json Request Archive List :- " + jsonObject);
        myArchiveTask(jsonObject);
    }

    private void myArchiveTask(JsonObject jsonObject) {

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

                Log.e(TAG, "Json Response Archive List :- " + response.body().toString());
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

                            archiveArrayList.add(homeModel);
                        }

                        setAdapter(archiveArrayList);

                    } else if (jsonObject.get("status").equals("400")) {

                        String msg = jsonObject.getString("message");
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "No Archive !", Snackbar.LENGTH_LONG).show();
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

    private void setAdapter(ArrayList<HomePageModel> homePageModelArrayList) {


        archiveAdapter = new ArchiveAdapter(getActivity(), homePageModelArrayList, this);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);


        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);

        SwipeOpenItemTouchHelper helper = new SwipeOpenItemTouchHelper(new SwipeOpenItemTouchHelper.SimpleCallback(
                SwipeOpenItemTouchHelper.START | SwipeOpenItemTouchHelper.END));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setAdapter(archiveAdapter);

        helper.attachToRecyclerView(mRecyclerView);

        helper.setCloseOnAction(false);
        archiveAdapter.notifyDataSetChanged();

      /*  ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(archiveAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);*/
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void removePosition(int position) {

    }

    @Override
    public void editPosition(int position) {

    }
}
