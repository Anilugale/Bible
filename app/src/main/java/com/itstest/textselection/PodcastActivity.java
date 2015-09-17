package com.itstest.textselection;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.itstest.textselection.adapter.PodcastAdapter;
import com.itstest.textselection.adapter.StoryAdapter;
import com.itstest.textselection.model.Podcast;
import com.itstest.textselection.model.Story;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PodcastActivity extends AppCompatActivity {

    long downloadID;

    private String downloadCompleteIntentName = DownloadManager.ACTION_DOWNLOAD_COMPLETE;
    private IntentFilter downloadCompleteIntentFilter = new IntentFilter(downloadCompleteIntentName);
    private BroadcastReceiver downloadCompleteReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TO BE FILLED
            System.out.println(intent.getExtras());
        }
    };
    private boolean playPause;
    private MediaPlayer mediaPlayer;
    /**
     * remain false till media is not completed, inside OnCompletionListener make it true.
     */
    private boolean intialStage = true;
    FloatingActionButton floatingActionButton;

    RecyclerView recyclerView ;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast);


        registerReceiver(downloadCompleteReceiver, downloadCompleteIntentFilter);
        floatingActionButton=(FloatingActionButton) findViewById(R.id.myFAB);
        recyclerView=(RecyclerView)findViewById(R.id.list_podcast);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<Podcast> dataStory=new ArrayList<>();
        download();
        for(int i=0;i<100;i++)
        {
            Podcast story=new Podcast();
            story.setId(i);
            story.setUrl("http://shanu.jelastic.elastx.net/wholesale/Kalimba.mp3");
            story.setTtl("Chapter "+i);
            dataStory.add(story);
        }


        PodcastAdapter storyAdapter = new PodcastAdapter(this, dataStory);
        recyclerView.setAdapter(storyAdapter);


        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

    }

        public void openSearchPkg(String songUrl) {

          //  if (!playPause) {
             // floatingActionButton.setBackgroundResource(R.mipmap.ic_pause);
                floatingActionButton.setImageDrawable(ContextCompat.getDrawable(PodcastActivity.this, R.mipmap.ic_pause));
                if (intialStage)
                    new Player()
                            .execute(songUrl);
                else {
                    if (!mediaPlayer.isPlaying())
                        mediaPlayer.start();
                }
                playPause = true;
         /*   } else {
                floatingActionButton.setImageDrawable(ContextCompat.getDrawable(PodcastActivity.this, R.mipmap.ic_play));
           //     btn.setText("button_play");
                if (mediaPlayer.isPlaying())
                    mediaPlayer.pause();
                playPause = false;
            }*/
        }


   void download()
    {
        String url = "http://shanu.jelastic.elastx.net/wholesale/Kalimba.mp3";
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

// only download via WIFI
       // request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Example");
        request.setDescription("Downloading a very large zip");

// we just want to download silently
        request.setVisibleInDownloadsUi(false);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
        request.setDestinationInExternalFilesDir(this, null, "Kalimba.mp3");

// enqueue this request
        DownloadManager downloadManager = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadID = downloadManager.enqueue(request);

    }

    class Player extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog progress;

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            Boolean prepared;
            try {

                mediaPlayer.setDataSource(params[0]);

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // TODO Auto-generated method stub
                        intialStage = true;
                        playPause=false;
                     // btn.setBackgroundResource(R.drawable.button_play);
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });
                mediaPlayer.prepare();
                prepared = true;
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                Log.d("IllegarArgument", e.getMessage());
                prepared = false;
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            }
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (progress.isShowing()) {
                progress.cancel();
            }
            Log.d("Prepared", "//" + result);
            mediaPlayer.start();

            intialStage = false;
        }

        public Player() {
            progress = new ProgressDialog(PodcastActivity.this);
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            this.progress.setMessage("Buffering...");
            this.progress.show();

        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }



}

    @Override
    protected void onDestroy() {
        unregisterReceiver(downloadCompleteReceiver);
        super.onDestroy();
    }
}
