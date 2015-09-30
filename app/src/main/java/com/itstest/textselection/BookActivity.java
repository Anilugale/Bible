package com.itstest.textselection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.itstest.textselection.adapter.BookAdapter;
import com.itstest.textselection.database.DatabaseHelper;
import com.itstest.textselection.model.Chapter;

import java.io.IOException;
import java.util.List;

public class BookActivity extends AppCompatActivity {

    RecyclerView recyclerView ;
    LinearLayoutManager  linearLayoutManager;
    char langugae;
    int color;

    public static String lang="lang";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        recyclerView=(RecyclerView)findViewById(R.id.list_chapter);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
color=getIntent().getIntExtra(MainActivity.COLOR,0);
        toolbar.setBackgroundColor(color);

         langugae = getIntent().getCharExtra(lang,'x');

        DatabaseHelper db=new DatabaseHelper(this);
        try {
            List<Chapter> dataStory =db.getDataBook(langugae);
            if(dataStory.size()>0){
            BookAdapter storyAdapter = new BookAdapter(this, dataStory,langugae,color);
            recyclerView.setAdapter(storyAdapter);
        }
        else {
            Toast.makeText(this, "Error in loading please try again...", Toast.LENGTH_SHORT).show();
            finish();
        }

        } catch (IOException e) {
            e.printStackTrace();
        }
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chapter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch(item.getItemId())
        {
            case R.id.podcast:
                startActivity(new Intent(this,PodcastActivity.class).putExtra(lang,langugae));
                break;
            case R.id.bookmark:
                startActivity(new Intent(this,BookmarkActivity.class).putExtra(lang,langugae)
                .putExtra(MainActivity.COLOR, color));
                break;
        }
        return true;
    }
}
