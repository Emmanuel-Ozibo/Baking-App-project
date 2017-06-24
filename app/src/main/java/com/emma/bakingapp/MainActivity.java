package com.emma.bakingapp;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.emma.bakingapp.Adapters.RecipeAdapter;
import com.emma.bakingapp.BakingAppDataBase.BakingAppContract;
import com.emma.bakingapp.Models.IngeredientsResponse;
import com.emma.bakingapp.Models.RecipeModels;
import com.emma.bakingapp.Rest.ApiClient;
import com.emma.bakingapp.Rest.ApiInterface;
import com.emma.bakingapp.Utils.AlertDialogue;
import com.emma.bakingapp.Utils.ToastMessageUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private ArrayList<RecipeModels> models;
    private static final String RECIPES_LIST = "recipe-list";
    private ProgressDialog mprogressbar;
    private TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //cast the view
        rv = (RecyclerView) findViewById(R.id.recipes_rv);
        mEmptyTextView = (TextView) findViewById(R.id.EmptyTextView);

        mprogressbar = new ProgressDialog(this);
        mprogressbar.setMessage("Please wait....");
        mprogressbar.setCancelable(false);
        mprogressbar.setIndeterminate(true);


        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(RECIPES_LIST)) {
                models = savedInstanceState.getParcelableArrayList(RECIPES_LIST);
                setUpRV(models);
            }
        }

        getRetrofitNetwork();
    }

    private void getRetrofitNetwork() {
        mprogressbar.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<RecipeModels>> models = apiService.getRecipeModel();
        models.enqueue(new Callback<List<RecipeModels>>() {
            @Override
            public void onResponse(Call<List<RecipeModels>> call, retrofit2.Response<List<RecipeModels>> response) {
                mprogressbar.cancel();
                mprogressbar.dismiss();

                List<RecipeModels> mde = response.body();
                setUpRV(mde);

                //put data into the database
                if (!isInDataBase()){
                    putDataIntoDataBase(mde);
                }
            }

            @Override
            public void onFailure(Call<List<RecipeModels>> call, Throwable t) {
                mprogressbar.dismiss();
                getDialog("Sorry something went wrong: ", t.toString());
            }
        });
    }

    private void putDataIntoDataBase(List<RecipeModels> mde) {
        ContentValues cv = new ContentValues();

        for (RecipeModels responds: mde){

            List<IngeredientsResponse> ingList = responds.getIngredientsResponse();

            for (IngeredientsResponse ingr : ingList){

                String recipeIngr = ingr.getIngredient();
                cv.put(BakingAppContract.BakingAppContractFiles.INGREDIENT, recipeIngr);
            }
        }

        getContentResolver().insert(BakingAppContract.BakingAppContractFiles.CONTENT_URI, cv);
    }

    private boolean isInDataBase() {
        //get all items in the data base
        Cursor cursor = getContentResolver().query(BakingAppContract.BakingAppContractFiles.CONTENT_URI,
                null,
                null,
                null,
                BakingAppContract.BakingAppContractFiles._ID);
        if (cursor == null){
            return false;
        }
        return true;
    }


    private void getDialog(String title, String message) {

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage(message)
                .setTitle(title)
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getRetrofitNetwork();
                    }
                })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });

        AlertDialog dialoge = alertBuilder.create();
        dialoge.show();

    }

    private void setUpRV(List<RecipeModels> models) {
        int[] images = {R.drawable.nutella,
                R.drawable.bakedbrownie,
                R.drawable.yellow_cake,
                R.drawable.lemonraspberrycheesecakeweb};

        if (models == null){
            mEmptyTextView.setVisibility(View.VISIBLE);
        }

        RecipeAdapter adp = new RecipeAdapter(models, images);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adp);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(RECIPES_LIST, models);
    }

    int no_of_clicks;

    @Override
    public void onBackPressed() {

        ToastMessageUtil.getToastMessage(getApplicationContext(), "Press Back again to exit");
        no_of_clicks++;

        if (no_of_clicks > 1){
            super.onBackPressed();
            return;
        }

    }
}