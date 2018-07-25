package com.backingapp.ayman.backingapp.UI;


import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.backingapp.ayman.backingapp.Constants;
import com.backingapp.ayman.backingapp.Models.Step;
import com.backingapp.ayman.backingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepDetailsActivityFragment extends Fragment {

    @BindView(R.id.videoPlayer) PlayerView playerView;
    @BindView(R.id.stepDescriptionTextView) TextView stepDescriptionTextView;
    @BindView(R.id.previousButton) Button previousButton;
    @BindView(R.id.nextButton) Button nextButton;

    ArrayList<Step> stepList;
    int position;
    long playbackPosition = 0;
    int currentWindow = 0;
    boolean playWhenReady = true;
    private ExoPlayer player;

    public StepDetailsActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_step_details_activity, container, false);
        ButterKnife.bind(this, rootView);

        if (savedInstanceState != null) {
            position = savedInstanceState.getInt(Constants.STEP_POSITION_EXTRA);
            stepList = savedInstanceState.getParcelableArrayList(Constants.STEPS_LIST_EXTRA);
            playbackPosition = savedInstanceState.getLong(Constants.PLAY_BACK_POSITION_EXTRA);
            currentWindow = savedInstanceState.getInt(Constants.CURRENT_WINDOW_EXTRA);
            playWhenReady = savedInstanceState.getBoolean(Constants.PLAY_WHEN_READY_EXTRA);
        } else {
            position = getArguments().getInt(Constants.STEP_POSITION_EXTRA);
            stepList = getArguments().getParcelableArrayList(Constants.STEPS_LIST_EXTRA);
        }


        setNavigationButtons();
        setStepDescriptionTextView();

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle currentState) {
        currentState.putParcelableArrayList(Constants.STEPS_LIST_EXTRA, stepList);
        currentState.putInt(Constants.STEP_POSITION_EXTRA, position);
        currentState.putLong(Constants.PLAY_BACK_POSITION_EXTRA, player.getCurrentPosition());
        currentState.putInt(Constants.CURRENT_WINDOW_EXTRA, player.getCurrentWindowIndex());
        currentState.putBoolean(Constants.PLAY_WHEN_READY_EXTRA, player.getPlayWhenReady());
    }

    @OnClick(R.id.previousButton)
    void onPreviousButtonClick() {
        if (position > 0)
            position--;
        setNavigationButtons();
        setStepDescriptionTextView();
        initializePlayer();
    }

    @OnClick(R.id.nextButton)
    void onNextButtonClick() {
        if (position < stepList.size())
            position++;
        setNavigationButtons();
        setStepDescriptionTextView();
        initializePlayer();
    }

    void setNavigationButtons() {
        if (position == 0) {
            previousButton.setVisibility(View.INVISIBLE);
            nextButton.setVisibility(View.VISIBLE);
        } else if (position == stepList.size() - 1) {
            previousButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.INVISIBLE);
        } else {
            previousButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.VISIBLE);
        }
    }

    void setStepDescriptionTextView() {
        stepDescriptionTextView.setText(stepList.get(position).getDescription());
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        playerView.setPlayer(player);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);

        Uri uri = Uri.parse(stepList.get(position).getVideoURL());
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, false, false);

    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }
}
