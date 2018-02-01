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
import com.squareup.picasso.Picasso;

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
 * Created by Peter on 22-Jun-17.
 */

public class ArchiveAdapter extends RecyclerView.Adapter<ArchiveAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    private static final String TAG = "ArchiveAdapter";
    Context context;
    ArrayList<HomePageModel> archiveArrayList;
    private ButtonCallbacks callbacks;

    public ArchiveAdapter(Context context, ArrayList<HomePageModel> archiveArrayList, ButtonCallbacks callbacks) {
        this.context = context;
        this.archiveArrayList = archiveArrayList;
        this.callbacks = callbacks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_archive_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, callbacks);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        HomePageModel homePageModel = archiveArrayList.get(position);
        holder.nameTV.setText(homePageModel.getName());
        holder.ageTv.setText(homePageModel.getAge());
        holder.ageTv.setVisibility(View.GONE);
        holder.locationTV.setText(homePageModel.getCity());
        String profilepic = homePageModel.getProfilepic();

        if (! profilepic.equalsIgnoreCase("")) {

            Picasso.with(context).load(profilepic)
                    .placeholder(R.drawable.pro_icon)
                    .error(R.drawable.pro_icon)
                    .resize(200, 200)
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.pro_icon));
        }

        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                removeArchive(position);
                callbacks.removePosition(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return archiveArrayList.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {

    }

    private void removeArchive(int adapterPosition) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(AppConstant.KEY_METHOD, AppConstant.METHOD.REMOVE_ARCHIVE);
        jsonObject.addProperty(AppConstant.KEY_USER_ID, AppPreferences.getUserId(context));
        jsonObject.addProperty(AppConstant.KEY_ARCHIVE_USER_ID, archiveArrayList.get(adapterPosition).getUserId());
        Log.e(TAG, "Json Request REMOVE_ARCHIVE Archive :- " + jsonObject);
        removeArchiveTask(jsonObject, adapterPosition);

    }

    private void removeArchiveTask(JsonObject jsonObject, final int position) {

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

                Log.e(TAG, "Json Response REMOVE_ARCHIVE Archive :- " + response.body().toString());
                String s = response.body().toString();
                try {
                    JSONObject jsonObject = new JSONObject(s);

                    if (jsonObject.getString("status").equalsIgnoreCase("200")) {
                        archiveArrayList.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(context.getApplicationContext(), "Archived Removed !", Toast.LENGTH_SHORT).show();

                    } else if (jsonObject.get("status").equals("400")) {

                        String msg = jsonObject.getString("message");
                        Toast.makeText(context.getApplicationContext(), "Already removed !", Toast.LENGTH_SHORT).show();
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

        CircleImageView imageView;
        ImageView closeImageView, downArrowImageView, upArrowImageView;
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

            linearLayout_main = (LinearLayout) itemView.findViewById(R.id.linearLayout_main);
            delete_btn = (TextView) itemView.findViewById(R.id.delete_btn);

        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        }

        @Override
        public void onItemClear() {

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
