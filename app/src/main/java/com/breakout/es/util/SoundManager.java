package com.breakout.es.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.breakout.es.R;

import java.io.IOException;

/**
 * Created by mz on 21/05/17.
 */

public class SoundManager {

    private final MediaPlayer wallSideCollision;
    private final MediaPlayer boost;
    private final MediaPlayer defenderCollision;
    private final MediaPlayer wallTopCollision;


    public SoundManager(Context context) {
        wallTopCollision = MediaPlayer.create(context, R.raw.beep1);
        wallSideCollision = MediaPlayer.create(context, R.raw.beep2);
        defenderCollision = MediaPlayer.create(context, R.raw.beep3);
        boost = MediaPlayer.create(context, R.raw.boost);
        init();
    }

    private void init() {
        wallTopCollision.setAudioStreamType(AudioManager.STREAM_MUSIC);
        wallSideCollision.setAudioStreamType(AudioManager.STREAM_MUSIC);
        defenderCollision.setAudioStreamType(AudioManager.STREAM_MUSIC);
        boost.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    public void playWallTopCollision() {
        wallTopCollision.start();
    }

    public void playWallCollision() {
        wallSideCollision.start();
    }

    public void playDefenderCollision() {
        defenderCollision.start();
    }

    public void playPowerUp() {
        boost.start();
    }
}
