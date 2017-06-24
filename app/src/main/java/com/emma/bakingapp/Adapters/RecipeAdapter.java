package com.emma.bakingapp.Adapters;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.emma.bakingapp.Models.RecipeModels;
import com.emma.bakingapp.R;
import com.emma.bakingapp.Ui.RecipeDetailActivity;
import com.emma.bakingapp.Utils.ImageLoaderUtil;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{

    private List<RecipeModels>recipeModelsList;
    private int[] images;

    public RecipeAdapter(List<RecipeModels> recipeModelsList, int[] images){
        this.recipeModelsList = recipeModelsList;
        this.images = images;
    }


    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //context
        Context context = parent.getContext();
        //layout inflater
        LayoutInflater inflater = LayoutInflater.from(context);
        //returned view
        View view = inflater.inflate(R.layout.single_recipe_layout, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {

        holder.onBindView(position);
    }

    @Override
    public int getItemCount() {
        if (recipeModelsList.size() == 0){
            return 0;
        }
        return recipeModelsList.size();

    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_recipe_name;
        private ImageView recipe_img;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            tv_recipe_name = (TextView)itemView.findViewById(R.id.recipe_name);
            recipe_img = (ImageView)itemView.findViewById(R.id.recipe_img);

        }

        public void onBindView(final int postion){
            //this gets the image url
            final String image_url = recipeModelsList.get(postion).getImage();
            //This uses picasso image library to load image into view
            ImageLoaderUtil.loadImage(itemView.getContext(),image_url, images[postion], recipe_img );

            recipe_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), RecipeDetailActivity.class);
                    intent.putExtra("recipesModels", recipeModelsList.get(postion));
                    intent.putExtra("imageRes", images[postion]);
                    intent.putExtra("image_url", image_url);
                    view.getContext().startActivity(intent);
                }
            });

            //adds a nice font to the recipe name
            Typeface typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/airstrikeacad.ttf");
            tv_recipe_name.setTypeface(typeface);

            String recipe_name = recipeModelsList.get(postion).getName();
            tv_recipe_name.setText(recipe_name);
        }

    }
}
