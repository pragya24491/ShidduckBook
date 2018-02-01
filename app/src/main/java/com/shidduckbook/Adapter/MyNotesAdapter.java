package com.shidduckbook.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
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
 * Created by Peter on 09-Jun-17.
 */

public class MyNotesAdapter extends RecyclerView.Adapter<MyNotesAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    private static final String TAG = "MyNotesAdapter";

    Context context;
    ArrayList<HomePageModel> homePageModelArrayList;
    private ButtonCallbacks callbacks;

    public MyNotesAdapter(Context context, ArrayList<HomePageModel> homePageModelArrayList, ButtonCallbacks callbacks) {
        this.context = context;
        this.callbacks = callbacks;
        this.homePageModelArrayList = homePageModelArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_my_notes, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, callbacks);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        HomePageModel homePageModel = homePageModelArrayList.get(position);
        holder.nameTV.setText(homePageModel.getName());
        holder.ageTv.setText(homePageModel.getAge());
        holder.ageTv.setVisibility(View.GONE);
        holder.locationTV.setText(homePageModel.getCity());

        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeNotes(position);
                callbacks.removePosition(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return homePageModelArrayList.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {

    }

    private void removeNotes(int adapterPosition) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(AppConstant.KEY_METHOD, AppConstant.METHOD.M_REMOVE_NOTES);
        jsonObject.addProperty(AppConstant.KEY_USER_ID, AppPreferences.getUserId(context));
        jsonObject.addProperty(AppConstant.KEY_NOTES_ID, homePageModelArrayList.get(adapterPosition).getUserId());
        Log.e(TAG, "Json Request M_REMOVE_NOTES Archive :- " + jsonObject);
        removeNotesTask(jsonObject, adapterPosition);
    }

    private void removeNotesTask(JsonObject jsonObject, final int position) {

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

                Log.e(TAG, "Json Response M_REMOVE_NOTES :- " + response.body().toString());
                String s = response.body().toString();
                try {
                    JSONObject jsonObject = new JSONObject(s);

                    JSONArray resultArray = jsonObject.getJSONArray("result");
                    if (jsonObject.getString("status").equalsIgnoreCase("200")) {

                        homePageModelArrayList.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(context.getApplicationContext(), "Removed from notes !", Toast.LENGTH_SHORT).show();

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

    public class ViewHolder extends BaseSwipeOpenViewHolder implements ItemTouchHelperViewHolder {

        CircleImageView imageView;
        ImageView closeImageView, crossImageView, upArrowImageView;
        TextView nameTV, ageTv, locationTV;
        LinearLayout linearLayout, hideLinearLayout, linearLayout_main;

        TextView delete_btn;

        public ViewHolder(View itemView, final ButtonCallbacks callbacks) {
            super(itemView);


            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
            imageView = (CircleImageView) itemView.findViewById(R.id.imageprofilepic);
            nameTV = (TextView) itemView.findViewById(R.id.nameTV);
            ageTv = (TextView) itemView.findViewById(R.id.ageTv);
            locationTV = (TextView) itemView.findViewById(R.id.locationTV);
            crossImageView = (ImageView) itemView.findViewById(R.id.crossImageView);

            linearLayout_main = (LinearLayout) itemView.findViewById(R.id.linearLayout_main);
            delete_btn = (TextView) itemView.findViewById(R.id.delete_btn);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
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
