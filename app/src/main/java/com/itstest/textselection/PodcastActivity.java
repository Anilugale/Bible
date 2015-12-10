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
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    public static String ID="id";
    String id;
    int RequestCodePodcast=121212;
    List<Music> dataPodcast;
 //   TextView error;
    ProgressDialog pd;

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

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

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
        //floatingActionButton=(FloatingActionButton) findViewById(R.id.myFAB);
        recyclerView=(RecyclerView)findViewById(R.id.list_podcast);

        pd=ProgressDialog.show(this,"","Please Wait. fetching devotions..",true,false);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Devotions");
        setSupportActionBar(toolbar);
        color=getIntent().getIntExtra(MainActivity.COLOR,0);
        toolbar.setBackgroundColor(color);
        String name=getIntent().getStringExtra("tittle");
        id=getIntent().getStringExtra(ID);

        lang=getIntent().getCharExtra(BookActivity.lang, 'X');


        if(dataPodcast==null)
        {
            downloadMusicData();
        }
        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){

                    case R.id.language:
                        Toast.makeText(getApplicationContext(),"language",Toast.LENGTH_SHORT).show();
                        Intent a = new Intent(PodcastActivity.this,MainActivity.class);
                        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(a);
                        break;
                    case R.id.music:
             /*           startActivity(new Intent(PodcastActivity1.this, PodcastActivity1.class).putExtra(BookActivity.lang, lang)
                                .putExtra(MainActivity.COLOR, color));
                        Toast.makeText(getApplicationContext(), "music", Toast.LENGTH_SHORT).show();*/
                        break;
                    case R.id.bookmark:
                        startActivity(new Intent(PodcastActivity.this, BookmarkActivity.class).putExtra(BookActivity.lang, lang)
                                .putExtra(MainActivity.COLOR, color));
                        Toast.makeText(getApplicationContext(),"bookmark",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.bible:
                        startActivity(new Intent(PodcastActivity.this, BookActivity.class).putExtra(BookActivity.lang, lang)
                                .putExtra(MainActivity.COLOR, color));
                        Toast.makeText(getApplicationContext(),"bible",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.search:
                        startActivity(new Intent(PodcastActivity.this, SearchActivity.class).putExtra(BookActivity.lang, lang)
                                .putExtra(MainActivity.COLOR, color));
                        Toast.makeText(getApplicationContext(),"search",Toast.LENGTH_SHORT).show();

                        break;

                    default:
                        Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();


                }
                return true;
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.font1, R.string.font1){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    private void downloadMusicData() {

        NetworkRequest.SimpleJsonRequest(this, new JSONObject(), NetworkRequest.SongSrc + "?index=1&id="+id+"&language="+ CommanMethod.languageCode(lang), this, RequestCodePodcast, 1);
    }



    @Override
    public void success(JSONArray response, int responseCode) {

        pd.dismiss();
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
        pd.dismiss();
        setError();
        response.printStackTrace();
    }

    public void setError() {

        recyclerView.setVisibility(View.GONE);
        findViewById(R.id.error).setVisibility(View.VISIBLE);
    }

    private void setData() {

        recyclerView.setVisibility(View.VISIBLE);
        PodcastAdapter storyAdapter = new PodcastAdapter(this, dataPodcast,lang,color);
        recyclerView.setAdapter(storyAdapter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(downloadCompleteReceiver);
        super.onDestroy();
    }
}
