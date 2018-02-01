package com.shidduckbook.Adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.shidduckbook.Activity.UserDetailsActivity;
import com.shidduckbook.Model.HomePageModel;
import com.shidduckbook.R;
import com.shidduckbook.RetrofitClasses.ApiClient;
import com.shidduckbook.RetrofitClasses.MyApiEndpointInterface;
import com.shidduckbook.Util.AppConstant;
import com.shidduckbook.Util.AppPreferences;
import com.shidduckbook.Util.ProgressHUD;
import com.shidduckbook.helper.ItemTouchHelperAdapter;
import com.shidduckbook.helper.ItemTouchHelperViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import atownsend.swipeopenhelper.BaseSwipeOpenViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Peter on 05-Jun-17.
 */

public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    private static final String TAG = "CommonAdapter";
    Context context;
    ArrayList<HomePageModel> homeArrayList;
    int fav_status;
    private ButtonCallbacks callbacks;

    public CommonAdapter(Context context, ArrayList<HomePageModel> homeArrayList, ButtonCallbacks callbacks) {
        this.context = context;
        this.callbacks = callbacks;
        this.homeArrayList = homeArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_content_common, parent, false);
        ViewHolder viewHolder = new ViewHolder(v, callbacks);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        HomePageModel homePageModel = homeArrayList.get(position);

        holder.nameTV.setText(homePageModel.getName());
        holder.ageTv.setText(homePageModel.getAge());
        holder.ageTv.setVisibility(View.GONE);
        holder.locationTV.setText(homePageModel.getCity());

        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fav_status = 0;
                removeFavourite(fav_status, position);
                callbacks.removePosition(position);
            }
        });

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, UserDetailsActivity.class);
                intent.setAction("FavoriteAdapter");
                intent.putExtra("name", homeArrayList.get(position).getName());
                intent.putExtra("age", homeArrayList.get(position).getAge());
                intent.putExtra("city", homeArrayList.get(position).getCity());
                intent.putExtra("profilepic", homeArrayList.get(position).getProfilepic());
                intent.putExtra("user_id", homeArrayList.get(position).getUserId());
                intent.putExtra("id", homeArrayList.get(position).getId());
                intent.putExtra("favourite_status", homeArrayList.get(position).getFav_status());
                context.startActivity(intent);
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
                addNotesTask(jsonObject);
            }
        });

       /* if (! homePageModel.getProfilepic().equalsIgnoreCase("")){

            Picasso.with(context).load(AppPreferences.getUserImage(context))
                    .placeholder(R.drawable.pro_icon)
                    .error(R.drawable.pro_icon)
                    .resize(200, 200)
                    .into(holder.imageView);
        }*/
/*

        holder.nameTV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
        */

      /*  holder.downArrowImageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

//               homeArrayList.remove(position);
//               notifyDataSetChanged();
               fav_status = 0;
               removeFavourite(fav_status,position);
           }
       });
       */

    }

    @Override
    public int getItemCount() {
        return homeArrayList.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {

    }

    private void removeFavourite(int fav_status, int position) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(AppConstant.KEY_METHOD, AppConstant.METHOD.M_ADD_FAVOURITE);
        jsonObject.addProperty(AppConstant.KEY_USER_ID, AppPreferences.getUserId(context));
        jsonObject.addProperty(AppConstant.KEY_FAVOURITE_USER_ID, homeArrayList.get(position).getId());
        jsonObject.addProperty("status", fav_status);

        Log.v(TAG, "Json Request My Favourite :-" + jsonObject.toString());

        removeFavoriteTask(jsonObject, position);
    }

    private void removeFavoriteTask(JsonObject jsonObject, final int position) {

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

                        Toast.makeText(context.getApplicationContext(), "Removed from Favorite !", Toast.LENGTH_SHORT).show();
                        homeArrayList.remove(position);
                        notifyItemRemoved(position);


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

    private void addNotesTask(JsonObject jsonObject) {


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

    public interface ButtonCallbacks {
        void removePosition(int position);

        void editPosition(int position);
    }

    public class ViewHolder extends BaseSwipeOpenViewHolder implements
            ItemTouchHelperViewHolder {

        public LinearLayout contentView;
        CircleImageView imageView;
        ImageView closeImageView, downArrowImageView, upArrowImageView;
        TextView nameTV, ageTv, locationTV;
        TextView delete_btn;
        LinearLayout linearLayout, hideLinearLayout, linearLayout_main;
        Button saveButton;
        EditText noteEditText;

        public ViewHolder(View itemView, final ButtonCallbacks callbacks) {
            super(itemView);

            contentView = (LinearLayout) itemView.findViewById(R.id.content_view);
            linearLayout_main = (LinearLayout) itemView.findViewById(R.id.linearLayout_main);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
            hideLinearLayout = (LinearLayout) itemView.findViewById(R.id.hideLinearLayout);
            imageView = (CircleImageView) itemView.findViewById(R.id.imageprofilepic);
            nameTV = (TextView) itemView.findViewById(R.id.nameTV);
            ageTv = (TextView) itemView.findViewById(R.id.ageTv);
            locationTV = (TextView) itemView.findViewById(R.id.locationTV);

            downArrowImageView = (ImageView) itemView.findViewById(R.id.downArrowImageView);
            upArrowImageView = (ImageView) itemView.findViewById(R.id.upArrowImageView);
            saveButton = (Button) itemView.findViewById(R.id.saveButton);
            noteEditText = (EditText) itemView.findViewById(R.id.noteEditText);

            delete_btn = (TextView) itemView.findViewById(R.id.delete_btn);

            downArrowImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    downArrowImageView.setVisibility(View.GONE);
                    upArrowImageView.setVisibility(View.VISIBLE);
                    hideLinearLayout.setVisibility(View.VISIBLE);
//                    holder.notesTitleTextView.setText(homeArrayList.get(position).getName());

                }
            });

            upArrowImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    downArrowImageView.setVisibility(View.VISIBLE);
                    upArrowImageView.setVisibility(View.GONE);
                    hideLinearLayout.setVisibility(View.GONE);

                }
            });

        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));

        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
//            itemView.setBackground(context.getResources().getDrawable(R.drawable.edittext_background, null));

        }

        @NonNull
        @Override
        public View getSwipeView() {
            return linearLayout_main;
        }

        @Override
        public float getEndHiddenViewSize() {
            return delete_btn.getMeasuredWidth();
        }

        @Override
        public float getStartHiddenViewSize() {
            return 0;
        }
    }

}
