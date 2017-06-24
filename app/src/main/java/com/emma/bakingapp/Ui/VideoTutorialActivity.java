package com.emma.bakingapp.Ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.emma.bakingapp.Adapters.StepsCustomAdapter;
import com.emma.bakingapp.Fragments.DescriptionFragment;
import com.emma.bakingapp.Fragments.StepsFragment;
import com.emma.bakingapp.Fragments.VideoViewFragment;
import com.emma.bakingapp.Models.RecipeModels;
import com.emma.bakingapp.Models.StepsResponse;
import com.emma.bakingapp.R;

import java.util.ArrayList;
import java.util.List;


public class VideoTutorialActivity extends AppCompatActivity implements StepsCustomAdapter.OnItemClick{

    private FragmentManager fragmentManager;
    private boolean isTwoPane;
    private View view;
    public static final String VIDEO_URL = "my_video_url";
    public static final String VIDEO_DESC = "my_desc";
    private ImageView video_icon;
    private TextView desc_dispaly_tv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_tutorial_frag);

        //find the tablet video view which is not in small phones
        view = findViewById(R.id.video_frag_container);
        video_icon = (ImageView) findViewById(R.id.video_icon);
        desc_dispaly_tv = (TextView) findViewById(R.id.desc_displayed);

        //get the action bar
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        //initialise the fragment manager
        fragmentManager = getSupportFragmentManager();

    }

    private void setFragments(VideoViewFragment videoViewFragment, DescriptionFragment descriptionFragment){

        desc_dispaly_tv.setVisibility(View.INVISIBLE);
        video_icon.setVisibility(View.INVISIBLE);

        //place the video fragment
        fragmentManager.beginTransaction().replace(R.id.video_frag_container, videoViewFragment).commit();

        //place the desc fragment
        fragmentManager.beginTransaction().replace(R.id.desc_frag_container, descriptionFragment).commit();

    }

    @Override
    public void onClick(int position, String video_url, String desc_url) {
        if (view != null){
            //The phone is a tablet and is should operate in two pane mode
            this.isTwoPane = true;


            VideoViewFragment videoViewFragment = VideoViewFragment.newInstance(video_url);
            DescriptionFragment descriptionFragment = DescriptionFragment.newInstance(desc_url);

            //set up the fragments
            setFragments(videoViewFragment, descriptionFragment);

        }else {

            //Its a small phone hence we will open the video activity
            Intent intent = new Intent(VideoTutorialActivity.this, VideoActivity.class);
            //put any data into the intent
            intent.putExtra(VIDEO_URL,video_url);
            intent.putExtra(VIDEO_DESC, desc_url);
            startActivity(intent);
        }

    }
}