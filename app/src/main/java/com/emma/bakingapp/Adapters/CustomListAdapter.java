package com.emma.bakingapp.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.emma.bakingapp.Models.IngeredientsResponse;
import com.emma.bakingapp.R;

import java.util.List;


public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.RecipeViewHolder> {
    private List<IngeredientsResponse> ingeredientsResponseList;
    private Context context;
    private int resource;

    public CustomListAdapter( Context context, int resource, List<IngeredientsResponse> ingeredientsResponseList) {
        this.context = context;
        this.resource = resource;
        this.ingeredientsResponseList = ingeredientsResponseList;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecipeViewHolder(LayoutInflater.from(context).inflate(resource, parent, false));
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.onBindView(position);
    }

    @Override
    public int getItemCount() {
        return ingeredientsResponseList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_quatity, tv_measure, tv_ingr_name;

        public RecipeViewHolder(View itemView) {
            super(itemView);

            tv_quatity = (TextView)itemView.findViewById(R.id.quantity_tv);
            tv_measure = (TextView)itemView.findViewById(R.id.measure_tv);
            tv_ingr_name = (TextView)itemView.findViewById(R.id.ingr_name_tv);

            //use custom font
            Typeface ingr_typeface = Typeface.createFromAsset(context.getAssets(), "fonts/chisel_mark.ttf");
            tv_ingr_name.setTypeface(ingr_typeface);


        }

        private void onBindView(int position){
            tv_quatity.setText(String.valueOf(ingeredientsResponseList.get(position).getQuantity()));
            tv_measure.setText(String.valueOf(ingeredientsResponseList.get(position).getMeasure()));
            tv_ingr_name.setText(String.valueOf(ingeredientsResponseList.get(position).getIngredient()));
        }

    }
}