package com.itstest.textselection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.itstest.textselection.adapter.ChapterAdapter;
import com.itstest.textselection.adapter.VersesAdapter;
import com.itstest.textselection.database.DatabaseHelper;
import com.itstest.textselection.model.Verse;

import java.util.List;

public class ChapterActivity extends AppCompatActivity {

    public static String BOOK_ID="bookId";
    public static String CHAPTER_ID="CHAPTER_ID";
    private ChapterAdapter storyAdapter;

    RecyclerView recyclerView ;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verses);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        String name=getIntent().getStringExtra("tittle");
        int bookId=getIntent().getIntExtra(BOOK_ID, 0);

        char lang=getIntent().getCharExtra(BookActivity.lang, 'X');
        recyclerView=(RecyclerView)findViewById(R.id.list_verses);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);


        DatabaseHelper db=new DatabaseHelper(this);
        try {
            List<Verse> dataStory =db.getChapter(bookId,lang);
            storyAdapter = new ChapterAdapter(this, dataStory,lang,bookId);
            recyclerView.setAdapter(storyAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_verses, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.font1:
                storyAdapter.setFont(1);
                storyAdapter.notifyDataSetChanged();
                break;
            case R.id.font2:
                storyAdapter.setFont(2);
                storyAdapter.notifyDataSetChanged();
                break;
            case R.id.font3:
                storyAdapter.setFont(3);
                storyAdapter.notifyDataSetChanged();
                break;

        }
        return  true;
    }*/



    public void onResumeList() {
        storyAdapter.notifyDataSetChanged();
    }
}
