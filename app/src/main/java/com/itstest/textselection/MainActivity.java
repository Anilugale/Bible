package com.itstest.textselection;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener,View.OnClickListener {

    TextView testDemo;
    public static String COLOR="Color";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.lang1).setOnClickListener(this);
        findViewById(R.id.lang2).setOnClickListener(this);
        findViewById(R.id.lang3).setOnClickListener(this);
        findViewById(R.id.lang4).setOnClickListener(this);
        findViewById(R.id.lang5).setOnClickListener(this);
        findViewById(R.id.lang6).setOnClickListener(this);
        findViewById(R.id.lang7).setOnClickListener(this);

    }

    public void textselected(View view) {
        int startIndex = testDemo.getSelectionStart();
        int endIndex = testDemo.getSelectionEnd();
        String  textString =testDemo.getText().toString();
        Spannable spanText = Spannable.Factory.getInstance().newSpannable(textString);
        spanText.setSpan(new BackgroundColorSpan(Color.YELLOW), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        testDemo.setText(spanText);

    }

    @Override
    public boolean onLongClick(View view) {

        return false;
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()) {

            case R.id.lang2:

                startActivity(new Intent(this, BookActivity.class).putExtra(BookActivity.lang, 'M')
                                .putExtra(MainActivity.COLOR, ContextCompat.getColor(this, R.color.second))
                );
                break;


            case  R.id.lang1:
                startActivity(new Intent(this, BookActivity.class)
                                .putExtra(MainActivity.COLOR, ContextCompat.getColor(this, R.color.first))
                );
                break;
            case  R.id.lang3:
                startActivity(new Intent(this, BookActivity.class)
                                .putExtra(MainActivity.COLOR, ContextCompat.getColor(this, R.color.third))
                );
                break;
            case  R.id.lang4:
                startActivity(new Intent(this, BookActivity.class)
                                .putExtra(MainActivity.COLOR, ContextCompat.getColor(this, R.color.fourth))
                );
                break;
            case  R.id.lang5:
                startActivity(new Intent(this, BookActivity.class)
                                .putExtra(MainActivity.COLOR, ContextCompat.getColor(this, R.color.fifth))
                );
                break;
            case  R.id.lang6:
                startActivity(new Intent(this, BookActivity.class)
                                .putExtra(MainActivity.COLOR, ContextCompat.getColor(this, R.color.six))
                );
                break;
            case  R.id.lang7:
                startActivity(new Intent(this, BookActivity.class)
                                .putExtra(MainActivity.COLOR, ContextCompat.getColor(this, R.color.seven))
                );
                break;

        }

    }
}
