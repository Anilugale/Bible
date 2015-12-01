package com.itstest.textselection.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
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
import com.itstest.textselection.VersesActivity;
import com.itstest.textselection.database.DatabaseHelper;
import com.itstest.textselection.model.Verse;

/**
 Created by Anil Ugale on 16/09/2015.
 */
public class ShareDialog extends DialogFragment implements View.OnClickListener {

    private TextView verseName;
    CardView share,bookmark;
    private Verse verse;
    ImageView bookmark_icon;
    char lang;
    int position;
    DatabaseHelper db;

    public ShareDialog() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        verseName = (TextView) view.findViewById(R.id.verses_name);
        share = (CardView) view.findViewById(R.id.share);
        bookmark = (CardView) view.findViewById(R.id.bookmark);
        bookmark_icon = (ImageView) view.findViewById(R.id.bookmark_icon);
        share.setOnClickListener(this);
        bookmark.setOnClickListener(this);
        db =new DatabaseHelper(getActivity());
        verseName.setText(verse.getName());
        if(lang=='M') {
            if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                Typeface type = Typeface.createFromAsset(getActivity().getAssets(), "m.ttf");
                verseName.setTypeface(type);
            }
        }

        if (verse.getBookmar()==1) {
            bookmark_icon.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.mipmap.ic_bookmark1));
        }
        verseName.setCustomSelectionActionModeCallback(new android.view.ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
                Log.d("Anil", "onCreateActionMode");
                menu.removeItem(android.R.id.selectAll);
                menu.removeItem(android.R.id.copy);
                menu.clear();
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.style, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) { return false;     }

            @Override
            public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
                int start = verseName.getSelectionStart();
                int end = verseName.getSelectionEnd();
                SpannableStringBuilder ssb = new SpannableStringBuilder(verseName.getText());

                switch (item.getItemId()) {

                    case R.id.hightlight:
                        verseName.setText(ssb);
                        ssb.setSpan(new BackgroundColorSpan(ContextCompat.getColor(getActivity(), R.color.holo_orange_light)),
                                start,
                                end,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        verseName.setText(ssb);
                        db=new DatabaseHelper(getActivity());
                        db.updateHightLight(lang, verse.getId(), start, end,1);
                        verse.setStart(start);
                        verse.setEnd(end);
                        ((VersesActivity)getActivity()).onResumeList(position,1);
                        db.close();

                    break;
                    case R.id.hightlight1:
                        verseName.setText(ssb);
                        ssb.setSpan(new BackgroundColorSpan(ContextCompat.getColor(getActivity(), R.color.fifth)),
                                start,
                                end,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        verseName.setText(ssb);
                        db=new DatabaseHelper(getActivity());
                        db.updateHightLight(lang, verse.getId(), start, end,2);
                        verse.setStart(start);
                        verse.setEnd(end);
                        ((VersesActivity)getActivity()).onResumeList(position,2);
                        db.close();

                    break;
                    case R.id.hightlight2:
                        verseName.setText(ssb);
                        ssb.setSpan(new BackgroundColorSpan(ContextCompat.getColor(getActivity(), R.color.six)),
                                start,
                                end,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        verseName.setText(ssb);
                        db=new DatabaseHelper(getActivity());
                        db.updateHightLight(lang, verse.getId(), start, end,3);
                        verse.setStart(start);
                        verse.setEnd(end);
                        ((VersesActivity)getActivity()).onResumeList(position,3);
                        db.close();

                        break;
                }
                return false;
            }
            @Override
            public void onDestroyActionMode(android.view.ActionMode mode) { }
        });
        return view;
    }

    public void setData(Verse verse,char lang,int position ) {

        this.verse=verse;
        this.lang=lang;
        this.position=position;

    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.share:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, verse.getName());
                shareIntent.setType("text/plain");
                getActivity().startActivity(shareIntent);
                break;

            case  R.id.bookmark:
                Toast.makeText(getActivity(), "Verse Saved", Toast.LENGTH_SHORT).show();
                int update=0;
                if(verse.getBookmar()==0)
                    update=1;
                db=new DatabaseHelper(getActivity());
                db.updateBookmark(lang, verse.getId(), update);
                verse.setBookmar(update);
                db.close();
                this.dismiss();
                break;
        }

    }


}