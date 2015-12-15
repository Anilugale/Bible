package com.itstest.textselection.adapter;


import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itstest.textselection.BookActivity;
import com.itstest.textselection.ChapterActivity;
import com.itstest.textselection.MainActivity;
import com.itstest.textselection.R;
import com.itstest.textselection.SearchActivity;
import com.itstest.textselection.VersesActivity;
import com.itstest.textselection.model.Search;
import com.itstest.textselection.model.Verse;

import java.util.ArrayList;
import java.util.List;


/**
 Created by Anil Ugale on 01-07-2015.
 */


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    SearchActivity context;
    public  List<Search> mLst;
    List<Search> mLst_bk;

    private char lang;
    private int lastPosition = -1;
    private int color;

    public SearchAdapter(SearchActivity context, List<Search> par, char lang, int color) {
        this.context = context;
        this.mLst = par;
        this.mLst_bk=new ArrayList<>();
        this.mLst_bk.addAll(mLst);
        this.lang=lang;

        this.color=color;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search, parent,false);
        return  new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.gdName.setCustomSelectionActionModeCallback(null);




        holder.gdName.setText(mLst.get(position).getChapterName() + " - Chapter > " + mLst.get(position).getChapter_id()+
        "Verse >"+mLst.get(position).getVerse_id());
        holder.verses_name.setText( mLst.get(position).getVerse_text());
        if(lang=='M') {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                Typeface type = Typeface.createFromAsset(context.getAssets(), "m.ttf");
                holder.gdName.setTypeface(type);
            }
        }

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, VersesActivity.class)
                        .putExtra(VersesActivity.BOOK_ID,mLst.get(position).getBook_id())
                        .putExtra(BookActivity.lang, lang)
                        .putExtra(MainActivity.COLOR, color)
                        .putExtra("V_ID",  mLst.get(position).getVerse_id())
                        .putExtra(ChapterActivity.CHAPTER_ID, mLst.get(position).getChapter_id()));
                context.finish();

            }
        });

    }


    @Override
    public int getItemCount() {
        return mLst.size();
    }



    static class ViewHolder extends  RecyclerView.ViewHolder
    {
        protected TextView gdName,verses_name;
        protected CardView relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            relativeLayout=(CardView) itemView.findViewById(R.id.goodies_list_item);
            gdName = (TextView) itemView.findViewById(R.id.gdName);
            verses_name = (TextView) itemView.findViewById(R.id.verses_name);


        }
    }





}
