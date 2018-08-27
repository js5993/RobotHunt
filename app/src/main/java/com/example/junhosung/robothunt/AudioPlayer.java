package com.example.junhosung.robothunt;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Junho Sung on 8/9/2018.
 */

public class AudioPlayer {

    private MediaPlayer mediaPlayer;

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void play(Context context) {
        stop();

        mediaPlayer = MediaPlayer.create(context,R.raw.over_the_rainbow);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stop();
            }
        });

        mediaPlayer.start();

    }

    public void playCheering(final Context context) {
        stop();

        mediaPlayer = MediaPlayer.create(context,R.raw.cheering_sound);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stop();
                play(context);
            }
        });

        mediaPlayer.start();

    }


}
