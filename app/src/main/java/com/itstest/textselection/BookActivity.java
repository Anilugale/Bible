package com.itstest.textselection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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


    public static String lang="lang";
    public static String book_name="book_name";
    public static String isChapterBookmark="isChapterBookmark";
    public static String isVersesBookMark="isVersesBookMark";

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    Toolbar toolbar;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        recyclerView=(RecyclerView)findViewById(R.id.list_chapter);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
         toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        color=getIntent().getIntExtra(MainActivity.COLOR,0);
        toolbar.setBackgroundColor(color);
        toolbar.setTitle("Book");
        langugae = getIntent().getCharExtra(lang, 'x');


      loadData();
        if(CommanMethod.ischapter_book && CommanMethod.bookmarkCahpter!=null)
        {
            startActivity(new Intent(this, ChapterActivity.class)
                    .putExtra(VersesActivity.BOOK_ID, CommanMethod.bookmarkCahpter.getBook_id()
                    ).putExtra(MainActivity.COLOR, color)

                    .putExtra(BookActivity.lang, langugae));
            finish();
        }
        if(CommanMethod.isVerses_BookMark&&CommanMethod.versesBookmark!=null)
        {
           startActivity(new Intent(this, ChapterActivity.class)
                    .putExtra(VersesActivity.BOOK_ID, CommanMethod.versesBookmark.getBook_id()
                    ).putExtra(MainActivity.COLOR, color)

                    .putExtra(BookActivity.lang, langugae));
            finish();

        }
    }

    private void loadData() {
      final   ProgressDialog pd=ProgressDialog.show(this,"","Loading...",true,false);
        sp=getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE);
        sp.edit().putBoolean("isdbopen",true).apply();

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
                        Intent a = new Intent(BookActivity.this,MainActivity.class);
                        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(a);
                        finish();
                        break;
                    case R.id.music:
                        startActivity(new Intent(BookActivity.this, PodcastActivity1.class).putExtra(BookActivity.lang, lang)
                                .putExtra(MainActivity.COLOR, color));
                        Toast.makeText(getApplicationContext(), "music", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case R.id.bookmark:
                        startActivity(new Intent(BookActivity.this, BookmarkActivity.class).putExtra(BookActivity.lang, lang)
                                .putExtra(MainActivity.COLOR, color));
                        Toast.makeText(getApplicationContext(),"bookmark",Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case R.id.bible:
                        /*startActivity(new Intent(BookActivity.this, BookActivity.class).putExtra(BookActivity.lang, lang)
                                .putExtra(MainActivity.COLOR, color));
                        Toast.makeText(getApplicationContext(),"bookmark",Toast.LENGTH_SHORT).show();*/
                        Toast.makeText(getApplicationContext(),"bible",Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case R.id.search:
                        startActivity(new Intent(BookActivity.this, SearchActivity.class).putExtra(BookActivity.lang, lang)
                                .putExtra(MainActivity.COLOR, color));
                        Toast.makeText(getApplicationContext(),"search",Toast.LENGTH_SHORT).show();
                        finish();
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
