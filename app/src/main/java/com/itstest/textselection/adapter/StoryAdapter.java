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
import com.itstest.textselection.model.Story;

import java.util.ArrayList;
import java.util.List;


/**
 Created by Anil Ugale on 01-07-2015.
 */
public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {

    ChapterActivity context;
    List<Story> mLst;
    List<Story> mLst_bk;


    public StoryAdapter(ChapterActivity context, List<Story> par) {

        this.context = context;
        this.mLst = par;
        this.mLst_bk=new ArrayList<>();
        this.mLst_bk.addAll(mLst);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_story, null);

        return  new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


       holder.gdName.setText(mLst.get(position).getTtl());
        holder.gdTxt.setText(mLst.get(position).getStry());
        holder.gdPoint.setText(mLst.get(position).getId()+"");


    ///    holder.gdImage.setTag(position);

     /*   Picasso.with(context).load(mLst.get(position).getGoodie_image())
                .transform(new CircleTransform())
                .error(R.mipmap.default_profile_pic)
                .into(holder.gdImage);*/


      holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.startActivity(new Intent(context, VersesActivity.class).putExtra("tittle",mLst.get(position).getTtl()));
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
        protected TextView gdTxt;
        protected ImageView gdImage;

        protected CardView relativeLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            relativeLayout=(CardView) itemView.findViewById(R.id.goodies_list_item);
            gdName = (TextView) itemView.findViewById(R.id.gdName);
            gdPoint = (TextView) itemView.findViewById(R.id.gdPoint);

         //   gdTxt = (TextView) itemView.findViewById(R.id.gdMsg);
         //   gdImage = (ImageView) itemView.findViewById(R.id.gdImage);

        }
    }

 public  void filter(String data)
    {

        List<Story> goodies1=new ArrayList<>();

        if(data.equals(""))
        {
            mLst.addAll(mLst_bk);
            this.notifyDataSetChanged();
            return ;
        }

        if(data!=null) {
            mLst.clear();
            for (Story g : mLst_bk) {
                if (g.getTtl().toLowerCase().startsWith(data.toLowerCase()) || g.getTtl().toLowerCase().startsWith(data.toLowerCase()))
                    goodies1.add(g);

            }
        }
        mLst.clear();
        mLst.addAll(goodies1);
        this.notifyDataSetChanged();
        return ;


    }


}
