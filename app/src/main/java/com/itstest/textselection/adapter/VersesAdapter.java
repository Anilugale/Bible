package com.itstest.textselection.adapter;


import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itstest.textselection.ChapterActivity;
import com.itstest.textselection.R;
import com.itstest.textselection.VersesActivity;
import com.itstest.textselection.model.Chapter;
import com.itstest.textselection.model.Verse;

import java.util.ArrayList;
import java.util.List;


/**
 Created by Anil Ugale on 01-07-2015.
 */
public class VersesAdapter extends RecyclerView.Adapter<VersesAdapter.ViewHolder> {

    VersesActivity context;
    List<Verse> mLst;
    List<Verse> mLst_bk;


    public VersesAdapter(VersesActivity context, List<Verse> par) {

        this.context = context;
        this.mLst = par;
        this.mLst_bk=new ArrayList<>();
        this.mLst_bk.addAll(mLst);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_verses, parent,false);

        return  new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        holder.gdName.setText(mLst.get(position).getName());
        context.registerForContextMenu(holder.gdName);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });





    }

    @Override
    public int getItemCount() {
        return mLst.size();
    }

    static class ViewHolder extends  RecyclerView.ViewHolder
    {

        protected TextView gdPoint;
        protected TextView gdName;

        protected CardView relativeLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            relativeLayout=(CardView) itemView.findViewById(R.id.goodies_list_item);
            gdName = (TextView) itemView.findViewById(R.id.gdName);
            gdPoint = (TextView) itemView.findViewById(R.id.gdPoint);
        }
    }

    public  void filter(String data)
    {

        List<Verse> goodies1=new ArrayList<>();

        if(data.equals(""))
        {
            mLst.addAll(mLst_bk);
            this.notifyDataSetChanged();
            return ;
        }

        if(data!=null) {
            mLst.clear();
            for (Verse g : mLst_bk) {
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
