package com.itstest.textselection.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.itstest.textselection.R;
import com.itstest.textselection.database.DatabaseHelper;
import com.itstest.textselection.model.Verse;

/**
 Created by Anil Ugale on 16/09/2015.
 */
public class ShareDialog extends DialogFragment implements View.OnClickListener {

    private TextView verseName;
    CardView share,bookmark;
    private Verse verse;

    public ShareDialog() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        verseName = (TextView) view.findViewById(R.id.verses_name);
        share = (CardView) view.findViewById(R.id.share);
        bookmark = (CardView) view.findViewById(R.id.bookmark);
        share.setOnClickListener(this);
        bookmark.setOnClickListener(this);
        verseName.setText(verse.getName());
        return view;
    }

    public void setData(Verse verse) {

        this.verse=verse;

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
                Toast.makeText(getActivity(), "Saving Bookmark...", Toast.LENGTH_SHORT).show();
                DatabaseHelper db=new DatabaseHelper(getActivity());

                int update=0;
                if(verse.getBookmar()==0)
                    update=1;
                db.updateBookmark('E',verse.getId(),update);
                verse.setBookmar(update);

                this.dismiss();
                break;
        }

    }
}