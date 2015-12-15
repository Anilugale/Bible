package com.itstest.textselection.adapter;


import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
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
import com.itstest.textselection.VersesActivity;
import com.itstest.textselection.model.Chapter;

import java.util.ArrayList;
import java.util.List;


/**
 Created by Anil Ugale on 01-07-2015.
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    BookActivity context;
    List<Chapter> mLst;
    List<Chapter> mLst_bk;
    char lang;
    private int color;

    public BookAdapter(BookActivity context, List<Chapter> par, char lang,int color) {
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
                .inflate(R.layout.item_chapter,parent, false);

        return  new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


       holder.gdName.setText(mLst.get(position).getName().trim());
        holder.gdName.setTextColor(color);
       holder.gdPoint.setText(mLst.get(position).getChapter_num() + "");
       holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               context.startActivity(new Intent(context, ChapterActivity.class)
                       .putExtra(VersesActivity.BOOK_ID, mLst.get(position).getBookId()
                       ).putExtra(MainActivity.COLOR, color)
                       .putExtra(BookActivity.book_name,mLst.get(position).getName())
                       .putExtra(BookActivity.lang, lang));
               context.finish();

           }
       });


        GradientDrawable sd = (GradientDrawable) holder.leftArrow.getBackground().mutate();
        sd.setColor(color);
        sd.invalidateSelf();

   }

    @Override
    public int getItemCount() {
        return mLst.size();
    }

    static class ViewHolder extends  RecyclerView.ViewHolder
    {

        protected TextView gdPoint;
        protected TextView gdName;
        protected TextView gdTxt;
        protected ImageView leftArrow;

        protected CardView relativeLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            relativeLayout=(CardView) itemView.findViewById(R.id.goodies_list_item);
            gdName = (TextView) itemView.findViewById(R.id.gdName);
            gdPoint = (TextView) itemView.findViewById(R.id.gdPoint);

            leftArrow = (ImageView) itemView.findViewById(R.id.leftArrow);
          ///  gdImage = (ImageView) itemView.findViewById(R.id.gdImage);

        }
    }

 public  void filter(String data)
    {

        List<Chapter> goodies1=new ArrayList<>();

        if(data.equals(""))
        {
            mLst.addAll(mLst_bk);
            this.notifyDataSetChanged();
            return ;
        }

        if(data!=null) {
            mLst.clear();
            for (Chapter g : mLst_bk) {
                if (g.getName().toLowerCase().startsWith(data.toLowerCase()) )
                    goodies1.add(g);

            }
        }
        mLst.clear();
        mLst.addAll(goodies1);
        this.notifyDataSetChanged();
        return ;


    }



}
