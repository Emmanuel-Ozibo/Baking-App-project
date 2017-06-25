package com.emma.bakingapp.WidgetBackgroundJobs;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.emma.bakingapp.BakingAppDataBase.BakingAppContract;
import com.emma.bakingapp.Models.IngeredientsResponse;
import com.emma.bakingapp.Models.RecipeModels;
import com.emma.bakingapp.Rest.ApiClient;
import com.emma.bakingapp.Rest.ApiInterface;
import com.emma.bakingapp.Utils.ToastMessageUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DataFetchService extends IntentService{

    public DataFetchService() {
        super("this service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e("This is service: ", "We got here");

        //fetch data form the internet and save them into the database for local storage
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<RecipeModels>> response = apiService.getRecipeModel();

        response.enqueue(new Callback<List<RecipeModels>>() {
            @Override
            public void onResponse(Call<List<RecipeModels>> call, Response<List<RecipeModels>> response) {
//                if (!isAlreadyInDatabase()){
                    //this prevent it from putting data into the database if data already exists
                    //so by default it only loads data into the database when it is first created
                    loadIntoDataBase(response.body());
                //}
            }
            @Override
            public void onFailure(Call<List<RecipeModels>> call, Throwable t) {

            }
        });
    }

    private boolean isAlreadyInDatabase() {

        Cursor mCursor = getContentResolver().query(BakingAppContract.BakingAppContractFiles.CONTENT_URI,
                null,
                null, null,
                BakingAppContract.BakingAppContractFiles._ID);

        if (mCursor == null){
            return false;
        }
        return true;
    }

    private void loadIntoDataBase(List<RecipeModels> body) {
        //first get each of the item i.e each recipe model
        for (RecipeModels model : body){
            //get the name
            String recipeName = model.getName();
            //puts the data in a content value
            ContentValues cv1 = new ContentValues();
            cv1.put(BakingAppContract.BakingAppContractFiles.RECIPE_NAME, recipeName);
            //insert the name to the database
            Uri uri1 = getContentResolver().insert(BakingAppContract.BakingAppContractFiles.CONTENT_URI, cv1);

            if (uri1 != null){
                ToastMessageUtil.getToastMessage(getApplicationContext(), "Added my real G");
            }
            //for each of this recipes get the list of ingredients
            List<IngeredientsResponse> ingedients = model.getIngredientsResponse();

            for (IngeredientsResponse ingredient : ingedients){

                String mIngredient = ingredient.getIngredient();
                //insert data into the content values class
                ContentValues cv2 = new ContentValues();
                cv2.put(BakingAppContract.BakingAppContractFiles.INGREDIENT, mIngredient);

                //insert into the data base
                Uri uri = getContentResolver().insert(BakingAppContract.BakingAppContractFiles.CONTENT_URI, cv2);
                if (uri != null){

                    ToastMessageUtil.getToastMessage(getApplicationContext(), "Saved successfully");

                }
            }
        }
    }
}
