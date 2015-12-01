package com.itstest.textselection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.itstest.textselection.adapter.VersesAdapter;
import com.itstest.textselection.database.DatabaseHelper;
import com.itstest.textselection.model.Verse;
import com.itstest.textselection.util.CommanMethod;

import java.util.List;

public class VersesActivity extends AppCompatActivity {

    public static String BOOK_ID="bookId";
    public static String CHAPTER_ID="CHAPTER_ID";
    private VersesAdapter storyAdapter;
    int V_ID;
    RecyclerView recyclerView ;
    EditText searchEdt;
    LinearLayoutManager linearLayoutManager;
    List<Verse> dataStory;
    int bookId,chapterId;
    char lang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verses);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        String name=getIntent().getStringExtra("tittle");
        toolbar.setBackgroundColor(getIntent().getIntExtra(MainActivity.COLOR,0));
        bookId=getIntent().getIntExtra(BOOK_ID, 0);
        chapterId=getIntent().getIntExtra(CHAPTER_ID,0);
        lang=getIntent().getCharExtra(BookActivity.lang, 'X');
        V_ID=getIntent().getIntExtra("V_ID", 0);
        recyclerView=(RecyclerView)findViewById(R.id.list_verses);
        searchEdt=(EditText) findViewById(R.id.searchEdt);

        searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                storyAdapter.filter(searchEdt.getText().toString().trim());

            }
        });
        linearLayoutManager=new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);


        DatabaseHelper db=new DatabaseHelper(this);
        try {
            dataStory =db.getVerses(bookId,chapterId,lang);
            if(dataStory.size()>0) {
                storyAdapter = new VersesAdapter(this, dataStory, lang,getIntent().getIntExtra(MainActivity.COLOR,0));
                recyclerView.setAdapter(storyAdapter);
                V_ID--;
                recyclerView.scrollToPosition(V_ID);

            }
            else {
                Toast.makeText(VersesActivity.this, "Error in loading please try again...", Toast.LENGTH_SHORT).show();
                finish();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();


        if(CommanMethod.isVerses_BookMark && dataStory!=null)
        {
            int i=0;
            for (Verse v:dataStory) {

                if(CommanMethod.versesBookmark.getVerses_id()==v.getVerses_id())
                {
                    linearLayoutManager.scrollToPosition(i);
                    break;
                }
                i++;
            }
        }


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
            case R.id.bookmark:
                if(dataStory!=null) {
                    Toast.makeText(this, "marked as Chapter "+dataStory.get(0).getChapter_id()+" Saved", Toast.LENGTH_SHORT).show();
                    DatabaseHelper db = new DatabaseHelper(this);
                    if (dataStory != null)
                        db.makeChapterBookmark(bookId, chapterId, lang);

                    db.close();
                }
                break;

        }
        return  true;
    }



    public void onResumeList(int position,int color) {

        System.out.println(color+"color");
        dataStory.get(position).setColor(color);
        storyAdapter.notifyDataSetChanged();
    }
}
