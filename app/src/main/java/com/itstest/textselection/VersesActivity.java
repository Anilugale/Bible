package com.itstest.textselection;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

    LinearLayoutManager linearLayoutManager;
    List<Verse> dataStory;
    int bookId,chapterId;
    char lang;
    String name;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verses);
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        name=getIntent().getStringExtra(BookActivity.book_name);
        final int color=getIntent().getIntExtra(MainActivity.COLOR, 0);
        toolbar.setBackgroundColor(color);
        bookId=getIntent().getIntExtra(BOOK_ID, 0);
        chapterId=getIntent().getIntExtra(CHAPTER_ID,0);
        lang=getIntent().getCharExtra(BookActivity.lang, 'X');
        V_ID=getIntent().getIntExtra("V_ID", 0);
        recyclerView=(RecyclerView)findViewById(R.id.list_verses);
  /*      searchEdt=(EditText) findViewById(R.id.searchEdt);

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
        });*/
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
                        Intent a = new Intent(VersesActivity.this,MainActivity.class);
                        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(a);
                        break;
                    case R.id.music:
                        startActivity(new Intent(VersesActivity.this, PodcastActivity1.class).putExtra(BookActivity.lang, lang)
                                .putExtra(MainActivity.COLOR, color));
                        Toast.makeText(getApplicationContext(), "music", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.bookmark:
                        startActivity(new Intent(VersesActivity.this, BookmarkActivity.class).putExtra(BookActivity.lang, lang)
                                .putExtra(MainActivity.COLOR, color));
                        Toast.makeText(getApplicationContext(),"bookmark",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.bible:
                        /*startActivity(new Intent(BookActivity.this, BookActivity.class).putExtra(BookActivity.lang, lang)
                                .putExtra(MainActivity.COLOR, color));
                        Toast.makeText(getApplicationContext(),"bookmark",Toast.LENGTH_SHORT).show();*/
                        Toast.makeText(getApplicationContext(),"bible",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.search:
                        startActivity(new Intent(VersesActivity.this, SearchActivity.class).putExtra(BookActivity.lang, lang)
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
                    Toast.makeText(this, name+" "+dataStory.get(0).getChapter_id()+" Saved", Toast.LENGTH_SHORT).show();
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
