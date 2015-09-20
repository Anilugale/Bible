package com.itstest.textselection.adapter;


import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itstest.textselection.BookmarkActivity;
import com.itstest.textselection.R;
import com.itstest.textselection.VersesActivity;
import com.itstest.textselection.database.DatabaseHelper;
import com.itstest.textselection.fragment.ShareDialog;
import com.itstest.textselection.model.Verse;

import java.util.ArrayList;
import java.util.List;


/**
 Created by Anil Ugale on 01-07-2015.
 */


public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {

    BookmarkActivity context;
    public  List<Verse> mLst;

    private int font;
    private char lang;


    public BookmarkAdapter(BookmarkActivity context, List<Verse> par, char lang) {
        this.context = context;
        this.mLst = par;
        this.lang=lang;
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

               holder.gdName.setText(mLst.get(position).getName());
        holder.gdName.setSelected(true);
        holder.gdName.setFocusable(true);

        holder.verses_no.setText(String.valueOf(position + 1));
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
                db.updateBookmark(lang,mLst.get(position).getId(),0);
                db.close();
                mLst.remove(position);
                notifyDataSetChanged();
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
