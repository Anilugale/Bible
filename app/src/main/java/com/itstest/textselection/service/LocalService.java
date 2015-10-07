package com.itstest.textselection.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.SeekBar;

import com.itstest.textselection.MainActivity;
import com.itstest.textselection.fragment.MusicDialog;

import java.io.IOException;
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

    public int initSong(String url, final MusicDialog fragment,final SeekBar seekBar,final Handler updateRunnableHandler) {
        try {



            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(url);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.prepareAsync();
            // You can show progress dialog here untill it prepared to play
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(final MediaPlayer mp) {

                    mp.start();

                    isPlaying=true;

                    fragment.progressBar(isPlaying);

                    final Thread mUpdateRunnable = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (mp != null){
                                int progressTimel=mp.getCurrentPosition();
                               fragment.updateSeekBar(progressTimel);

                                seekBar.setProgress(mp.getCurrentPosition());
                              }
                            updateRunnableHandler.postDelayed(this, 1000);
                        }
                    });



                    updateRunnableHandler.postDelayed(mUpdateRunnable, 1000);
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    // dissmiss progress bar here. It will come here when
                    // MediaPlayer
                    // is not able to play file. You can show error message to user
                    return false;
                }
            });


           /* mediaPlayer.setDataSource(url);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();

            this.seekBar=seekBar;
            this.

            mediaPlayer.start();
            isPlaying=true;

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(final MediaPlayer mp) {

                }
            });

*/
            // mediaPlayer.prepareAsync();


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
            return 1;
        }
        else{
            System.out.println("pause");
            mediaPlayer.pause();
            isPlaying=false;
            return 0;
        }


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