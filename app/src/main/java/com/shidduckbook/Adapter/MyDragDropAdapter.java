package com.shidduckbook.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shidduckbook.Model.HomePageModel;
import com.shidduckbook.R;
import com.shidduckbook.helper.ItemTouchHelperAdapter;
import com.shidduckbook.helper.ItemTouchHelperViewHolder;
import com.shidduckbook.helper.OnStartDragListener;

import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Peter on 06-Jun-17.
 */

public class MyDragDropAdapter extends RecyclerView.Adapter<MyDragDropAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    private static final String TAG = "MyDragDropAdapter";
    private final OnStartDragListener mDragStartListener;
    Context context;
    ArrayList<String> stringArrayList;
    int count = 0;
    ViewHolder viewHolder;
    ArrayList<HomePageModel> arrayList = new ArrayList<>();
    HomePageModel homePageModel;

    public MyDragDropAdapter(Context context, ArrayList<String> stringArrayList, OnStartDragListener dragStartListener, OnItemClickListener onItemClickListener) {
        mDragStartListener = dragStartListener;
        this.context = context;
        this.stringArrayList = stringArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_content_drag, parent, false);
        viewHolder = new ViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        homePageModel = new HomePageModel();
        String name = stringArrayList.get(position);
        holder.nameTV.setText(name);
        int listSize = stringArrayList.size();

        int pos = position + 1;
        Log.v(TAG, " position number Size 2 :- " + pos);

        holder.priorityTextView.setText("" + pos);

        homePageModel.setId("" + pos);
        homePageModel.setName("" + name);
        arrayList.add(homePageModel);

        holder.nameTV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });

        holder.nameTV.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    @Override
    public void onItemDismiss(int position) {
        stringArrayList.remove(position);
        arrayList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Log.v(TAG, "From position :- " + fromPosition);
        Log.v(TAG, "To position :- " + toPosition);

        Collections.swap(stringArrayList, fromPosition, toPosition);
        Collections.swap(arrayList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        notifyItemRangeChanged(fromPosition, stringArrayList.size());
        notifyItemRangeChanged(toPosition, stringArrayList.size());
        notifyItemRangeChanged(fromPosition, arrayList.size());
        notifyItemRangeChanged(toPosition, arrayList.size());
        return true;
    }

    public ArrayList<String> getPrefernceList() {

        Log.v(TAG, "Arraylist updated :- " + stringArrayList.size());

        for (int i = 0; i< stringArrayList.size();i++) {

            Log.v(TAG, "List Item name :- " +  stringArrayList.get(i) + "  and position :- " + i );
        }
        for (String list : stringArrayList) {
            Log.v(TAG, "List Item name :- " + list + "  and position :- " /*+ list.getId()*/);
        }
        return stringArrayList;
    }

    public interface OnItemClickListener {
        public void onItemClick(int view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        CircleImageView imageView;
        ImageView starImageView, downArrowImageView, starFilledImageView;
        TextView nameTV, priorityTextView, ageTv, locationTV;
        LinearLayout linearLayout, mainLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTV = (TextView) itemView.findViewById(R.id.nameTV);
            priorityTextView = (TextView) itemView.findViewById(R.id.priorityTextView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onItemClear() {
//            itemView.setBackgroundColor(0);
            itemView.setBackground(context.getResources().getDrawable(R.drawable.edittext_background, null));
        }
    }

}