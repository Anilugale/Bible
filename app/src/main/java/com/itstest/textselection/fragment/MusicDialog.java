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
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
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

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 Created by Anil Ugale on 16/09/2015.
 */
public class MusicDialog extends DialogFragment implements View.OnClickListener {


    private Music podcast;
    char lang;
    static int back = 0;


    SeekBar seekBar;
    ProgressBar process;
    FloatingActionButton fab;
    Handler updateRunnableHandler;
    private DownloadManager dm;
    TextView sName, sSingerName, sSingerContact, sSingerEmail, sSingerDetails,time;

    private Handler myHandler = new Handler();

    MediaPlayer mediaPlayer;

    public MusicDialog() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.music_dialog, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        fab = (FloatingActionButton) view.findViewById(R.id.myFAB);
        fab.setOnClickListener(this);
        process = (ProgressBar) view.findViewById(R.id.process);

        System.out.println(podcast.toString());
        sName = (TextView) view.findViewById(R.id.sName);
        sSingerName = (TextView) view.findViewById(R.id.sSingerName);
        sSingerContact = (TextView) view.findViewById(R.id.sSingerContact);
        sSingerEmail = (TextView) view.findViewById(R.id.sSingerEmail);
        sSingerDetails = (TextView) view.findViewById(R.id.sSingerDetails);
        time = (TextView) view.findViewById(R.id.time);
        view.findViewById(R.id.download).setOnClickListener(this);

        sName.setText(podcast.getName());
        sSingerName.setText(podcast.getSinger_name());
        sSingerContact.setText(podcast.getSinger_mobile_no());
        sSingerEmail.setText(podcast.getSinger_email());
        sSingerDetails.setText(podcast.getSinger_details());

        sSingerContact.setOnClickListener(this);
        sSingerEmail.setOnClickListener(this);

         updateRunnableHandler = new Handler();
        mediaPlayer=new MediaPlayer();

        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setSeekto(i);

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
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (back == 2) {

                        dismiss();
                        return true;
                    } else {
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

    public void setData(Music podcast, char lang) {

        this.podcast = podcast;
        this.lang = lang;

    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.myFAB:
                playMusic(podcast.getUrl());
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

    public void setSeekto(int goTo)
    {
        mediaPlayer.seekTo(goTo);
    }



    public void progressBar(final boolean status) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(status) {
                    process.setVisibility(View.GONE);
                    fab.setVisibility(View.VISIBLE);
                }
                else {
                    process.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.GONE);
                }
            }
        });


    }

    void Download() {

        dm = (DownloadManager) getActivity().getSystemService(Activity.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(
                Uri.parse(podcast.getUrl()));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        dm.enqueue(request);
        Toast.makeText(getActivity(), "Look Notification bar fr download progress", Toast.LENGTH_LONG).show();
    }


    public void updateSeekBar( int currentPosition) {

        seekBar.setProgress(currentPosition);

                            String time1 = String.format("%d : %d ",
                                    TimeUnit.MILLISECONDS.toMinutes(currentPosition),
                                    TimeUnit.MILLISECONDS.toSeconds(currentPosition) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(currentPosition))
                            );
                            time.setText(time1);


    }

    private void playMusic(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {



                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource(url);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (SecurityException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    e.printStackTrace();
                }
                try {
                    mediaPlayer.prepareAsync();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                }
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                        seekBar.setMax(mp.getDuration());


                        Runnable dataupdate=new Runnable() {
                            @Override
                            public void run() {
                                int progress=mediaPlayer.getCurrentPosition();
                                System.out.println(progress);
                                seekBar.setProgress(progress);
                                myHandler.postDelayed(this,1000);
                            }
                        };
                        myHandler.postDelayed(dataupdate,1000);


                    }
                });


            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null)
        {
            mediaPlayer.start();
            mediaPlayer=null;

        }
    }
}
