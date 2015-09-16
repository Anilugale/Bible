package com.itstest.textselection.adapter;


import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itstest.textselection.R;
import com.itstest.textselection.VersesActivity;
import com.itstest.textselection.fragment.ShareDialog;
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
        holder.verses_no.setText(String.valueOf(position + 1));
        holder.menu.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               showDialog(mLst.get(position));
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

    void showDialog(Verse verse)
    {
        FragmentManager fm = context.getSupportFragmentManager();
        ShareDialog editNameDialog = new ShareDialog();
        editNameDialog.setData(verse);
        editNameDialog.show(fm, "fragment_dialog");
    }


}
