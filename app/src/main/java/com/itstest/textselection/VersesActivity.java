package com.itstest.textselection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.itstest.textselection.adapter.VersesAdapter;
import com.itstest.textselection.database.DatabaseHelper;
import com.itstest.textselection.model.Verse;

import java.util.List;

public class VersesActivity extends AppCompatActivity {

    public static String BOOK_ID="bookId";
    public static String CHAPTER_ID="CHAPTER_ID";
    private VersesAdapter storyAdapter;

    RecyclerView recyclerView ;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verses);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        String name=getIntent().getStringExtra("tittle");
        toolbar.setBackgroundColor(getIntent().getIntExtra(MainActivity.COLOR,0));
        int bookId=getIntent().getIntExtra(BOOK_ID, 0);
        int chapterId=getIntent().getIntExtra(CHAPTER_ID,0);
        char lang=getIntent().getCharExtra(BookActivity.lang, 'X');
        recyclerView=(RecyclerView)findViewById(R.id.list_verses);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);


        DatabaseHelper db=new DatabaseHelper(this);
        try {
            List<Verse> dataStory =db.getVerses(bookId,chapterId,lang);
            if(dataStory.size()>0) {
                storyAdapter = new VersesAdapter(this, dataStory, lang);
                recyclerView.setAdapter(storyAdapter);
            }
            else {
                Toast.makeText(VersesActivity.this, "Error in loading please try again...", Toast.LENGTH_SHORT).show();
                finish();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();



    }

    @Override
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
    }



    public void onResumeList() {
        storyAdapter.notifyDataSetChanged();
    }
}
