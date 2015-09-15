package com.itstest.textselection;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Splash extends AppCompatActivity {
    Handler handler = new Handler();
    Runnable wait= new Runnable() {
        @Override
        public void run() {

            Intent intent = new Intent(Splash.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        handler.postDelayed(wait, 2000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(wait);
        finish();
    }
}
