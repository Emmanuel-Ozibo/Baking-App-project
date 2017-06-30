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
import com.emma.bakingapp.Utils.ImageLoaderUtil;


public class VideoActivity extends AppCompatActivity {
    private FragmentManager mfragmentManager;
    private TextView decs_displayed_tv;
    private ImageView video_icon;
    private VideoViewFragment videoViewFragment1;
    private DescriptionFragment descriptionFragment1;



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
        String thumbnail = intent.getExtras().getString(VideoTutorialActivity.thumbNailImage);

        //set up the video thumbnail
        ImageLoaderUtil.loadImage(getApplicationContext(), thumbnail, R.drawable.ic_ondemand_video_white_36dp,video_icon );

        //Find the fragment by id/Tag
        videoViewFragment1 = (VideoViewFragment)mfragmentManager.findFragmentById(R.id.video_frag_container);
        descriptionFragment1 = (DescriptionFragment)mfragmentManager.findFragmentById(R.id.desc_frag_container);



        if (videoViewFragment1 == null && descriptionFragment1 == null){

            //Creates new instance of the fragment
            videoViewFragment1 = new VideoViewFragment();
            descriptionFragment1 = new DescriptionFragment();

            //displays the fragment
            showFragments(videoViewFragment1, descriptionFragment1);
            //set the data
            videoViewFragment1.setData(video_url);
            descriptionFragment1.setData1(video_desc);
        }

    }


    private void showFragments(VideoViewFragment videoViewFragment, DescriptionFragment descriptionFragment){

        //make the view to be invisible
        decs_displayed_tv.setVisibility(View.INVISIBLE);
        video_icon.setVisibility(View.INVISIBLE);

        //display all fragments
        mfragmentManager.beginTransaction()
                .add(R.id.video_frag_container, videoViewFragment).commit();
        mfragmentManager.beginTransaction()
                .add(R.id.desc_frag_container, descriptionFragment).commit();
    }


    @Override
    protected void onPause() {
        mfragmentManager.beginTransaction().remove(videoViewFragment1).commit();
        mfragmentManager.beginTransaction().remove(descriptionFragment1).commit();
        super.onPause();
    }

    @Override
    protected void onResume() {

        String videoUrl = videoViewFragment1.getData();
        if (videoUrl != null){
            videoViewFragment1.setData(videoUrl);
        }

        super.onResume();

    }
}