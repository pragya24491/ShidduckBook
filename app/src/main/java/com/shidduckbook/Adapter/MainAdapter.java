package com.shidduckbook.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.shidduckbook.Activity.LoginActivity;
import com.shidduckbook.Activity.UserDetailsActivity;
import com.shidduckbook.Model.HomePageModel;
import com.shidduckbook.R;
import com.shidduckbook.RetrofitClasses.ApiClient;
import com.shidduckbook.RetrofitClasses.MyApiEndpointInterface;
import com.shidduckbook.Util.AppConstant;
import com.shidduckbook.Util.AppPreferences;
import com.shidduckbook.Util.ProgressHUD;
import com.shidduckbook.helper.ItemTouchHelperViewHolder;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import atownsend.swipeopenhelper.BaseSwipeOpenViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Peter on 01-Jun-17.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private static final String TAG = "MainAdapter";
    private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec
    public static ArrayList<HomePageModel> searchList;
    Context context;
    ArrayList<HomePageModel> homeArrayList;
    int fav_status;
    View view;
    HashMap<HomePageModel, Runnable> pendingRunnables = new HashMap<>();
    private ButtonCallbacks callbacks;
    private List<HomePageModel> itemsPendingRemoval;
    private Handler handler = new Handler(); // hanlder for running delayed runnables

    public MainAdapter() {

    }

    public MainAdapter(Context context, ArrayList<HomePageModel> homeArrayList, ButtonCallbacks callbacks) {
        this.context = context;
        this.homeArrayList = homeArrayList;
        this.callbacks = callbacks;
        this.searchList = new ArrayList<>();
        this.searchList.addAll(homeArrayList);
        itemsPendingRemoval = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_content_main, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, callbacks);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return homeArrayList.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final HomePageModel homePageModel = homeArrayList.get(position);

        if (AppPreferences.getUserId(context).equalsIgnoreCase("")) {

            holder.nameTV.setText(homePageModel.getName());
            holder.ageTv.setText("Age : " + homePageModel.getAge());
            holder.ageTv.setVisibility(View.GONE);

            if (homePageModel.getCity().equalsIgnoreCase("0")) {
                holder.locationTV.setText("");
            } else {
                holder.locationTV.setText(/*"Location : " +*/homePageModel.getCity());
            }

            Log.v(TAG, "User id :- " + homePageModel.getUserId());


            if (homePageModel.getFav_status().equalsIgnoreCase("1")) {
                holder.starFilledImageView.setVisibility(View.VISIBLE);
                holder.starImageView.setVisibility(View.GONE);
            } else {
                holder.starFilledImageView.setVisibility(View.GONE);
                holder.starImageView.setVisibility(View.VISIBLE);
            }
        } else {
            if (!homePageModel.getArchive_status().equalsIgnoreCase("1")) {
                holder.nameTV.setText(homePageModel.getName());
                holder.ageTv.setText("Age : " + homePageModel.getAge());
                holder.ageTv.setVisibility(View.GONE);

                if (homePageModel.getCity().equalsIgnoreCase("0")) {
                    holder.locationTV.setText("");
                } else {
                    holder.locationTV.setText(/*"Location : " +*/homePageModel.getCity());
                }

                Log.v(TAG, "User id :- " + homePageModel.getUserId());

                if (itemsPendingRemoval.contains(homePageModel)) {
                    /** {show swipe layout} and {hide regular layout} */
                    holder.linearLayout_main.setVisibility(View.GONE);
                    holder.undo_btn.setVisibility(View.VISIBLE);
                    holder.undo_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            undoOpt(homePageModel);
                        }
                    });

                } else {
                    /** {show regular layout} and {hide swipe layout} */
                    holder.linearLayout_main.setVisibility(View.VISIBLE);
                    holder.archive_btn.setVisibility(View.GONE);
                    holder.undo_btn.setVisibility(View.GONE);
                    holder.nameTV.setText(homePageModel.getName());
                    holder.locationTV.setText(/*"Location : " +*/homePageModel.getCity());
                }


                if (!homePageModel.getProfilepic().equalsIgnoreCase("")) {

                    Picasso.with(context).load(homePageModel.getProfilepic())
                            .placeholder(R.drawable.pro_icon)
                            .error(R.drawable.pro_icon)
                            .resize(200, 200)
                            .into(holder.imageView);
                }

                if (homePageModel.getFav_status().equalsIgnoreCase("1")) {
                    holder.starFilledImageView.setVisibility(View.VISIBLE);
                    holder.starImageView.setVisibility(View.GONE);
                } else {
                    holder.starFilledImageView.setVisibility(View.GONE);
                    holder.starImageView.setVisibility(View.VISIBLE);
                }
            } else {

                holder.linearLayout_main.setVisibility(View.GONE);
                holder.linearLayout_undo.setVisibility(View.GONE);
            }
        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AppPreferences.getUserId(context).equalsIgnoreCase("")) {
                    loginDialog();
                } else {
                    Intent intent = new Intent(context, UserDetailsActivity.class);
                    intent.setAction("MainAdapter");
                    intent.putExtra("name", homeArrayList.get(position).getName());
                    intent.putExtra("age", homeArrayList.get(position).getAge());
                    intent.putExtra("city", homeArrayList.get(position).getCity());
                    intent.putExtra("profilepic", homeArrayList.get(position).getProfilepic());
                    intent.putExtra("user_id", homeArrayList.get(position).getUserId());
                    intent.putExtra("id", homeArrayList.get(position).getId());
                    intent.putExtra("favourite_status", homeArrayList.get(position).getFav_status());
                    context.startActivity(intent);
                }
            }
        });

        holder.starImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AppPreferences.getUserId(context).equalsIgnoreCase("")) {
                    loginDialog();
                } else {
                    holder.starImageView.setVisibility(View.GONE);
                    holder.starFilledImageView.setVisibility(View.VISIBLE);
                    fav_status = 1;
                }
                addFavourite(fav_status, position, view);
            }
        });

        holder.starFilledImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AppPreferences.getUserId(context).equalsIgnoreCase("")) {
                    loginDialog();
                } else {

                    holder.starFilledImageView.setVisibility(View.GONE);
                    holder.starImageView.setVisibility(View.VISIBLE);
                    fav_status = 0;
                }
                addFavourite(fav_status, position, view);
            }
        });

        holder.downArrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppPreferences.getUserId(context).equalsIgnoreCase("")) {
                    loginDialog();
                } else {
                    holder.downArrowImageView.setVisibility(View.GONE);
                    holder.upArrowImageView.setVisibility(View.VISIBLE);
                    holder.hideLinearLayout.setVisibility(View.VISIBLE);
//                    holder.notesTitleTextView.setText(homeArrayList.get(position).getName());
                }
            }
        });

        holder.upArrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppPreferences.getUserId(context).equalsIgnoreCase("")) {

                    loginDialog();

                } else {
                    holder.downArrowImageView.setVisibility(View.VISIBLE);
                    holder.upArrowImageView.setVisibility(View.GONE);
                    holder.hideLinearLayout.setVisibility(View.GONE);
                }
            }
        });

        holder.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(AppConstant.KEY_METHOD, AppConstant.METHOD.M_ADD_NOTES);
                jsonObject.addProperty(AppConstant.KEY_USER_ID, AppPreferences.getUserId(context));
                jsonObject.addProperty(AppConstant.KEY_MATCH_USER_ID, homeArrayList.get(position).getUserId());
                jsonObject.addProperty("notes_description", holder.noteEditText.getText().toString().trim());

                Log.v(TAG, "Json Request Add Notes:-" + jsonObject.toString());
                addNotesTask(jsonObject, view);
            }
        });

    }

    private void undoOpt(HomePageModel homePageModel) {
        Runnable pendingRemovalRunnable = pendingRunnables.get(homePageModel);
        pendingRunnables.remove(homePageModel);
        if (pendingRemovalRunnable != null)
            handler.removeCallbacks(pendingRemovalRunnable);
        itemsPendingRemoval.remove(homePageModel);
        // this will rebind the row in "normal" state
        notifyItemChanged(homeArrayList.indexOf(homePageModel));
    }


    public void clear() {
        homeArrayList.clear();
        notifyDataSetChanged();
    }

    public void pendingRemoval(int position) {

        final HomePageModel data = homeArrayList.get(position);
        if (!itemsPendingRemoval.contains(data)) {
            itemsPendingRemoval.add(data);
            // this will redraw row in "undo" state
            notifyItemChanged(position);
            // let's create, store and post a runnable to remove the data
            Runnable pendingRemovalRunnable = new Runnable() {
                @Override
                public void run() {
                    remove(homeArrayList.indexOf(data));
                }
            };
            handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT);
            pendingRunnables.put(data, pendingRemovalRunnable);
        }
    }

    public void remove(int position) {
        HomePageModel data = homeArrayList.get(position);
        if (itemsPendingRemoval.contains(data)) {
            itemsPendingRemoval.remove(data);
        }
        if (homeArrayList.contains(data)) {

            if (AppPreferences.getUserId(context).equalsIgnoreCase("")) {

                loginDialog();

            } else {
                addArchive(position);
                homeArrayList.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    public boolean isPendingRemoval(int position) {
        HomePageModel data = homeArrayList.get(position);
        return itemsPendingRemoval.contains(data);
    }

    private void addFavourite(int fav_status, int position, View view) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(AppConstant.KEY_METHOD, AppConstant.METHOD.M_ADD_FAVOURITE);
        jsonObject.addProperty(AppConstant.KEY_USER_ID, AppPreferences.getUserId(context));
        jsonObject.addProperty(AppConstant.KEY_FAVOURITE_USER_ID, homeArrayList.get(position).getId());
        jsonObject.addProperty("status", fav_status);

        Log.v(TAG, "Json Request My Favourite :-" + jsonObject.toString());

        addFavoriteTask(jsonObject, view);
    }

    private void addFavoriteTask(JsonObject jsonObject, final View view) {


        final ProgressHUD progressHD = ProgressHUD.show(context, "Please wait...", true, false, new DialogInterface.OnCancelListener() {
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

                Log.e(TAG, "Json Response Favorite :- " + response.body().toString());
                String s = response.body().toString();
                try {
                    JSONObject jsonObject = new JSONObject(s);

                    JSONArray resultArray = jsonObject.getJSONArray("result");
                    if (jsonObject.getString("status").equalsIgnoreCase("200")) {

                        for (int i = 0; i < resultArray.length(); i++) {
                            JSONObject jsonObject1 = resultArray.getJSONObject(i);

                            String status = jsonObject1.getString("status");
                            if (status.equalsIgnoreCase("1")) {

//                                Snackbar.make(view.findViewById(android.R.id.content), "Favorite Added", Snackbar.LENGTH_SHORT).show();
                                Toast.makeText(context.getApplicationContext(), "Favorite Added !", Toast.LENGTH_SHORT).show();
                            } else {
//                                Snackbar.make(view.findViewById(android.R.id.content), "Favorite Removed", Snackbar.LENGTH_SHORT).show();
                                Toast.makeText(context.getApplicationContext(), "Favorite Removed !", Toast.LENGTH_SHORT).show();
                            }
                        }

                    } else if (jsonObject.get("status").equals("400")) {

                        String msg = jsonObject.getString("message");
                        Toast.makeText(context.getApplicationContext(), "Some Error occured !", Toast.LENGTH_SHORT).show();
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

    private void addNotesTask(JsonObject jsonObject, View view) {


        final ProgressHUD progressHD = ProgressHUD.show(context, "Please wait...", true, false, new DialogInterface.OnCancelListener() {
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

                Log.e(TAG, "Json Response Add Notes :- " + response.body().toString());
                String s = response.body().toString();
                try {
                    JSONObject jsonObject = new JSONObject(s);

                    if (jsonObject.getString("status").equalsIgnoreCase("200")) {

                        Toast.makeText(context.getApplicationContext(), "Added to My Notes !", Toast.LENGTH_SHORT).show();

                    } else if (jsonObject.get("status").equals("400")) {

                        String msg = jsonObject.getString("message");
                        Toast.makeText(context.getApplicationContext(), "Some Error occured !", Toast.LENGTH_SHORT).show();
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

    // Filter Class
    public void filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());
        Log.v(TAG, "Char Text in Main adapter :- " + charText);
        if (homeArrayList != null)
            homeArrayList.clear();
        if (charText.length() == 0) {
            homeArrayList.addAll(searchList);
        } else {
            for (HomePageModel wp : searchList) {

                if (wp.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    String name = wp.getName();
                    Log.v(TAG, "By Nameeeeeeeeeeee  :- " + name);
                    homeArrayList.add(wp);

                }/* else if (wp.getCity().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    String city = wp.getCity();
                    Log.v(TAG, "By Location  :- " + city);
                    homeArrayList.add(wp);
                }*/
            }
        }
        notifyDataSetChanged();
    }

    public void loginDialog() {

        final Dialog markerDialog = new Dialog(context);
        markerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = markerDialog.getWindow();
//        window.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corner_dialog));
        window.setGravity(Gravity.CENTER);

        markerDialog.setContentView(R.layout.logout_popup);
        final Button btn_done = (Button) markerDialog.findViewById(R.id.done_btn);
        final Button btn_cancel = (Button) markerDialog.findViewById(R.id.cancel_btn);

        final TextView text2 = (TextView) markerDialog.findViewById(R.id.texttwo);
        text2.setText("You are not logged in. Please Login !!!");
        //String[] ITEMS = getResources().getStringArray(R.array.country);

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                btn_cancel.setBackgroundResource(R.drawable.buttonshape);
//                btn_done.setBackgroundResource(R.drawable.buttonshape_two);

                Intent intent = new Intent(context, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                btn_cancel.setBackgroundResource(R.drawable.buttonshape_two);
//                btn_done.setBackgroundResource(R.drawable.buttonshape);
                markerDialog.dismiss();
            }
        });

        markerDialog.setCanceledOnTouchOutside(false);
        markerDialog.show();
    }

    private void addArchive(int adapterPosition) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(AppConstant.KEY_METHOD, AppConstant.METHOD.ADD_ARCHIVE);
        jsonObject.addProperty(AppConstant.KEY_USER_ID, AppPreferences.getUserId(context));
        jsonObject.addProperty(AppConstant.KEY_ARCHIVE_USER_ID, homeArrayList.get(adapterPosition).getUserId());
        Log.e(TAG, "Json Request Archive :- " + jsonObject);
        addArchiveTask(jsonObject);
    }

    private void addArchiveTask(JsonObject jsonObject) {

        final ProgressHUD progressHD = ProgressHUD.show(context, "Please wait...", true, false, new DialogInterface.OnCancelListener() {
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

                Log.e(TAG, "Json Response Archive :- " + response.body().toString());
                String s = response.body().toString();
                try {
                    JSONObject jsonObject = new JSONObject(s);

                    if (jsonObject.getString("status").equalsIgnoreCase("200")) {
                        JSONArray resultArray = jsonObject.getJSONArray("result");

                        Toast.makeText(context, "Added to Archive !", Toast.LENGTH_SHORT).show();

                    } else if (jsonObject.get("status").equals("400")) {

                        String msg = jsonObject.getString("message");
                        Toast.makeText(context.getApplicationContext(), "Some Error occured !", Toast.LENGTH_SHORT).show();
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

    public interface ButtonCallbacks {
        void removePosition(int position);

        void editPosition(int position);
    }

    public class ViewHolder extends BaseSwipeOpenViewHolder implements
            ItemTouchHelperViewHolder {
        public LinearLayout contentView;
        CircleImageView imageView;
        ImageView starImageView, downArrowImageView, starFilledImageView, upArrowImageView;
        Button saveButton;
        TextView nameTV, ageTv, locationTV, archive_btn, undo_btn;
        LinearLayout linearLayout, hideLinearLayout, linearLayout_main, linearLayout_undo;
        EditText noteEditText;

        public ViewHolder(View itemView, final ButtonCallbacks callbacks) {
            super(itemView);

            contentView = (LinearLayout) view.findViewById(R.id.content_view);
            archive_btn = (TextView) itemView.findViewById(R.id.archive_btn);
            undo_btn = (TextView) itemView.findViewById(R.id.undo_btn);

            saveButton = (Button) itemView.findViewById(R.id.saveButton);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
            hideLinearLayout = (LinearLayout) itemView.findViewById(R.id.hideLinearLayout);
            linearLayout_main = (LinearLayout) itemView.findViewById(R.id.linearLayout_main);
            linearLayout_undo = (LinearLayout) itemView.findViewById(R.id.linearLayout_undo);

            nameTV = (TextView) itemView.findViewById(R.id.nameTV);
            ageTv = (TextView) itemView.findViewById(R.id.ageTv);
            locationTV = (TextView) itemView.findViewById(R.id.locationTV);

            imageView = (CircleImageView) itemView.findViewById(R.id.imageprofilepic);
            starImageView = (ImageView) itemView.findViewById(R.id.starImageView);
            starFilledImageView = (ImageView) itemView.findViewById(R.id.starFilledImageView);
            downArrowImageView = (ImageView) itemView.findViewById(R.id.downArrowImageView);
            upArrowImageView = (ImageView) itemView.findViewById(R.id.upArrowImageView);
            noteEditText = (EditText) itemView.findViewById(R.id.noteEditText);
        }

        @Override
        public void onItemSelected() {
//            itemView.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(context.getResources().getColor(R.color.color_white));
        }

        @NonNull
        @Override
        public View getSwipeView() {
            return contentView;
        }

        @Override
        public float getEndHiddenViewSize() {
            return archive_btn.getMeasuredWidth();
        }

        @Override
        public float getStartHiddenViewSize() {
            return 0;
        }

        @Override
        public void notifyStartOpen() {

        }

        @Override
        public void notifyEndOpen() {
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.color_white));
        }
    }
}

