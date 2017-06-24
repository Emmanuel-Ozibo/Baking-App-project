package com.emma.bakingapp.BakingAppDataBase;


import android.net.Uri;
import android.provider.BaseColumns;

import com.emma.bakingapp.Models.IngeredientsResponse;

import java.util.ArrayList;

public class BakingAppContract {

    //define the authority
    public static final String AUTHORITY = "com.emma.bakingapp";

    //DEFINE THE BASE URI
    public static final Uri  BASE_URI = Uri.parse("content://" + AUTHORITY);

    //define the path
    public static final String PATH = "baking_app_table";

    //Empty constructor
    private BakingAppContract(){}

    public static class BakingAppContractFiles implements BaseColumns{

        //content uri for this table
        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH).build();
        //table name
        public static final String TABLE_NAME = "baking_app_table";

        //recipe name column
        public static final String RECIPE_NAME = "recipe_name";

        //recipe ingredient
        public static final String INGREDIENT = "ingredient";

    }

}
