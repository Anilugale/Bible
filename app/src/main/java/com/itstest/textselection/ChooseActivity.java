package com.itstest.textselection;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseActivity extends AppCompatActivity implements View.OnClickListener{

    CardView bible,podcast;
    int color;
    char lang;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        color=getIntent().getIntExtra(MainActivity.COLOR,0);
        toolbar.setBackgroundColor(color);
        String name=getIntent().getStringExtra("tittle");
        toolbar.setTitle("Bible");


         lang=getIntent().getCharExtra(BookActivity.lang, 'X');

        bible=(CardView) findViewById(R.id.bible);
        podcast=(CardView) findViewById(R.id.podcast);

        ((TextView)findViewById(R.id.podcast_text)).setTextColor(color);
        ((TextView)findViewById(R.id.bible_text)).setTextColor(color);



        bible.setOnClickListener(this);
        podcast.setOnClickListener(this);



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
                        Intent a = new Intent(ChooseActivity.this,MainActivity.class);
                        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(a);
                        finish();
                        break;
                    case R.id.music:
                        startActivity(new Intent(ChooseActivity.this, PodcastActivity1.class).putExtra(BookActivity.lang, lang)
                                .putExtra(MainActivity.COLOR, color));
                        Toast.makeText(getApplicationContext(), "music", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case R.id.bookmark:
                        startActivity(new Intent(ChooseActivity.this, BookmarkActivity.class).putExtra(BookActivity.lang, lang)
                                .putExtra(MainActivity.COLOR, color));
                        Toast.makeText(getApplicationContext(),"bookmark",Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case R.id.bible:
                        startActivity(new Intent(ChooseActivity.this, BookActivity.class).putExtra(BookActivity.lang, lang)
                                .putExtra(MainActivity.COLOR, color));

                        Toast.makeText(getApplicationContext(),"bible",Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case R.id.search:
                        startActivity(new Intent(ChooseActivity.this, SearchActivity.class).putExtra(BookActivity.lang, lang)
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
    public void onClick(View v) {

        switch (v.getId())
        {

            case R.id.bible:
                startActivity(new Intent(this, BookActivity.class).putExtra(BookActivity.lang, lang)
                        .putExtra(MainActivity.COLOR,color)
                );
                break;
            case R.id.podcast:
                startActivity(new Intent(this,PodcastActivity1.class).putExtra(BookActivity.lang,lang)
                        .putExtra(MainActivity.COLOR, color)
                );
                break;
        }

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
                startActivity(new Intent(this,SearchActivity.class).putExtra(BookActivity.lang,lang)
                                .putExtra(MainActivity.COLOR, color)
                );
                break;
            case R.id.bookmark:
                startActivity(new Intent(this,BookmarkActivity.class).putExtra(BookActivity.lang,lang)
                        .putExtra(MainActivity.COLOR, color));
                break;
        }
        finish();
        return true;
    }
}
