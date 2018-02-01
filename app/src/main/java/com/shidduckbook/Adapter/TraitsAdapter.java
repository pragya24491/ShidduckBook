package com.shidduckbook.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shidduckbook.Model.HomePageModel;
import com.shidduckbook.R;
import com.shidduckbook.Util.AppPreferences;
import com.shidduckbook.Util.CircleCheckBox;

import java.util.ArrayList;

/**
 * Created by Peter on 20-Jun-17.
 */

public class TraitsAdapter extends RecyclerView.Adapter<TraitsAdapter.ViewHolder> implements View.OnClickListener {

    private static final String TAG = "TraitsAdapter";
    Context context;
    ArrayList<HomePageModel> allTraitsList = new ArrayList<>();
    OnItemClickListener onItemClickListener;
    ArrayList<HomePageModel> checkedTraits = new ArrayList<>();
    private ArrayList<HomePageModel> selectedTraits = new ArrayList<>();

    public TraitsAdapter(Context context, ArrayList<HomePageModel> allTraitsList, ArrayList<HomePageModel> selectedList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.allTraitsList = allTraitsList;
        this.onItemClickListener = onItemClickListener;
        if (AppPreferences.getPartnerPreferenceStatus(context).equalsIgnoreCase("1")) {
            selectedTraits = selectedList;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_content_traits, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        HomePageModel homePageModel = new HomePageModel();

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final HomePageModel homePageModel = allTraitsList.get(position);
        holder.checkBox.setText(homePageModel.getName());

        for (HomePageModel homePageModel1 : checkedTraits) {
            Log.v(TAG, "inside loop 1");
            if (homePageModel1.getName().equalsIgnoreCase(allTraitsList.get(position).getName())) {
                Log.v(TAG, "Name 1 :- " + homePageModel1.getName());
                Log.v(TAG, "Name 2 :- " + allTraitsList.get(0).getName());
                homePageModel.setChckStatus(true);
                Log.v(TAG, "inside condition 1");
            }
        }

        if (homePageModel.isChckStatus()) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }

        holder.checkBox.setOnCheckedChangeListener(new CircleCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CircleCheckBox view, boolean isChecked) {
                homePageModel.setChckStatus(isChecked);
                if (isChecked) {
                    selectedTraits.add(homePageModel);
                    Log.d(TAG, "Selected list Names:- " + homePageModel.getName());
                    Log.d(TAG, "Selected list :- " + selectedTraits.toString());

                    for (HomePageModel pageModel : selectedTraits){
                        Log.v(TAG, "After Selecting Traits:- " + homePageModel.getName());
                    }

                } else {
                    Log.d(TAG, "Selected list :- " + selectedTraits.toString());
                    selectedTraits.remove(homePageModel);

                    for (HomePageModel pageModel : selectedTraits){
                        Log.v(TAG, "After Removing Traits:- " + homePageModel.getName());
                    }

                    Log.d(TAG, "DataList Remove Names :- " + homePageModel.getName());
                    Log.d(TAG, "DataList Remove :- " + selectedTraits.toString());
                }
            }
        });
    }

    public ArrayList<HomePageModel> getSelectedItem() {
        ArrayList<HomePageModel> lists = new ArrayList<HomePageModel>();
        for (HomePageModel model : allTraitsList) {
            if (model.isChckStatus()) {
                // lists.add(model.getId());
                lists.add(model);
            }
            Log.d(TAG, "CheckBox model " + lists.toString());
        }
        return lists;
    }

    public ArrayList<String> getSelectedItemIds() {

        ArrayList<String> lists = new ArrayList<String>();
        for (HomePageModel model : allTraitsList) {
            if (model.isChckStatus()) {
                // lists.add(model.getId());
                lists.add(model.getId());
            }
            Log.d(TAG, "CheckBox items id :- " + lists.toString());
        }
        return lists;
    }

    public int getSelectedCount() {
        int count = 0;
        ArrayList<String> lists = new ArrayList<String>();
        for (HomePageModel model : allTraitsList) {
            if (model.isChckStatus()) {
                // lists.add(model.getId());
                count++;
            }
            Log.d(TAG, "Total Count :- " + count);
        }
        return count;
    }

    public ArrayList<String> getSelectedTraitsName() {

        ArrayList<String> lists = new ArrayList<String>();
        for (HomePageModel model : allTraitsList) {
            if (model.isChckStatus()) {
                // lists.add(model.getId());
                lists.add(model.getName());
            }
            Log.d(TAG, "CheckBox items Name :- " + lists.toString());
        }
        return lists;
    }

    @Override
    public int getItemCount() {
        return allTraitsList.size();
    }

    @Override
    public void onClick(View v) {

    }

    public interface OnItemClickListener {
        public void onItemClick(int view, int position, HomePageModel homePageModel);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleCheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBox = (CircleCheckBox) itemView.findViewById(R.id.checkbox);
            checkBox.setChecked(false);
        }

        @Override
        public void onClick(View v) {

        }
    }

}
