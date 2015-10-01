package com.itstest.textselection;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ChooseActivity extends AppCompatActivity implements View.OnClickListener{

    CardView bible,podcast;
    int color;
    char lang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        color=getIntent().getIntExtra(MainActivity.COLOR,0);
        toolbar.setBackgroundColor(color);
        String name=getIntent().getStringExtra("tittle");
        toolbar.setTitle(name);
        color=getIntent().getIntExtra(MainActivity.COLOR,0);
                toolbar.setBackgroundColor(color);

         lang=getIntent().getCharExtra(BookActivity.lang, 'X');

        bible=(CardView) findViewById(R.id.bible);
        podcast=(CardView) findViewById(R.id.podcast);

        bible.setOnClickListener(this);
        podcast.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {

            case R.id.bible:
                startActivity(new Intent(this, BookActivity.class).putExtra(BookActivity.lang, lang)
                        .putExtra(MainActivity.COLOR,color));
                break;
            case R.id.podcast:
                startActivity(new Intent(this,PodcastActivity.class).putExtra(BookActivity.lang,lang));
                break;


        }

    }
}
