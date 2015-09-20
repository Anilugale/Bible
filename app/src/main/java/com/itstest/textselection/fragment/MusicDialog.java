package com.itstest.textselection.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itstest.textselection.R;
import com.itstest.textselection.database.DatabaseHelper;
import com.itstest.textselection.model.Podcast;
import com.itstest.textselection.model.Verse;

/**
 Created by Anil Ugale on 16/09/2015.
 */
public class MusicDialog extends DialogFragment implements View.OnClickListener {

    private TextView verseName;
    CardView share,bookmark;
    private Podcast podcast;
    ImageView bookmark_icon;
    char lang;
    DatabaseHelper db;

    public MusicDialog() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.music_dialog, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);


        return view;
    }

    public void setData(Podcast podcast,char lang) {

        this.podcast=podcast;
        this.lang=lang;

    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {

        }

    }


}