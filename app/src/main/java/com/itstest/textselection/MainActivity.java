package com.itstest.textselection;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


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
        findViewById(R.id.contact_us).setOnClickListener(this);
        findViewById(R.id.email_us).setOnClickListener(this);

        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {


        TextView telgu_char=(TextView) findViewById(R.id.telgu_char);
        TextView telgu_name=(TextView) findViewById(R.id.telgu_name);

        Typeface typeTelgu = Typeface.createFromAsset(getAssets(), "telgu.ttf");

        telgu_char.setTypeface(typeTelgu);
        telgu_name.setTypeface(typeTelgu);


        TextView malya_name=(TextView) findViewById(R.id.malya_name);
        TextView malya_char=(TextView) findViewById(R.id.malya_char);



        Typeface typeMalya = Typeface.createFromAsset(getAssets(), "m.ttf");
        malya_name.setTypeface(typeMalya);
        malya_char.setTypeface(typeMalya);

        TextView kannada_name=(TextView) findViewById(R.id.kannada_name);
        TextView kannada_char=(TextView) findViewById(R.id.kannada_char);



        Typeface typeKannad = Typeface.createFromAsset(getAssets(), "k.ttf");
        kannada_name.setTypeface(typeKannad);
        kannada_char.setTypeface(typeKannad);


        }
    }


    @Override
    public void onClick(View view) {

        switch(view.getId()) {

            case R.id.lang2:

                startActivity(new Intent(this, ChooseActivity.class)
                                .putExtra(BookActivity.lang, 'M')
                                .putExtra(MainActivity.COLOR, ContextCompat.getColor(this, R.color.second))
                );
                break;


            case  R.id.lang1:
                startActivity(new Intent(this, ChooseActivity.class)
                                .putExtra(MainActivity.COLOR, ContextCompat.getColor(this, R.color.first))
                );
                break;
            case  R.id.lang3:
                startActivity(new Intent(this, ChooseActivity.class)
                                .putExtra(BookActivity.lang, 'H')
                                .putExtra(MainActivity.COLOR, ContextCompat.getColor(this, R.color.third))
                );
                break;
            case  R.id.lang4:
                startActivity(new Intent(this, ChooseActivity.class)
                                .putExtra(BookActivity.lang, 'T')
                                .putExtra(MainActivity.COLOR, ContextCompat.getColor(this, R.color.fourth))
                );
                break;
            case  R.id.lang5:
                startActivity(new Intent(this, ChooseActivity.class)
                                .putExtra(BookActivity.lang, 'm')
                                .putExtra(MainActivity.COLOR, ContextCompat.getColor(this, R.color.fifth))
                );
                break;
            case  R.id.lang6:
                startActivity(new Intent(this, ChooseActivity.class)
                                .putExtra(BookActivity.lang, 't')
                                .putExtra(MainActivity.COLOR, ContextCompat.getColor(this, R.color.six))
                );
                break;
            case  R.id.lang7:
                startActivity(new Intent(this, ChooseActivity.class)
                                .putExtra(BookActivity.lang, 'K')
                                .putExtra(MainActivity.COLOR, ContextCompat.getColor(this, R.color.seven))
                );
                break;

            case  R.id.contact_us:

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+91  9822915522" ));
                startActivity(callIntent);
                break;
            case  R.id.email_us:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","ss12r06@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
                break;


        }

    }
}
