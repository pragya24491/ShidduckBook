package com.shidduckbook.Fragment;


import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.shidduckbook.Activity.HomeActivity;
import com.shidduckbook.Adapter.MainAdapter;
import com.shidduckbook.Model.HomePageModel;
import com.shidduckbook.R;
import com.shidduckbook.RetrofitClasses.ApiClient;
import com.shidduckbook.RetrofitClasses.MyApiEndpointInterface;
import com.shidduckbook.Util.AppConstant;
import com.shidduckbook.Util.AppPreferences;
import com.shidduckbook.Util.DividerItemDecoration;
import com.shidduckbook.Util.Function;
import com.shidduckbook.Util.ProgressHUD;
import com.shidduckbook.Util.SwipeUtil;
import com.shidduckbook.helper.OnStartDragListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements OnStartDragListener, MainAdapter.ButtonCallbacks, SearchView.OnQueryTextListener {

    private static final String TAG = "MainFragment";
    ImageView addnewmember;
    MainAdapter adapter;
    ArrayList<HomePageModel> homeArrayList = new ArrayList<>();
    HomePageModel homeModel;
    String gender;
    EditText editText;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView.ItemDecoration itemDecoration;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ImageView filterImageView;
    private TextView ageTextView, locationTextView, backgroundTextView, statusTextView;
    private ItemTouchHelper mItemTouchHelper;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        setHasOptionsMenu(true);
        initViews(rootView);

        if (getArguments() != null) {
            gender = getArguments().getString("gender");
            Log.v(TAG, "Home page :- " + gender);
        }
        if (AppPreferences.getUserId(getActivity()).equalsIgnoreCase("")) {
//        if (HomeActivity.homePage == true) {
            Log.v(TAG, "Inside Home page !!!");
            mainPageApiCalling(gender, AppConstant.METHOD.M_HOMEPAGE);
            HomeActivity.homePage = false;
        } else if (HomeActivity.homePage == false && !AppPreferences.getUserId(getActivity()).equalsIgnoreCase("")) {
            Log.v(TAG, "Inside login !!!");
            mainPageApiCalling(AppPreferences.getGender(getActivity()), AppConstant.METHOD.M_LOGINHOMEPAGE);
        }


        return rootView;
    }

    private void initViews(View rootView) {

        itemDecoration = new
                DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);


        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

//        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);

        addnewmember = (ImageView) rootView.findViewById(R.id.addnewmember);

        filterImageView = (ImageView) rootView.findViewById(R.id.filterImageView);
        ageTextView = (TextView) rootView.findViewById(R.id.ageTextView);
        locationTextView = (TextView) rootView.findViewById(R.id.locationTextView);
        backgroundTextView = (TextView) rootView.findViewById(R.id.backgroundTextView);
        statusTextView = (TextView) rootView.findViewById(R.id.statusTextView);

        filterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(), "Filter clicked !!!",Toast.LENGTH_SHORT).show();
            }
        });

        ageTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(), "Age clicked !!!",Toast.LENGTH_SHORT).show();
            }
        });

        locationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(), "Location clicked !!!",Toast.LENGTH_SHORT).show();
            }
        });

        backgroundTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(), "Background clicked !!!",Toast.LENGTH_SHORT).show();
            }
        });

        statusTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(), "Status clicked !!!", Toast.LENGTH_SHORT).show();
            }
        });
