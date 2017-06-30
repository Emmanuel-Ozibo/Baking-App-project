package com.emma.bakingapp.Utils;

import android.content.Context;
import android.widget.ImageView;

import com.emma.bakingapp.R;
import com.squareup.picasso.Picasso;


public class ImageLoaderUtil {

    public static void loadImage(Context context, String imageUrl, int res, ImageView imageView){

        if (imageUrl.isEmpty()){
            imageView.setImageResource(res);

        }else {

            Picasso.with(context)
                    .load(imageUrl)
                    .error(res)
                    .into(imageView);
        }

    }

}
