package com.android.artsytartsy.ui;


import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.artsytartsy.R;
import com.android.artsytartsy.data.data.model.Ingredient;
import com.android.artsytartsy.data.data.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepDetailsFragment extends Fragment {

    private Step step;
    private TextView stepDetailsTextView;
    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;
//    private ImageView stepDetailImageView;


    public StepDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("abc", "inside oncreateview");
        if(savedInstanceState != null) {
            step = (Step) savedInstanceState.getSerializable(Constants.KEY_SAVE_STEP);
        }
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        stepDetailsTextView = rootView.findViewById(R.id.step_details_tv);
        stepDetailsTextView.setText(step.getDescription());
//        stepDetailImageView = rootView.findViewById(R.id.step_detail_iv);

        mPlayerView = rootView.findViewById(R.id.playerView);
        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                (getResources(), R.drawable.question_mark));

        if (step.getVideoURL() != "") {
            initializePlayer(Uri.parse(step.getVideoURL()));
            Log.d("abc", "player initialized via video url");
        } else if (step.getThumbnailURL() != ""){
            initializePlayer(Uri.parse(step.getThumbnailURL()));
            Log.d("abc", "player initialized via thumbnail url");
        }

        return rootView;
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
//            mExoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "ClassicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    public void setStep(Step step) {
        this.step = step;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(Constants.KEY_SAVE_STEP, step);
    }

    @Override
    public void onPause() {
        super.onPause();
        mExoPlayer.setPlayWhenReady(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void releasePlayer() {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
    }
}
