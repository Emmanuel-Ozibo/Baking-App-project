package com.emma.bakingapp.WidgetBackgroundJobs;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.emma.bakingapp.BakingAppDataBase.BakingAppContract;
import com.emma.bakingapp.Models.IngeredientsResponse;
import com.emma.bakingapp.Models.RecipeModels;
import com.emma.bakingapp.Models.StepsResponse;
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
                if (!isAlreadyInDatabase()){
                    //this prevent it from putting data into the database if data already exists
                    //so by default it only loads data into the database when it is first created
                    loadIntoDataBase(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<RecipeModels>> call, Throwable t) {
                //Handle the error case here
            }
        });
    }



    private boolean isAlreadyInDatabase() {
        Cursor mCursor = getContentResolver().query(BakingAppContract.BakingAppContractFiles.CONTENT_URI,
                null,
                null, null,
                BakingAppContract.BakingAppContractFiles._ID);

        if (mCursor != null){
            return mCursor.getCount()>0;
        }
        return false;
    }



    private void loadIntoDataBase(List<RecipeModels> body) {
        //local variables
        StringBuffer ingrBuffer, stepsBuffer;
        //first get each of the item i.e each recipe model

        //string buffer for the ingredient
        ingrBuffer = new StringBuffer();

        //string buffer for the steps
        stepsBuffer = new StringBuffer();

        for (RecipeModels model : body){
            //get the name
            String recipeName = model.getName();


            //ensures that our buffer is empty before attempting another transaction
            if (ingrBuffer.length() > 0 && stepsBuffer.length() > 0){
                ingrBuffer.delete(0, ingrBuffer.length());
                stepsBuffer.delete(0, stepsBuffer.length());
            }


            //for each of this recipes get the list of ingredients
            List<IngeredientsResponse> ingedients = model.getIngredientsResponse();

            //for each recipe get the list of steps
            List<StepsResponse> stepsResponseList = model.getStepsResponses();

            for (IngeredientsResponse ingredient : ingedients){

                String mIngredient = ingredient.getIngredient();

                //append to buffer
                ingrBuffer.append(mIngredient  + "\n\n");

            }

            for (StepsResponse stepsResponse : stepsResponseList){

                //gets the steps response
                String steps = stepsResponse.getDescription();


                //append to steps buffer
                stepsBuffer.append(steps + "\n\n");
            }

            ContentValues cvS = new ContentValues();
            cvS.put(BakingAppContract.BakingAppContractFiles.RECIPE_NAME, recipeName);
            cvS.put(BakingAppContract.BakingAppContractFiles.INGREDIENT, ingrBuffer.toString());
            cvS.put(BakingAppContract.BakingAppContractFiles.RECIPE_STEPS, stepsBuffer.toString());

            getContentResolver().insert(BakingAppContract.BakingAppContractFiles.CONTENT_URI, cvS);

        }
    }

}
