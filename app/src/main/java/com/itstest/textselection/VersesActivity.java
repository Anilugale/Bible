package com.itstest.textselection;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.ClipboardManager;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.itstest.textselection.adapter.ChapterAdapter;
import com.itstest.textselection.adapter.VersesAdapter;
import com.itstest.textselection.database.DatabaseHelper;
import com.itstest.textselection.fragment.ColorDialogBox;
import com.itstest.textselection.model.Chapter;
import com.itstest.textselection.model.Verse;

import java.io.IOException;
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
        int bookId=getIntent().getIntExtra(BOOK_ID, 0);
        int chapterId=getIntent().getIntExtra(CHAPTER_ID,0);
        char lang=getIntent().getCharExtra(ChapterActivity.lang, 'X');
        recyclerView=(RecyclerView)findViewById(R.id.list_verses);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);


        DatabaseHelper db=new DatabaseHelper(this);
        try {
            List<Verse> dataStory =db.getVerses(bookId,chapterId,lang);
            storyAdapter = new VersesAdapter(this, dataStory);
            recyclerView.setAdapter(storyAdapter);


            List<Verse> databookmark= db.getBookMarkVerse(lang);
            for (Verse verse:databookmark)
            {
                System.out.println(verse.getName());
                System.out.println(verse.getId());
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

   public TextView edtxt;
   public int position;

    public void onBrush(View view) {
        int startIndex = edtxt.getSelectionStart();
        int endIndex = edtxt.getSelectionEnd();
        String  textString =edtxt.getText().toString();
        System.out.println(startIndex+""+endIndex);

        FragmentManager fm = getSupportFragmentManager();
        ColorDialogBox editNameDialog = ColorDialogBox.newInstance();

        editNameDialog.setData(edtxt,storyAdapter,position);
        editNameDialog.show(fm,null);
    }
}
