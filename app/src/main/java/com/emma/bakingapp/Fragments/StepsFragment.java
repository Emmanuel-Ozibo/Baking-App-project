package com.emma.bakingapp.Fragments;


import android.app.Activity;
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
import com.emma.bakingapp.Models.StepsResponse;
import com.emma.bakingapp.R;
import com.emma.bakingapp.Utils.ItemDecorator;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class StepsFragment extends Fragment{
    private static final String STEPS_PERCEL = "percel";
    private RecyclerView steps_rv;
    private StepsCustomAdapter adapter;
    private StepsCustomAdapter.OnItemClick onItemClick;


    public static StepsFragment newInstance(ArrayList<StepsResponse> stepsResponseList) {

        Bundle args = new Bundle();
        args.putParcelableArrayList(STEPS_PERCEL, stepsResponseList);
        StepsFragment fragment = new StepsFragment();
        fragment.setArguments(args);
        return fragment;
    }

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

        //gets the args and set up views
        final List<StepsResponse> mstepsList = getArguments().getParcelableArrayList(STEPS_PERCEL);
        adapter = new StepsCustomAdapter(mstepsList, onItemClick);

        steps_rv = (RecyclerView)view.findViewById(R.id.steps_rv);
        steps_rv.setHasFixedSize(true);
        steps_rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        steps_rv.setAdapter(adapter);
    }
}
