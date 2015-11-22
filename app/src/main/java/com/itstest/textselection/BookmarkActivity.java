package com.itstest.textselection;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.itstest.textselection.adapter.BookmarkAdapter;
import com.itstest.textselection.adapter.PagerAdapter;
import com.itstest.textselection.database.DatabaseHelper;
import com.itstest.textselection.model.Verse;

import java.util.List;

public class BookmarkActivity extends AppCompatActivity {


    private BookmarkAdapter adapter;
    RecyclerView recyclerView ;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        String name=getIntent().getStringExtra("tittle");
        toolbar.setTitle(name);
        int color=getIntent().getIntExtra(MainActivity.COLOR, 0);
        toolbar.setBackgroundColor(color);
        setSupportActionBar(toolbar);
        char lang=getIntent().getCharExtra(BookActivity.lang, 'X');

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setBackgroundColor(color);
        tabLayout.addTab(tabLayout.newTab().setText("Chapter Bookmark"));
        tabLayout.addTab(tabLayout.newTab().setText("Verses Bookmark"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(),lang,color);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



       /* recyclerView=(RecyclerView)findViewById(R.id.list_verses);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

         int color=getIntent().getIntExtra(MainActivity.COLOR,0);
        toolbar.setBackgroundColor(color);

        DatabaseHelper db=new DatabaseHelper(this);
        try {
            List<Verse> databookmark= db.getBookMarkVerse(lang);
            adapter=new BookmarkAdapter(this,databookmark,lang,color);
            recyclerView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
*/


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
                adapter.setFont(1);
                adapter.notifyDataSetChanged();
                break;
            case R.id.font2:
                adapter.setFont(2);
                adapter.notifyDataSetChanged();
                break;
            case R.id.font3:
                adapter.setFont(3);
                adapter.notifyDataSetChanged();
                break;

        }
        return  true;
    }



}
