package com.itstest.textselection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.itstest.textselection.adapter.ChapterAdapter;
import com.itstest.textselection.adapter.StoryAdapter;
import com.itstest.textselection.database.DatabaseHelper;
import com.itstest.textselection.model.Chapter;
import com.itstest.textselection.model.Story;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChapterActivity extends AppCompatActivity {

    RecyclerView recyclerView ;
    LinearLayoutManager  linearLayoutManager;

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

       char langugae = getIntent().getCharExtra(lang,'x');

        DatabaseHelper db=new DatabaseHelper(this);
        try {
            List<Chapter> dataStory =db.getDataBook(langugae);
            ChapterAdapter storyAdapter = new ChapterAdapter(this, dataStory,langugae);
            recyclerView.setAdapter(storyAdapter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chapter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
