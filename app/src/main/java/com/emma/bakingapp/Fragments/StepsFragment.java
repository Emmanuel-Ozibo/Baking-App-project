package com.emma.bakingapp.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emma.bakingapp.Adapters.StepsCustomAdapter;
import com.emma.bakingapp.Models.IngeredientsResponse;
import com.emma.bakingapp.Models.RecipeModels;
import com.emma.bakingapp.Models.StepsResponse;
import com.emma.bakingapp.R;
import com.emma.bakingapp.Rest.ApiClient;
import com.emma.bakingapp.Rest.ApiInterface;
import com.emma.bakingapp.Utils.ToastMessageUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StepsFragment extends Fragment{
    private static final String STEPS_PERCEL = "percel";
    private RecyclerView steps_rv;
    private StepsCustomAdapter adapter;
    private StepsCustomAdapter.OnItemClick onItemClick;
    private List<IngeredientsResponse> ingeredientList;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onItemClick = (StepsCustomAdapter.OnItemClick)context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.steps_frag_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getRetrofitNetWorkAndSetUpViews(view);
    }

    private void getRetrofitNetWorkAndSetUpViews(final View view) {
        ingeredientList = new ArrayList<>();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<List<RecipeModels>> reponseList = apiService.getRecipeModel();
        reponseList.enqueue(new Callback<List<RecipeModels>>() {

            @Override
            public void onResponse(Call<List<RecipeModels>> call, Response<List<RecipeModels>> response) {
                List<RecipeModels> respondsList = response.body();
                for (RecipeModels model : respondsList){
                    List<StepsResponse> ingrList = model.getStepsResponses();

                    adapter = new StepsCustomAdapter(ingrList, onItemClick);

                    steps_rv = (RecyclerView)view.findViewById(R.id.steps_rv);
                    steps_rv.setHasFixedSize(true);
                    steps_rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    steps_rv.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<RecipeModels>> call, Throwable t) {
                ToastMessageUtil.getToastMessage(getContext(), "Error: " + t.toString());
            }
        });
    }
}
