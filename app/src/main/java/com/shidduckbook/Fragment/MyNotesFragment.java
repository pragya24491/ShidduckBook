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
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.shidduckbook.Adapter.MyNotesAdapter;
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
public class MyNotesFragment extends Fragment implements OnStartDragListener, MyNotesAdapter.ButtonCallbacks {

    private static final String TAG = "MyNotesFragment";
    RecyclerView recyclerView;
    MyNotesAdapter myNotesAdapter;
    HomePageModel homeModel;
    ArrayList<HomePageModel> homePageModelArrayList = new ArrayList<>() ;
    View rootView;
    TextView textView;

    public MyNotesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(false);
        rootView = inflater.inflate(R.layout.fragment_my_notes, container, false);
        textView = (TextView) rootView.findViewById(R.id.textView);
        myNotesApi();
//        setAdapter(homePageModelArrayList);
        return rootView;
    }

    private void myNotesApi() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(AppConstant.KEY_METHOD, AppConstant.METHOD.M_MY_NOTES);
        jsonObject.addProperty(AppConstant.KEY_USER_ID, AppPreferences.getUserId(getActivity()));
        Log.e(TAG, "Json Request my_notes :- " + jsonObject);
        myNotesTask(jsonObject);
    }

    private void myNotesTask(JsonObject jsonObject) {

        final ProgressHUD progressHD = ProgressHUD.show(getActivity(), "Please wait...", true, false, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // TODO Auto-generated method stub
            }
        });

        MyApiEndpointInterface apiService =
                ApiClient.getClient().create(MyApiEndpointInterface.class);
            Call<JsonObject> call = apiService.user_notes_Api(jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                Log.e(TAG, "Json Response my_notes :- " + response.body().toString());
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

                            homePageModelArrayList.add(homeModel);
                        }

                        if (! homePageModelArrayList.isEmpty()) {

                            textView.setVisibility(View.GONE);
                            setAdapter(homePageModelArrayList);
                        } else {
                            textView.setVisibility(View.VISIBLE);
                            textView.setText("You have no Notes yet");
                        }

                    } else if (jsonObject.get("status").equals("400")) {

                        String msg = jsonObject.getString("message");
                        textView.setText("You have no Notes yet");
                        textView.setVisibility(View.VISIBLE);
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
                progressHD.dismiss();
            }
        });
    }

    private void setAdapter(ArrayList<HomePageModel> homePageModelArrayList) {

        myNotesAdapter = new MyNotesAdapter(getActivity(), homePageModelArrayList, this);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);


        SwipeOpenItemTouchHelper helper = new SwipeOpenItemTouchHelper(new SwipeOpenItemTouchHelper.SimpleCallback(
                SwipeOpenItemTouchHelper.START | SwipeOpenItemTouchHelper.END));

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(myNotesAdapter);

        helper.attachToRecyclerView(recyclerView);

        helper.setCloseOnAction(false);
        myNotesAdapter.notifyDataSetChanged();

       /* ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(myNotesAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
*/
    }

    private ItemTouchHelper mItemTouchHelper;

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {

    }

    @Override
    public void removePosition(int position) {

    }

    @Override
    public void editPosition(int position) {

    }
}
