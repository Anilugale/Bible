package com.itstest.textselection.fragment;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.itstest.textselection.R;
import com.itstest.textselection.model.Music;
import com.itstest.textselection.service.LocalService;
import com.itstest.textselection.util.CommanMethod;

/**
 Created by Anil Ugale on 16/09/2015.
 */
public class MusicDialog extends DialogFragment implements View.OnClickListener {


    private Music podcast;
    char lang;
    static int back = 0;
    LocalService mService;
    boolean mBound = false;
    SeekBar seekBar;
    boolean init=false;
    ProgressDialog pd;
    ProgressBar process;
    FloatingActionButton fab;
    private long enqueue;
    private DownloadManager dm;
    BroadcastReceiver receiver;
    TextView sName,sSingerName,sSingerContact,sSingerEmail,sSingerDetails;
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
      final   View view = inflater.inflate(R.layout.music_dialog, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        fab=(FloatingActionButton) view.findViewById(R.id.myFAB);
        fab.setOnClickListener(this);
        process= (ProgressBar) view.findViewById(R.id.process);

        System.out.println(podcast.toString());
        sName= (TextView) view.findViewById(R.id.sName);
        sSingerName= (TextView) view.findViewById(R.id.sSingerName);
        sSingerContact= (TextView) view.findViewById(R.id.sSingerContact);
        sSingerEmail= (TextView) view.findViewById(R.id.sSingerEmail);
        sSingerDetails= (TextView) view.findViewById(R.id.sSingerDetails);
        view.findViewById(R.id.download).setOnClickListener(this);

        sName.setText(podcast.getName());
        sSingerName.setText(podcast.getSinger_name());
        sSingerContact.setText(podcast.getSinger_mobile_no());
        sSingerEmail.setText(podcast.getSinger_email());
        sSingerDetails.setText(podcast.getSinger_details());

        sSingerContact.setOnClickListener(this);
        sSingerEmail.setOnClickListener(this);


;



        seekBar= (SeekBar) view.findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(mBound)
                {
                    mService.setSeekto(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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

    public void setData(Music podcast,char lang) {

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
            mService.stopSong();
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

            case R.id.sSingerContact:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + podcast.getSinger_mobile_no()));
                startActivity(intent);
                break;
            case R.id.sSingerEmail:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", podcast.getSinger_email(), null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
                break;
            case R.id.download:

                Download();

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

    void Download()
    {

        dm = (DownloadManager) getActivity().getSystemService(Activity.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(
                Uri.parse(podcast.getUrl()));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        enqueue = dm.enqueue(request);
        Toast.makeText(getActivity(), "Look Notification bar fr download progress", Toast.LENGTH_LONG).show();
    }


    public void updateSeekBar(final int currentPosition,final  int duration) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("status",currentPosition+"    "+duration);
                seekBar.setMax(duration);
                seekBar.setProgress(currentPosition);
            }
        });
    }



}