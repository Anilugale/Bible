package com.itstest.textselection;

import android.app.ProgressDialog;
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
import com.itstest.textselection.model.Search;
import com.itstest.textselection.util.CommanMethod;

import java.io.IOException;
import java.util.List;

public class BookActivity extends AppCompatActivity {

    RecyclerView recyclerView ;
    LinearLayoutManager  linearLayoutManager;
    char langugae;
    int color;
    boolean ischapter_book,isVerses_BookMark;

    public static String lang="lang";
    public static String isChapterBookmark="isChapterBookmark";
    public static String isVersesBookMark="isVersesBookMark";
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

        langugae = getIntent().getCharExtra(lang, 'x');
        ischapter_book = getIntent().getBooleanExtra(isChapterBookmark, false);
        isVerses_BookMark = getIntent().getBooleanExtra(isVersesBookMark,false);



      loadData();
        if(ischapter_book)
        {
            startActivity(new Intent(this, ChapterActivity.class)
                    .putExtra(VersesActivity.BOOK_ID, CommanMethod.bookmarkCahpter.getBook_id()
                    ).putExtra(MainActivity.COLOR, color)
                    .putExtra(isChapterBookmark,true)
                    .putExtra(BookActivity.lang, lang));
        }
        if(isVerses_BookMark)
        {
           /* startActivity(new Intent(this, ChapterActivity.class)
                    .putExtra(VersesActivity.BOOK_ID, CommanMethod.ver.getBook_id()
                    ).putExtra(MainActivity.COLOR, color)
                    .putExtra(isChapterBookmark,true)
                    .putExtra(BookActivity.lang, lang));*/
        }
    }

    private void loadData() {
      final   ProgressDialog pd=ProgressDialog.show(this,"","Loading...",true,false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                DatabaseHelper db=new DatabaseHelper(BookActivity.this);
                try {
                   final  List<Chapter> dataStory =db.getDataBook(langugae);


                    BookActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(dataStory.size()>0){
                                BookAdapter storyAdapter = new BookAdapter(BookActivity.this, dataStory,langugae,color);
                                recyclerView.setAdapter(storyAdapter);

                            }
                            else {
                                Toast.makeText(BookActivity.this, "Error in loading please try again...", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            pd.dismiss();
                        }
                    });


                } catch (IOException e) {
                    BookActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            finish();
                        }
                    });

                    e.printStackTrace();
                }
                db.close();
            }
        }).start();

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
                startActivity(new Intent(this,SearchActivity.class).putExtra(lang,langugae)
                .putExtra(MainActivity.COLOR, color)
                );
                break;
            case R.id.bookmark:
                startActivity(new Intent(this,BookmarkActivity.class).putExtra(lang,langugae)
                        .putExtra(MainActivity.COLOR, color));
                break;
        }
        return true;
    }
}
