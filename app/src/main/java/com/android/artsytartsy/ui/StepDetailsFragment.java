package com.android.artsytartsy.ui;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.artsytartsy.R;
import com.android.artsytartsy.data.data.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


public class StepDetailsFragment extends Fragment{

    private Step step;
    private TextView stepDetailsTextView;
    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;
    private long position;
    private ProgressBar mProgressBar;
    private static Button nextStep;
    private Button prevStep;
    private NavigationButtonsHandler handler;
    private static boolean isHideNextButton;

    public StepDetailsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle(R.string.steps_fragment_title);

        if(savedInstanceState != null) {
            step = (Step) savedInstanceState.getSerializable(Constants.KEY_SAVE_STEP);
            position = savedInstanceState.getLong(Constants.KEY_EXO_POSITION);
        }
        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        stepDetailsTextView = rootView.findViewById(R.id.step_details_tv);
        stepDetailsTextView.setText(step.getDescription());
        nextStep = rootView.findViewById(R.id.next_step);
        prevStep = rootView.findViewById(R.id.prev_step);
//        if (rootView.findViewById(R.id.prev_step) == null ) {
//            nextStep.setVisibility(View.INVISIBLE);
//            prevStep.setVisibility(View.INVISIBLE);
//        }
        mProgressBar = rootView.findViewById(R.id.exo_pb);

        mPlayerView = rootView.findViewById(R.id.playerView);
        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                (getResources(), R.drawable.question_mark));

        if (step.getVideoURL() != "") {
            initializePlayer(Uri.parse(step.getVideoURL()));
        } else if (step.getThumbnailURL() != ""){
            initializePlayer(Uri.parse(step.getThumbnailURL()));
        } else {
            mPlayerView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.GONE);
        }

        if (mExoPlayer != null) {
            mExoPlayer.addListener(new ExoPlayer.EventListener() {
                @Override
                public void onTimelineChanged(Timeline timeline, Object manifest) {

                }

                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                }

                @Override
                public void onLoadingChanged(boolean isLoading) {

                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    if (playbackState == ExoPlayer.STATE_BUFFERING) {
                        mProgressBar.setVisibility(View.VISIBLE);
                    } else {
                        mProgressBar.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {

                }

                @Override
                public void onPositionDiscontinuity() {

                }
            });
        }

        if (rootView.findViewById(R.id.prev_step) != null ) {

            nextStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isHideNextButton = handler.onNextButtonClicked(step);
                }
            });

            prevStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.onPreviousButtonClicked(step);
                    isHideNextButton = false;
                }
            });

            if (isHideNextButton) {
                nextStep.setVisibility(View.INVISIBLE);
            } else {
                nextStep.setVisibility(View.VISIBLE);
            }

            if (step.getId() == 0) {
                prevStep.setVisibility(View.INVISIBLE);
            } else {
                prevStep.setVisibility(View.VISIBLE);
            }
        }
        return rootView;
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {

            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            String userAgent = Util.getUserAgent(getContext(), "ArtsyTartsy");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            if (position > 0l) {
                mExoPlayer.seekTo(position);
            }
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    public void setStep(Step step) {
        this.step = step;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(Constants.KEY_SAVE_STEP, step);
        if (mExoPlayer != null) {
            outState.putLong(Constants.KEY_EXO_POSITION, mExoPlayer.getCurrentPosition());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            mExoPlayer.getCurrentPosition();
            mExoPlayer.setPlayWhenReady(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void releasePlayer() {
            if (mExoPlayer != null) {
                mExoPlayer.stop();
                mExoPlayer.release();
                mExoPlayer = null;
            }
    }

    public interface NavigationButtonsHandler {
        boolean onNextButtonClicked(Step step);
        void onPreviousButtonClicked(Step step);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NavigationButtonsHandler) {
            handler = (NavigationButtonsHandler) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement NavigationButtonsHandler");
        }
    }

}
