package com.itstest.textselection.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;

import java.net.URL;
import java.util.Random;

/**
   Created by anil ugale  on 21/09/2015.
 */
public class LocalService extends Service {

    private final IBinder mBinder = new LocalBinder();
    MediaPlayer mediaPlayer;


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

    public int initSong(String url) {
     /*   mediaPlayer.setDataSource();
        mediaPlayer.prepare();*/
        return 1;
    }
}