/*
        addnewmember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
*/


    }

    private void mainPageApiCalling(String gender, String method_name) {

        Log.v(TAG, "Gender :- " + gender);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(AppConstant.KEY_METHOD, method_name);
        jsonObject.addProperty(AppConstant.KEY_USER_ID, AppPreferences.getUserId(getActivity()));
        jsonObject.addProperty("gender", gender);

        if (method_name.equalsIgnoreCase(AppConstant.METHOD.M_LOGINHOMEPAGE)) {
            Log.v(TAG, "Json Request Home Page :- " + jsonObject.toString());
            if (AppPreferences.getPartnerPreferenceStatus(getActivity()).equalsIgnoreCase(""))
                jsonObject.addProperty("partner_preference_status", "1");
            else
                jsonObject.addProperty("partner_preference_status", AppPreferences.getPartnerPreferenceStatus(getActivity()));
        }

        Log.v(TAG, "Json Request Home Page :- " + jsonObject.toString());

        if (Function.isConnectingToInternet(getActivity())) {
            homePageTask(jsonObject);
        } else {
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Internet not connected !", Snackbar.LENGTH_LONG).show();
        }
    }

    private void homePageTask(JsonObject jsonObject) {


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

                Log.v(TAG, "Json Response Home Page :- " + response.body().toString());
                String s = response.body().toString();
                try {
                    JSONObject jsonObject = new JSONObject(s);

                    if (jsonObject.getString("status").equalsIgnoreCase("200")) {

                        JSONArray resultArray = jsonObject.getJSONArray("result");
                        ArrayList<Integer> list = new ArrayList<Integer>();

                        for (int i = 0; i < resultArray.length(); i++) {

                            homeModel = new HomePageModel();
                            JSONObject jsonObject1 = resultArray.getJSONObject(i);

                            String name = jsonObject1.getString("name");
                            String location = jsonObject1.getString("location");
                            String age = jsonObject1.getString("age");
                            String profile_pic = jsonObject1.getString("profile_pic");
                            String userId = jsonObject1.getString("user_id");
                            String status = jsonObject1.getString("status");
                            String favourite_status = jsonObject1.getString("favourite_status");
                            String id = jsonObject1.getString("id");
                            String archive_status = jsonObject1.getString("archive_status");
                            String num_Per = jsonObject1.getString("num_Per");


                            homeModel.setName(name);
                            homeModel.setCity(location);
                            homeModel.setAge(age);
                            homeModel.setProfilepic(profile_pic);
                            homeModel.setUserId(userId);
                            homeModel.setStatus(status);
                            homeModel.setFav_status(favourite_status);
                            homeModel.setId(id);
                            homeModel.setArchive_status(archive_status);
                            homeModel.setNum_Per(Integer.parseInt(num_Per));

                            homeArrayList.add(homeModel);
                        }

                        Log.v(TAG, "Before sort :- " + homeArrayList.toString());
                        Collections.sort(homeArrayList, new Comparator<HomePageModel>() {
                            @Override
                            public int compare(HomePageModel num_Per1, HomePageModel num_Per2) {
                                return num_Per2.getNum_Per() - num_Per1.getNum_Per();
                            }
                        });

                        Log.v(TAG, "After sort :- " + homeArrayList.toString());
                       /* if (AppPreferences.getUserId(getActivity()).equalsIgnoreCase("")) {*/

                        JSONArray resultArray_other = jsonObject.getJSONArray("result_other");
                        Log.v(TAG, "Result Array :- " + resultArray_other.toString());

                        Log.v(TAG, "Result Array is empty :- " + resultArray_other.toString().isEmpty());

                        if (resultArray_other.length() != 0) {
                            for (int i = 0; i < resultArray_other.length(); i++) {
                                homeModel = new HomePageModel();

                                JSONObject jsonObject1 = resultArray_other.getJSONObject(i);


                                String id = jsonObject1.getString("id");
                                String name = jsonObject1.getString("name");
                                String location = jsonObject1.getString("location");
                                String age = jsonObject1.getString("age");
                                String profile_pic = jsonObject1.getString("profile_pic");
                                String userId = jsonObject1.getString("user_id");
                                String status = jsonObject1.getString("status");
                                String favourite_status = jsonObject1.getString("favourite_status");
                                String archive_status = jsonObject1.getString("archive_status");

                                homeModel.setName(name);
                                homeModel.setCity(location);
                                homeModel.setAge(age);
                                homeModel.setProfilepic(profile_pic);
                                homeModel.setUserId(userId);
                                homeModel.setStatus(status);
                                homeModel.setFav_status(favourite_status);
                                homeModel.setId(id);
                                homeModel.setArchive_status(archive_status);

                                homeArrayList.add(homeModel);
                            }
                        }
//                        }


                        setAdapter(homeArrayList);

                    } else if (jsonObject.get("status").equals("400")) {

                        String msg = jsonObject.getString("message");
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Some Error occured !", Snackbar.LENGTH_LONG).show();
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.main, menu);

        // Pull out the SearchView
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setVisible(true);

        final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) searchItem.getActionView();
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        SearchView.SearchAutoComplete searchEditText = (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
//        searchEditText.setHintTextColor(Color.WHITE);
//        searchEditText.setHintTextColor(getResources().getColor(R.color.white));
/*
        EditText view = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_edit_frame);
        String text = view.getText().toString();
        Log.v(TAG, "Text fjddfidbfd :- " + text);*/


        ImageView searchMagIcon = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
        searchMagIcon.setImageResource(R.drawable.ic_arrow_back_white_24dp);

        ImageView searchCloseIcon = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        searchCloseIcon.setImageResource(R.drawable.ic_close_white_24dp);

        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        //*** setOnQueryTextListener ***
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                String value = searchView.getQuery().toString().toLowerCase(Locale.getDefault());
                Log.v("Main Activity", "search Text :- " + value);
                Log.v("Main ", "search Text :- " + newText);
//                adapter.filter(value.toLowerCase());
                adapter.filter(newText);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_share) {

            String shareBody = "Download Shidduch Book app to search your partner !!! ";

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/html");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share using"));

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();

        adapter = new MainAdapter(getActivity(), homeArrayList, this);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setAdapter(adapter);

    }

    private void setAdapter(ArrayList<HomePageModel> homeArrayList) {

        SwipeUtil swipeHelper = new SwipeUtil(0, ItemTouchHelper.LEFT, getActivity()) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int swipedPosition = viewHolder.getAdapterPosition();
                MainAdapter adapter = (MainAdapter) mRecyclerView.getAdapter();
                adapter.pendingRemoval(swipedPosition);
            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                MainAdapter adapter = (MainAdapter) mRecyclerView.getAdapter();
                if (adapter.isPendingRemoval(position)) {
                    return 0;
                }
                return super.getSwipeDirs(recyclerView, viewHolder);
            }
        };

        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(swipeHelper);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        //set swipe label
        swipeHelper.setLeftSwipeLable("Archive");
        //set swipe background-Color
        swipeHelper.setLeftcolorCode(ContextCompat.getColor(getActivity(), R.color.colorAccent));


       /* adapter = new MainAdapter(getActivity(), homeArrayList, this);

        SwipeOpenItemTouchHelper helper = new SwipeOpenItemTouchHelper(new SwipeOpenItemTouchHelper.SimpleCallback(
                SwipeOpenItemTouchHelper.START | SwipeOpenItemTouchHelper.END));


//        helper.attachToRecyclerView(mRecyclerView);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setAdapter(adapter); */

        /*  mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                refreshContent();
            }
        });*/

       /*

        helper.attachToRecyclerView(mRecyclerView);

        helper.setCloseOnAction(false);
        adapter.notifyDataSetChanged();*/

    }

    private void refreshContent() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                if (HomeActivity.homePage == true) {
                    mainPageApiCalling(gender, AppConstant.METHOD.M_HOMEPAGE);
                    HomeActivity.homePage = false;
                } else if (HomeActivity.homePage == false && !AppPreferences.getUserId(getActivity()).equalsIgnoreCase("")) {
                    Log.v(TAG, "Inside login !!!");
                    mainPageApiCalling(AppPreferences.getGender(getActivity()), AppConstant.METHOD.M_LOGINHOMEPAGE);
                }
                mSwipeRefreshLayout.setRefreshing(false);

            }
        }, 3000);
//        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.v(TAG, "inside onQueryTextSubmit -- " + query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.v(TAG, "inside onQueryTextChange -- " + newText);
        adapter.filter(newText);
        return true;
    }
}
