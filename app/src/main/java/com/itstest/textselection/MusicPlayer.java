package com.itstest.textselection;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.itstest.textselection.model.Music;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MusicPlayer extends AppCompatActivity implements View.OnClickListener {



    static int back = 0;
    public static Music music;
    public static char lang;
    boolean isPause;
    SeekBar seekBar;
    Button fab;
    Handler updateRunnableHandler;
    private DownloadManager dm;
    TextView sName, sSingerName, sSingerContact, sSingerEmail, sSingerDetails, time,totalTime;
    private Handler myHandler = new Handler();
    ProgressDialog pd;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(music!=null)
            init();
        else
        {
            Toast.makeText(MusicPlayer.this, "Error", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private void init() {

        fab = (Button) findViewById(R.id.myFAB);
        fab.setOnClickListener(this);



        sName = (TextView) findViewById(R.id.sName);
        sSingerName = (TextView) findViewById(R.id.sSingerName);
        sSingerContact = (TextView) findViewById(R.id.sSingerContact);
        sSingerEmail = (TextView) findViewById(R.id.sSingerEmail);
        sSingerDetails = (TextView) findViewById(R.id.sSingerDetails);
        time = (TextView) findViewById(R.id.time);
        totalTime = (TextView) findViewById(R.id.totalTime);
        findViewById(R.id.download).setOnClickListener(this);

        sName.setText(music.getName());
        sSingerName.setText(music.getSinger_name());
        sSingerContact.setText(String.format("For contact – Call on +91 %s", music.getSinger_mobile_no()));
        sSingerEmail.setText(music.getSinger_email());
        sSingerDetails.setText(music.getSinger_details());

        sSingerContact.setOnClickListener(this);
        sSingerEmail.setOnClickListener(this);
        findViewById(R.id.share).setOnClickListener(this);

        updateRunnableHandler = new Handler();
        mediaPlayer = new MediaPlayer();

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer.isPlaying()&& fromUser) {
                    // pd=ProgressDialog.show(MusicPlayer.this,"","Loading",true,false);
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        findViewById(R.id.forward).setOnClickListener(this);
        findViewById(R.id.backward).setOnClickListener(this);
        findViewById(R.id.contact_holder).setOnClickListener(this);

        playMusic(music.getUrl());

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.myFAB:
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    fab.setText("Play");
                    isPause=true;
                }
                else if(isPause){
                    isPause=false;
                    fab.setText("Pause");
                    mediaPlayer.start();
                    Toast.makeText(MusicPlayer.this, "Pause", Toast.LENGTH_SHORT).show();
                }
                else
                    playMusic(music.getUrl());
                break;

            case R.id.sSingerContact:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + music.getSinger_mobile_no()));
                startActivity(intent);
                break;
            case R.id.sSingerEmail:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", music.getSinger_email(), null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
                break;
            case R.id.download:
                Download();
                break;
            case R.id.forward:

                int forwardPosition=mediaPlayer.getCurrentPosition()+(1000*30);
                if(forwardPosition<mediaPlayer.getDuration())
                    mediaPlayer.seekTo((int) forwardPosition);


                break;
            case R.id.backward:

                int backwardPosition=mediaPlayer.getCurrentPosition()-(1000*30);
                if(backwardPosition>0)
                    mediaPlayer.seekTo((int) backwardPosition);
                break;
            case R.id.share:
                shareSong();
                break;
            case R.id.contact_holder:
                call();
                break;


        }

    }


    public void call() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+music.getSinger_mobile_no()));
        startActivity(callIntent);
    }




    void Download() {
        Toast.makeText(MusicPlayer.this, "Song is downloading..", Toast.LENGTH_SHORT).show();
        dm = (DownloadManager)this.getSystemService(Activity.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(
                Uri.parse(music.getUrl()));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        dm.enqueue(request);
        Toast.makeText(this, "Look Notification bar fr download progress", Toast.LENGTH_LONG).show();
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
        fab.setText("Pause");
        pd=ProgressDialog.show(this,"","Please wait… Fetching Devotions…",true,false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource(url);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (SecurityException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();

                }
                try {
                    mediaPlayer.prepareAsync();
                    // progressBar(true);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    Toast.makeText(MusicPlayer.this, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                }
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        pd.dismiss();
                        totalTime.setText(String.format("%d : %d ",
                                TimeUnit.MILLISECONDS.toMinutes(mp.getDuration()),
                                TimeUnit.MILLISECONDS.toSeconds(mp.getDuration()) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mp.getDuration()))
                        ));
                        mp.start();
                        seekBar.setMax(mp.getDuration());

                        Runnable dataupdate=new Runnable() {
                            @Override
                            public void run() {
                                if(pd.isShowing())
                                {
                                    pd.dismiss();
                                }
                                if(mediaPlayer!=null) {
                                    int progress = mediaPlayer.getCurrentPosition();

                                    updateSeekBar(progress);
                                    myHandler.postDelayed(this, 1000);
                                }
                            }
                        };
                        myHandler.postDelayed(dataupdate,1000);

                    }
                });


            }
        }).start();
    }

    @Override
    protected void onStop() {
        if(mediaPlayer!=null)
        {
            if(!isPause) {
                mediaPlayer.pause();
                fab.setText("Play");
                isPause = true;
            }

        }
        super.onStop();
    }

    @Override
    protected void onRestart() {
        if(mediaPlayer!=null)
        {
            if(isPause) {
                mediaPlayer.start();
                fab.setText("Pause");
                isPause = false;
            }

        }

        super.onRestart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null)
        {
            Toast.makeText(MusicPlayer.this, "Stop Playing", Toast.LENGTH_SHORT).show();
            mediaPlayer.stop();
            mediaPlayer=null;

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_music, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.home:
                gotoHome();
                break;
        }
        return  true;
    }

    private void gotoHome() {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void shareSong() {
        Intent shareIntent=new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        String shareString = music.getName()+"\n"+
                music.getSinger_email()+"\n"+
                music.getSinger_mobile_no()+"\n"+
                music.getUrl();

        shareIntent.putExtra(Intent.EXTRA_TEXT,shareString);
        startActivity(Intent.createChooser(shareIntent,"Share Using"));
    }

    @Override
    public void onBackPressed() {

        if (back == 2) {

            if(mediaPlayer.isPlaying())
                mediaPlayer.stop();
            super.onBackPressed();
        } else {
            back++;
            Toast.makeText(this, "press more back close the Music", Toast.LENGTH_SHORT).show();

        }


    }
}
