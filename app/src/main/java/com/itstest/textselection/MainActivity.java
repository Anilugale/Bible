package com.itstest.textselection;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.itstest.textselection.database.DatabaseHelper;
import com.itstest.textselection.model.Chapter;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener,View.OnClickListener {

    TextView testDemo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testDemo=(TextView)findViewById(R.id.testDemo) ;
        testDemo.setOnLongClickListener(this);
        findViewById(R.id.lang1).setOnClickListener(this);
        findViewById(R.id.lang2).setOnClickListener(this);
        findViewById(R.id.lang3).setOnClickListener(this);
        findViewById(R.id.lang4).setOnClickListener(this);
        findViewById(R.id.lang5).setOnClickListener(this);
        findViewById(R.id.podcast).setOnClickListener(this);
    }

    public void textselected(View view) {
        int startIndex = testDemo.getSelectionStart();
        int endIndex = testDemo.getSelectionEnd();
        String  textString =testDemo.getText().toString();
        Spannable spanText = Spannable.Factory.getInstance().newSpannable(textString);
        spanText.setSpan(new BackgroundColorSpan(Color.YELLOW), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        testDemo.setText(spanText);
        findViewById(R.id.btn).setVisibility(View.GONE);
    }

    @Override
    public boolean onLongClick(View view) {
        findViewById(R.id.btn).setVisibility(View.VISIBLE);
        return false;
    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.podcast)
        {
            startActivity(new Intent(this,PodcastActivity.class));
        }
        else if(view.getId()==R.id.lang2)
            startActivity(new Intent(this,ChapterActivity.class).putExtra(ChapterActivity.lang,'M'));
         else
            startActivity(new Intent(this,ChapterActivity.class));

    }
}
