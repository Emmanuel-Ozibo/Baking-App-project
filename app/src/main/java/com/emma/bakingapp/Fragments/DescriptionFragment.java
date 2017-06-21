package com.emma.bakingapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emma.bakingapp.R;


public class DescriptionFragment extends Fragment {
    private static final String DESCRIPTION_KEY = "desc_key";

    public static DescriptionFragment newInstance(String description) {

        Bundle args = new Bundle();
        args.putString(DESCRIPTION_KEY, description);

        DescriptionFragment fragment = new DescriptionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.description_frag_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView desc_tv = (TextView)view.findViewById(R.id.desc_tv);
        //get the string args
        String desc = getArguments().getString(DESCRIPTION_KEY);
        desc_tv.setText(desc);

    }
}
