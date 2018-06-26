package com.sandraprog.bakingapp;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.sandraprog.bakingapp.model.Step;

import java.util.List;

public class RecipeDetailFragment extends Fragment implements ExoPlayer.EventListener {
    private Step mStep;
    private List<Step> mStepList;
    private int position;
    private View rootView;

    public static final String ARG_ITEM_ID = "item";

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mExoPlayerView;
    private long mPlayerPosition;
    private String mVideoUrl;
    private String mImageUrl;


    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mStepList = getArguments().getParcelableArrayList(ARG_ITEM_ID);
            position = getArguments().getInt("id");
            mStep = mStepList.get(position);
            mVideoUrl = mStep.getVideo();
            mImageUrl = mStep.getThumbnailURL();
        }
        if (savedInstanceState != null) {
            mPlayerPosition = savedInstanceState.getLong("player");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.recipe_detail, container, false);
        mExoPlayerView = rootView.findViewById(R.id.playerView);

        if (mStep != null) {
            ((TextView) rootView.findViewById(R.id.step_description)).setText(mStep.getDescription());
            initializePlayer(Uri.parse(mStep.getVideo()));
        }

        return rootView;
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("player", mPlayerPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        TextView tvStepDescription = getActivity().findViewById(R.id.step_description);
        Button prevButton = getActivity().findViewById(R.id.button_prev);
        Button nextButton = getActivity().findViewById(R.id.button_next);

        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            tvStepDescription.setVisibility(View.GONE);
            prevButton.setVisibility(View.GONE);
            nextButton.setVisibility(View.GONE);

            if (getActivity().getActionBar() != null) {
                getActivity().getActionBar().hide();
            }
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);

            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mExoPlayerView.getLayoutParams();
            params.width = params.MATCH_PARENT;
            params.height = params.MATCH_PARENT;
            mExoPlayerView.setLayoutParams(params);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

            tvStepDescription.setVisibility(View.VISIBLE);
            prevButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.VISIBLE);
            if (getActivity().getActionBar() != null) {
                getActivity().getActionBar().show();
            }
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mExoPlayerView.getLayoutParams();
            params.width = params.MATCH_PARENT;
            params.height = params.WRAP_CONTENT;
            mExoPlayerView.setLayoutParams(params);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) mPlayerPosition = mExoPlayer.getCurrentPosition();
        releasePlayer();
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this.getActivity(), trackSelector);
            mExoPlayerView.setPlayer(mExoPlayer);

            mExoPlayer.addListener(this);

            String userAgent = Util.getUserAgent(this.getActivity(), "RecipeStep");
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this.getActivity(), userAgent);
            MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(mediaUri);

            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
            mExoPlayer.seekTo(mPlayerPosition);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mPlayerPosition = mExoPlayer.getContentPosition();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

   public void setupView(int position) {
        mStep = mStepList.get(position);
        if (mStep != null) {
            ((TextView) rootView.findViewById(R.id.step_description)).setText(mStep.getDescription());
            releasePlayer();
            mVideoUrl = mStep.getVideo();
            mPlayerPosition = 0;
            initializePlayer(Uri.parse(mStep.getVideo()));
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }
}
