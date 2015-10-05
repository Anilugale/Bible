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
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.itstest.textselection.adapter.PodcastAdapter;
import com.itstest.textselection.model.Music;
import com.itstest.textselection.util.CommanMethod;
import com.itstest.textselection.util.JsonCallBack;
import com.itstest.textselection.util.NetworkRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PodcastActivity extends AppCompatActivity implements JsonCallBack{


    char lang;
    int RequestCodePodcast=121212;
    List<Music> dataPodcast;
    TextView error;

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
    ProgressBar progress;


    private boolean intialStage = true;
    FloatingActionButton floatingActionButton;

    RecyclerView recyclerView ;
    LinearLayoutManager linearLayoutManager;
    int color;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast);


        registerReceiver(downloadCompleteReceiver, downloadCompleteIntentFilter);
        floatingActionButton=(FloatingActionButton) findViewById(R.id.myFAB);
        recyclerView=(RecyclerView)findViewById(R.id.list_podcast);
        progress=(ProgressBar) findViewById(R.id.progress);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        error =(TextView) findViewById(R.id.error);
        setSupportActionBar(toolbar);
        color=getIntent().getIntExtra(MainActivity.COLOR,0);
        toolbar.setBackgroundColor(color);
        String name=getIntent().getStringExtra("tittle");
        toolbar.setTitle(name);
        lang=getIntent().getCharExtra(BookActivity.lang, 'X');


        if(dataPodcast==null)
        {
            downloadMusicData();
        }

    }

    private void downloadMusicData() {

        NetworkRequest.SimpleJsonRequest(this, new JSONObject(), NetworkRequest.SongSrc + "?index=1&language="+ CommanMethod.languageCode(lang), this, RequestCodePodcast, 1);
    }



    @Override
    public void success(JSONArray response, int responseCode) {

        if(RequestCodePodcast==responseCode)
        {
            System.out.println(response.toString());

            Type listType = new TypeToken<List<Music>>(){}.getType();
            dataPodcast=NetworkRequest.gson.fromJson(response.toString(),listType);
            if(dataPodcast.size()>0)
            setData();
            else
                setError();
        }
    }
    @Override
    public void failer(VolleyError response, int responseCode) {

        setError();
    }

    public void setError() {
        progress.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        error.setVisibility(View.VISIBLE);
    }

    private void setData() {
        progress.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        PodcastAdapter storyAdapter = new PodcastAdapter(this, dataPodcast,lang);
        recyclerView.setAdapter(storyAdapter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(downloadCompleteReceiver);
        super.onDestroy();
    }
}
