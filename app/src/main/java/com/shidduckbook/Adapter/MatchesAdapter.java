package com.shidduckbook.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shidduckbook.Model.HomePageModel;
import com.shidduckbook.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Peter on 05-Jun-17.
 */

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder> {

    private static final String TAG = "MatchesAdapter" ;
    Context context;
    ArrayList<HomePageModel> homeArrayList;
    int fav_status;

    public MatchesAdapter(Context context, ArrayList<HomePageModel> homeArrayList) {
        this.context = context;
        this.homeArrayList = homeArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_content_common, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        HomePageModel homePageModel = homeArrayList.get(position);
        holder.nameTV.setText(homePageModel.getName());
        holder.ageTv.setText(homePageModel.getAge());
        holder.locationTV.setText(homePageModel.getCity());

    }

    @Override
    public int getItemCount() {
        return homeArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imageView;
        ImageView closeImageView, downArrowImageView, starFilledImageView;
        TextView nameTV, ageTv, locationTV;
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
            imageView = (CircleImageView) itemView.findViewById(R.id.imageprofilepic);
            nameTV = (TextView) itemView.findViewById(R.id.nameTV);
            ageTv = (TextView) itemView.findViewById(R.id.ageTv);
            locationTV = (TextView) itemView.findViewById(R.id.locationTV);
            starFilledImageView = (ImageView) itemView.findViewById(R.id.starFilledImageView);
            downArrowImageView = (ImageView) itemView.findViewById(R.id.downArrowImageView);
        }
    }
}
