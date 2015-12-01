package com.itstest.textselection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itstest.textselection.adapter.ChapterAdapter;
import com.itstest.textselection.adapter.SearchAdapter;
import com.itstest.textselection.database.DatabaseHelper;
import com.itstest.textselection.model.Search;
import com.itstest.textselection.model.Verse;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    public static String BOOK_ID="bookId";
    public static String CHAPTER_ID="CHAPTER_ID";
    private SearchAdapter storyAdapter;
    EditText searchEdt;
    RecyclerView recyclerView ;
     DatabaseHelper db;
     char lang;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        String name=getIntent().getStringExtra("tittle");
        int bookId=getIntent().getIntExtra(BOOK_ID, 0);
        toolbar.setBackgroundColor(getIntent().getIntExtra(MainActivity.COLOR,0));

        lang=getIntent().getCharExtra(BookActivity.lang, 'X');
        recyclerView=(RecyclerView)findViewById(R.id.list_verses);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);
        searchEdt=(EditText) findViewById(R.id.searchEdt);
        db=new DatabaseHelper(this);

        searchEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search(searchEdt);
                    return true;
                }
                return false;
            }
        });

    }

    public void search(View view) {

        if(searchEdt.getText().length()>3) {
            List<Search> data = db.getSearch(lang, searchEdt.getText().toString().trim());

            if (data.size() > 0) {
                storyAdapter = new SearchAdapter(SearchActivity.this, data, lang, getIntent().getIntExtra(MainActivity.COLOR, 0));
                recyclerView.setAdapter(storyAdapter);
            }
        }
        else
            Toast.makeText(SearchActivity.this, "Enter At lest 3 characters", Toast.LENGTH_SHORT).show();
    }
}
