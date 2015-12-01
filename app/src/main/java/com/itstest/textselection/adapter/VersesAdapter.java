package com.itstest.textselection.adapter;


import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.itstest.textselection.R;
import com.itstest.textselection.VersesActivity;
import com.itstest.textselection.fragment.ShareDialog;
import com.itstest.textselection.model.Story;
import com.itstest.textselection.model.Verse;

import java.util.ArrayList;
import java.util.List;


/**
 Created by Anil Ugale on 01-07-2015.
 */


public class VersesAdapter extends RecyclerView.Adapter<VersesAdapter.ViewHolder> {

    VersesActivity context;
    public  List<Verse> mLst;
    List<Verse> mLst_bk;
    private int font;
    private char lang;
    private int color;
    private int lastPosition = -1;

    public VersesAdapter(VersesActivity context, List<Verse> par,char lang,int color) {
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
                .inflate(R.layout.item_verses, parent,false);
        return  new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.gdName.setCustomSelectionActionModeCallback(null);
    //    holder.gdName.setTextColor(color);
        holder.gdName.setText(mLst.get(position).getName());
        if(lang=='M') {
            if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                Typeface type = Typeface.createFromAsset(context.getAssets(), "m.ttf");
                holder.gdName.setTypeface(type);
            }
        }
        holder.verses_no.setText(String.valueOf(position + 1));
        holder.verses_no.setTextColor(color);
        switch (font) {
            case 1:
                holder.gdName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                break;
            case 2:
                holder.gdName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                break;
            case 3:
                holder.gdName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                break;
        }

        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(mLst.get(position),position);
            }
        });
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(mLst.get(position),position);
            }
        });


        SpannableStringBuilder ssb = new SpannableStringBuilder(mLst.get(position).getName());
        holder.gdName.setText(ssb);
        Log.e("color",mLst.get(position).getColor()+"");
        if(   mLst.get(position).getColor()==1) {
            ssb.setSpan(new BackgroundColorSpan(ContextCompat.getColor(context, R.color.holo_orange_light)),
                    mLst.get(position).getStart(),
                    mLst.get(position).getEnd(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else
        if(mLst.get(position).getColor()==2) {
            ssb.setSpan(new BackgroundColorSpan(ContextCompat.getColor(context, R.color.fifth)),
                    mLst.get(position).getStart(),
                    mLst.get(position).getEnd(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }else
      {
            ssb.setSpan(new BackgroundColorSpan(ContextCompat.getColor(context, R.color.six)),
                    mLst.get(position).getStart(),
                    mLst.get(position).getEnd(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        holder.gdName.setText(ssb);

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



    void showDialog(Verse verse,int i)
    {
        FragmentManager fm = context.getSupportFragmentManager();
        ShareDialog editNameDialog = new ShareDialog();
        editNameDialog.setData(verse,lang,i);
        editNameDialog.show(fm, "fragment_dialog");
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
                if (g.getName().toLowerCase().contains(data.toLowerCase()) || g.getName().toLowerCase().startsWith(data.toLowerCase()))
                    goodies1.add(g);

            }
        }
        mLst.clear();
        mLst.addAll(goodies1);
        this.notifyDataSetChanged();
        return ;


    }
}
