package com.itstest.textselection.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.widget.SeekBar;

import com.itstest.textselection.MainActivity;
import com.itstest.textselection.fragment.MusicDialog;

/**
 Created by anil ugale  on 21/09/2015.
 */
public class LocalService extends Service {

    private final IBinder mBinder = new LocalBinder();
    MediaPlayer mediaPlayer;
    boolean isPlaying=false;
    SeekBar seekBar;


    public class LocalBinder extends Binder {
        public LocalService getService() {
            return LocalService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new  MediaPlayer();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public int initSong(String url,MusicDialog fragment,SeekBar seekBar) {
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            fragment.progressBar(false);
            this.seekBar=seekBar;
            this.seekBar.setMax(mediaPlayer.getDuration());
           /* mediaPlayer.start();
           final  Handler mHandler = new Handler();

            fragment.getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (mediaPlayer != null) {
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        LocalService.this.seekBar.setProgress(mCurrentPosition);
                    }
                    mHandler.postDelayed(this, 1000);
                }
            });
            this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (mediaPlayer != null && fromUser) {
                        mediaPlayer.seekTo(progress * 1000);
                    }
                }
            });*/


            return 1;
        } catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public int controller()
    {
        if(!isPlaying){
            mediaPlayer.start();
            isPlaying=true;
        }
        else{
            mediaPlayer.pause();
            isPlaying=false;
        }
        return 1;
    }




    public int stopSong()
    {
        mediaPlayer.stop();
        return 1;
    }


}