package com.emma.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emma.bakingapp.Models.StepsResponse;
import com.emma.bakingapp.R;

import java.util.List;



public class StepsCustomAdapter extends RecyclerView.Adapter<StepsCustomAdapter.StepsViewHolder>{
    private List<StepsResponse> stepsResponseList;
    private OnItemClick onItemClick;

    public StepsCustomAdapter(List<StepsResponse> stepsResponseList, OnItemClick onItemClick){
        this.stepsResponseList = stepsResponseList;
        this.onItemClick = onItemClick;
    }

    //interface to handle the on click events
    public interface OnItemClick{
        void onClick(int position, String video_url, String desc_url);
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        return new StepsViewHolder(LayoutInflater.from(context).inflate(R.layout.steps_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        holder.onBindViews(position);

    }

    @Override
    public int getItemCount() {
        if (stepsResponseList.size() == 0){
            return 0;
        }
        return stepsResponseList.size();
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tv_step;

        public StepsViewHolder(View itemView) {
            super(itemView);
            tv_step = (TextView)itemView.findViewById(R.id.step_item);
            tv_step.setOnClickListener(this);
        }

        public void onBindViews(int position){
            tv_step.setText(stepsResponseList.get(position).getShortDescription());
        }


        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            String video_url = stepsResponseList.get(position).getVideoURL();
            String desc_url = stepsResponseList.get(position).getDescription();

            onItemClick.onClick(position, video_url, desc_url);
        }
    }
}
