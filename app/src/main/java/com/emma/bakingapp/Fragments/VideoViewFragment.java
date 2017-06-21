package com.emma.bakingapp.Fragments;

import android.media.session.MediaController;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.emma.bakingapp.R;
import com.emma.bakingapp.Utils.ToastMessageUtil;

public class VideoViewFragment extends Fragment {
    private VideoView mVideoView;
    private android.widget.MediaController mediaController;
    private int mInitialDuration;
    private static final String DURATION_KEY = "key";
    private MediaSessionCompat mediaSessionCompat;

     public static VideoViewFragment newInstance(String videoUrl) {

         Bundle args = new Bundle();
         args.putString("videoUrl", videoUrl);
         VideoViewFragment fragment = new VideoViewFragment();
         fragment.setArguments(args);
         return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.video_player_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mVideoView = (VideoView) view.findViewById(R.id.video_player_view);
        mVideoView.setVideoURI(Uri.parse(getArguments().getString("videoUrl")));
        mediaController = new android.widget.MediaController(view.getContext());
        mVideoView.setMediaController(mediaController);
        mVideoView.start();

        //gets the duration if any
        if (savedInstanceState != null){
            if (savedInstanceState.containsKey(DURATION_KEY)){
                int mduration  = savedInstanceState.getInt(DURATION_KEY);

                if (mduration != 0){
                    ToastMessageUtil.getToastMessage(view.getContext(), mduration+"");
                    mVideoView.seekTo(mduration);
                }

            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        mVideoView.pause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mVideoView.stopPlayback();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //persits the current duration of the video player view
        mInitialDuration = mVideoView.getCurrentPosition();
        outState.putInt(DURATION_KEY, mInitialDuration);
    }

}
