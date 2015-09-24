package com.itstest.textselection.adapter;


import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.itstest.textselection.BookActivity;
import com.itstest.textselection.ChapterActivity;
import com.itstest.textselection.R;
import com.itstest.textselection.VersesActivity;
import com.itstest.textselection.fragment.ShareDialog;
import com.itstest.textselection.model.Verse;

import java.util.ArrayList;
import java.util.List;


/**
 Created by Anil Ugale on 01-07-2015.
 */


public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ViewHolder> {

    ChapterActivity context;
    public  List<Verse> mLst;
    List<Verse> mLst_bk;
    private int book_id;
    private char lang;
    private int lastPosition = -1;

    public ChapterAdapter(ChapterActivity context, List<Verse> par, char lang,int book_id) {
        this.context = context;
        this.mLst = par;
        this.mLst_bk=new ArrayList<>();
        this.mLst_bk.addAll(mLst);
        this.lang=lang;
        this.book_id=book_id;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chapter_1, parent,false);
        return  new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.gdName.setCustomSelectionActionModeCallback(null);




        holder.gdName.setText(getChapterTrans(lang) + " > " + mLst.get(position).getId());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, VersesActivity.class)
                        .putExtra(VersesActivity.BOOK_ID, book_id)
                        .putExtra(BookActivity.lang, lang)
                        .putExtra(ChapterActivity.CHAPTER_ID, mLst.get(position).getId()));
            }
        });
        setAnimation(holder.relativeLayout,position);
    }

    private String getChapterTrans(char lang) {

        switch (lang)
        {
            case 'M':
                return "അധ്യായത്തിൽ";
            default:
                return "Chapter";
        }
    }

    @Override
    public int getItemCount() {
        return mLst.size();
    }



    static class ViewHolder extends  RecyclerView.ViewHolder
    {


        protected TextView gdName;

        protected CardView relativeLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            relativeLayout=(CardView) itemView.findViewById(R.id.goodies_list_item);
            gdName = (TextView) itemView.findViewById(R.id.gdName);


        }
    }


    private void setAnimation(View viewToAnimate, int position)
    {

            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            animation.setDuration(600);
            viewToAnimate.startAnimation(animation);


    }


}
