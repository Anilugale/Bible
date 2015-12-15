package com.itstest.textselection.adapter;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itstest.textselection.BookActivity;
import com.itstest.textselection.MainActivity;
import com.itstest.textselection.R;
import com.itstest.textselection.database.DatabaseHelper;
import com.itstest.textselection.model.ChapterBookmark;
import com.itstest.textselection.model.Verse;
import com.itstest.textselection.util.CommanMethod;

import java.util.List;


/**
 Created by Anil Ugale on 01-07-2015.
 */


public class ChapterBookmarkAdapter extends RecyclerView.Adapter<ChapterBookmarkAdapter.ViewHolder> {

    Activity context;
    public  List<ChapterBookmark> mLst;

    private int font;
    private char lang;
    private int color;


    public ChapterBookmarkAdapter(Activity context, List<ChapterBookmark> par, char lang, int color) {
        this.context = context;
        this.mLst = par;
        this.lang=lang;
        this.color=color;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bookmark, parent, false);
        return  new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.gdName.setCustomSelectionActionModeCallback(null);

        holder.gdName.setText(mLst.get(position).getBookName()+" > Chapter" + mLst.get(position).getChapter_id());
        holder.gdName.setTextColor(color);


        holder.verses_no.setText(String.valueOf(position + 1));
        holder.verses_no.setTextColor(color);
        switch (font)
        {
            case 1:
                holder.gdName.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                break;
            case 2:
                holder.gdName.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
                break;
            case 3:
                holder.gdName.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                break;
        }

        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db=new DatabaseHelper(context);
                db.deleteCbook(mLst.get(position).getId());
                db.close();
                mLst.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommanMethod.bookmarkCahpter=mLst.get(position);
                CommanMethod.isVerses_BookMark=false;
                CommanMethod.ischapter_book=true;
                context.startActivity(new Intent(context, BookActivity.class).putExtra(BookActivity.lang, lang)
                        .putExtra(MainActivity.COLOR, color));
                context.finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mLst.size();
    }

    public void setFont(int font) {
        this.font = font;
    }

    static class ViewHolder extends  RecyclerView.ViewHolder
    {

        protected TextView gdPoint;
        protected TextView gdName;
        protected TextView verses_no;
        protected ImageView menu;
        protected CardView relativeLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            relativeLayout=(CardView) itemView.findViewById(R.id.goodies_list_item);
            gdName = (TextView) itemView.findViewById(R.id.gdName);
            gdPoint = (TextView) itemView.findViewById(R.id.gdPoint);
            verses_no = (TextView) itemView.findViewById(R.id.verses_no);
            menu = (ImageView) itemView.findViewById(R.id.menu);

        }
    }



}
