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

import java.util.Timer;
import java.util.TimerTask;

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

    public int initSong(String url, final MusicDialog fragment,SeekBar seekBar) {
        try {
            System.out.println(url
            );
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            fragment.progressBar(false);
            this.seekBar=seekBar;
            this.seekBar.setMax(mediaPlayer.getDuration());
            mediaPlayer.start();
            Timer mTimer = new Timer();
            mTimer.schedule(new TimerTask() {

                @Override
                public void run() {
                    if(mediaPlayer!=null)
                        fragment.updateSeekBar(mediaPlayer.getCurrentPosition(),mediaPlayer.getDuration());
                }
            }, 1000);


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
            System.out.println("start");
        }
        else{
            System.out.println("pause");
            mediaPlayer.pause();
            isPlaying=false;
        }

        return 1;
    }


    public void setSeekto(int goTo)
    {

        mediaPlayer.seekTo(goTo);
    }


    public int stopSong()
    {
        mediaPlayer.stop();
        return 1;
    }


}