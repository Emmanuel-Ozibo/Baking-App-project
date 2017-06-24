package com.emma.bakingapp.Ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.emma.bakingapp.Fragments.DescriptionFragment;
import com.emma.bakingapp.Fragments.VideoViewFragment;
import com.emma.bakingapp.R;

public class VideoActivity extends AppCompatActivity {
    private FragmentManager mfragmentManager;
    private TextView decs_displayed_tv;
    private ImageView video_icon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        //cast views
        decs_displayed_tv = (TextView) findViewById(R.id.desc_displayed);
        video_icon = (ImageView) findViewById(R.id.video_icon);


        //instantiate the fragment manager
        mfragmentManager = getSupportFragmentManager();
        //get the intents
        Intent intent = getIntent();
        //get the video url and the video description
        String video_url = intent.getExtras().getString(VideoTutorialActivity.VIDEO_URL);
        String video_desc = intent.getExtras().getString(VideoTutorialActivity.VIDEO_DESC);

        String video = "android.resource://" + getPackageName() + "/" + R.raw.song;

        VideoViewFragment videoViewFragment = VideoViewFragment.newInstance(video);
        DescriptionFragment descriptionFragment = DescriptionFragment.newInstance(video_desc);

        //displays the fragment
        showFragments(videoViewFragment, descriptionFragment);

    }

    private void showFragments(VideoViewFragment videoViewFragment, DescriptionFragment descriptionFragment){

        //make the view to be invisible
        decs_displayed_tv.setVisibility(View.INVISIBLE);
        video_icon.setVisibility(View.INVISIBLE);

        //display all fragments
        mfragmentManager.beginTransaction().replace(R.id.video_frag_container, videoViewFragment).commit();
        mfragmentManager.beginTransaction().replace(R.id.desc_frag_container, descriptionFragment).commit();
    }

}