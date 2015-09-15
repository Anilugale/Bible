package com.itstest.textselection.adapter;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.itstest.textselection.PodcastActivity;
import com.itstest.textselection.R;
import com.itstest.textselection.model.Podcast;

import java.util.ArrayList;
import java.util.List;


/**
 Created by Anil Ugale on 01-07-2015.
 */
public class PodcastAdapter extends RecyclerView.Adapter<PodcastAdapter.ViewHolder> {

    PodcastActivity context;
    List<Podcast> mLst;
    List<Podcast> mLst_bk;


    public PodcastAdapter(PodcastActivity context, List<Podcast> par) {

        this.context = context;
        this.mLst = par;
        this.mLst_bk=new ArrayList<>();
        this.mLst_bk.addAll(mLst);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_podcast, parent,false);

        return  new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


       holder.gdName.setText(mLst.get(position).getTtl());





      holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // context.startActivity(new Intent(context, VersesActivity.class).putExtra("tittle",mLst.get(position).getTtl()));
                Toast.makeText(context,mLst.get(position).getUrl(),Toast.LENGTH_SHORT).show();
                context.openSearchPkg(mLst.get(position).getUrl());
            }
        });





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




}
