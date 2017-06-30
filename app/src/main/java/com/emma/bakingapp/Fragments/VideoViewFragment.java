package com.emma.bakingapp.Fragments;

import android.media.session.MediaController;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.emma.bakingapp.R;
import com.emma.bakingapp.Utils.ToastMessageUtil;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class VideoViewFragment extends Fragment {
    private String vidUrl;

    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    private long mInitialDuration;
    private static final String DURATION_KEY = "key";
    private boolean isPlayerReady = true;
    private static final String VIDEO_URL = "videoUrl";



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    public void setData(String data) {
        this.vidUrl = data;
    }

    public String getData() {
        return vidUrl;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.video_player_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        simpleExoPlayerView = (SimpleExoPlayerView)view.findViewById(R.id.ExoPlayer_view);

        if (player == null){
            //create one
            player = (SimpleExoPlayer) initExoplayer();

        }


        //set the player view to the Exoplayer
        simpleExoPlayerView.setPlayer(player);

        Uri mVideoUri = Uri.parse(vidUrl);

        player.setPlayWhenReady(isPlayerReady);

        MediaSource mediaSource = getMediaSource(mVideoUri);

        //get data from save instance state
        if (savedInstanceState != null){
            if (savedInstanceState.containsKey(DURATION_KEY)){
                mInitialDuration = savedInstanceState.getLong(DURATION_KEY);
                player.seekTo(mInitialDuration);
            }
        }

        //start the Exo player
        player.prepare(mediaSource);

    }

    private ExoPlayer initExoplayer(){

       return ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(),
                new DefaultLoadControl());
    }

    private MediaSource getMediaSource(Uri uri) {

        DefaultExtractorsFactory Def = new DefaultExtractorsFactory();
        DefaultHttpDataSourceFactory dhdf = new DefaultHttpDataSourceFactory("ua");
        return new ExtractorMediaSource(uri, dhdf, Def, null, null);
    }

    @Override
    public void onResume() {
        super.onResume();
        //when it is resumed also resume the ExopLAYER
        initExoplayer();
        isPlayerReady = false;

    }

    @Override
    public void onPause() {
        super.onPause();
        if (player != null){
            player.release();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        releaseExoPlayer();
    }

    private void releaseExoPlayer() {
        player.stop();
        player.release();
        player = null;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //persits the current duration of the video player view
        mInitialDuration = player.getCurrentPosition();
        outState.putLong(DURATION_KEY, mInitialDuration);
    }

}
