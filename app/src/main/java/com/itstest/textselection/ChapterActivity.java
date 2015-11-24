package com.itstest.textselection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.itstest.textselection.adapter.ChapterAdapter;
import com.itstest.textselection.adapter.VersesAdapter;
import com.itstest.textselection.database.DatabaseHelper;
import com.itstest.textselection.model.Verse;
import com.itstest.textselection.util.CommanMethod;

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
        setContentView(R.layout.activity_chaper);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        String name=getIntent().getStringExtra("tittle");
        int bookId=getIntent().getIntExtra(BOOK_ID, 0);
        toolbar.setBackgroundColor(getIntent().getIntExtra(MainActivity.COLOR,0));

        char lang=getIntent().getCharExtra(BookActivity.lang, 'X');
        recyclerView=(RecyclerView)findViewById(R.id.list_verses);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);




        DatabaseHelper db=new DatabaseHelper(this);
        try {

            List<Verse> dataStory =db.getChapter(bookId,lang);
            if(dataStory.size()>0){
                storyAdapter = new ChapterAdapter(this, dataStory,lang,bookId,getIntent().getIntExtra(MainActivity.COLOR,0));
                recyclerView.setAdapter(storyAdapter);
            }
            else {
                Toast.makeText(this, "Error in loading please try again...", Toast.LENGTH_SHORT).show();
                finish();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        db.close();

        if(CommanMethod.ischapter_book)
        {
            startActivity(new Intent(this, VersesActivity.class)
                    .putExtra(VersesActivity.BOOK_ID, CommanMethod.bookmarkCahpter.getBook_id())
                    .putExtra(BookActivity.lang, lang)
                    .putExtra(MainActivity.COLOR, getIntent().getIntExtra(MainActivity.COLOR,0))
                    .putExtra(ChapterActivity.CHAPTER_ID,CommanMethod.bookmarkCahpter.getChapter_id()));
            CommanMethod.bookmarkCahpter=null;
        }
        if(CommanMethod.isVerses_BookMark)
        {
            startActivity(new Intent(this, VersesActivity.class)
                    .putExtra(VersesActivity.BOOK_ID,CommanMethod.versesBookmark.getBook_id())
                    .putExtra(BookActivity.lang, lang)
                    .putExtra(MainActivity.COLOR, getIntent().getIntExtra(MainActivity.COLOR,0))
                    .putExtra(ChapterActivity.CHAPTER_ID,CommanMethod.versesBookmark.getChapter_id()));

        }
    }






}
