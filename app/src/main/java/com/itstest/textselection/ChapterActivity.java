package com.itstest.textselection;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.itstest.textselection.adapter.BookAdapter;
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
    String name;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaper);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        name=getIntent().getStringExtra(BookActivity.book_name);
        int bookId=getIntent().getIntExtra(BOOK_ID, 0);
        final int color=getIntent().getIntExtra(MainActivity.COLOR, 0);
        toolbar.setBackgroundColor(color);

       final char lang=getIntent().getCharExtra(BookActivity.lang, 'X');
        recyclerView=(RecyclerView)findViewById(R.id.list_verses);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);



        DatabaseHelper db=new DatabaseHelper(this);
        try {

            List<Verse> dataStory =db.getChapter(bookId,lang);
            if(dataStory.size()>0){
                storyAdapter = new ChapterAdapter(this, dataStory,lang,bookId,getIntent().getIntExtra(MainActivity.COLOR,0),name);
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




        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){

                    case R.id.language:
                        Toast.makeText(getApplicationContext(),"language",Toast.LENGTH_SHORT).show();
                        Intent a = new Intent(ChapterActivity.this,MainActivity.class);
                        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(a);
                        break;
                    case R.id.music:
                        startActivity(new Intent(ChapterActivity.this, PodcastActivity1.class).putExtra(BookActivity.lang, lang)
                                .putExtra(MainActivity.COLOR, color));
                                  Toast.makeText(getApplicationContext(), "music", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.bookmark:
                        startActivity(new Intent(ChapterActivity.this, BookmarkActivity.class).putExtra(BookActivity.lang, lang)
                                .putExtra(MainActivity.COLOR, color));
                        Toast.makeText(getApplicationContext(),"bookmark",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.bible:
                        startActivity(new Intent(ChapterActivity.this, BookActivity.class).putExtra(BookActivity.lang, lang)
                                .putExtra(MainActivity.COLOR, color));
                        Toast.makeText(getApplicationContext(),"bookmark",Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),"bible",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.search:
                        startActivity(new Intent(ChapterActivity.this, SearchActivity.class).putExtra(BookActivity.lang, lang)
                                .putExtra(MainActivity.COLOR, color));
                        Toast.makeText(getApplicationContext(),"search",Toast.LENGTH_SHORT).show();

                        break;

                    default:
                        Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();


                }
                return true;
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.font1, R.string.font1){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }






}
