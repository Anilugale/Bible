package com.itstest.textselection.fragment;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.itstest.textselection.MainActivity;
import com.itstest.textselection.R;
import com.itstest.textselection.database.DatabaseHelper;
import com.itstest.textselection.model.Podcast;
import com.itstest.textselection.model.Verse;
import com.itstest.textselection.service.LocalService;

/**
 Created by Anil Ugale on 16/09/2015.
 */
public class MusicDialog extends DialogFragment implements View.OnClickListener {


    private Podcast podcast;
    char lang;
    static int back = 0;
    LocalService mService;
    boolean mBound = false;
    SeekBar seekBar;
    boolean init=false;
    ProgressDialog pd;
    ProgressBar process;
    FloatingActionButton fab;
    public MusicDialog() { }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {

            LocalService.LocalBinder binder = (LocalService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.music_dialog, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        fab=(FloatingActionButton) view.findViewById(R.id.myFAB);
        fab.setOnClickListener(this);
        process= (ProgressBar) view.findViewById(R.id.process);
        seekBar= (SeekBar) view.findViewById(R.id.seekBar);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK ) {
                    if( back == 2){

                        mService.stopSong();
                        dismiss();
                        return true;
                    }

                    else {
                        back++;
                        Toast.makeText(getActivity(), "press more back close the Music", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                } else {

                    return false;
                }

            }
        });

        return view;
    }

    public void setData(Podcast podcast,char lang) {

        this.podcast=podcast;
        this.lang=lang;

    }


    @Override
    public void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(getActivity(), LocalService.class);
        getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            getActivity().unbindService(mConnection);
            mBound = false;
        }
    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {


            case R.id.myFAB:
                if (mBound) {

                    if(!init) {
                        fab.setVisibility(View.GONE);
                        process.setVisibility(View.VISIBLE);


                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                int num = mService.initSong(podcast.getUrl(), MusicDialog.this,seekBar);

                            }
                        }).start();

                        init=true;
                    }
                    else
                        mService.controller();

                }
                break;
        }

    }

    public void progressBar(boolean status)
    {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                process.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
            }
        });



    }


}