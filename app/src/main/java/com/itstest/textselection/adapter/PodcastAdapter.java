package com.itstest.textselection.adapter;


import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.itstest.textselection.PodcastActivity;
import com.itstest.textselection.R;
import com.itstest.textselection.fragment.MusicDialog;
import com.itstest.textselection.model.Music;
import com.itstest.textselection.util.CommanMethod;
import com.itstest.textselection.util.JsonCallBack;
import com.itstest.textselection.util.NetworkRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 Created by Anil Ugale on 01-07-2015.
 */
public class PodcastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements JsonCallBack {

    PodcastActivity context;
    List<Music> mLst;
    List<Music> mLst_bk;

    private char lang;
    int index=1;
    int RequestCodePodcast=12121;

    public PodcastAdapter(PodcastActivity context, List<Music> par, char lang) {

        this.context = context;
        this.mLst = par;

        this.mLst_bk=new ArrayList<>();
        this.mLst_bk.addAll(mLst);
        this.lang=lang;
        mLst.add(mLst.size(),new Music());
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView;
        switch(viewType)
        {

            case VIEW_TYPES.Normal:
                itemLayoutView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_podcast, parent,false);
                return  new ViewHolder(itemLayoutView);

            case VIEW_TYPES.Footer:
                itemLayoutView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_footer, parent,false);
                return  new FooterViewHolder(itemLayoutView);
            default:
                itemLayoutView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_footer, parent,false);
                return  new ViewHolder(itemLayoutView);

        }

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

      if(holder instanceof PodcastAdapter.ViewHolder) {

          PodcastAdapter.ViewHolder  holder1=(PodcastAdapter.ViewHolder) holder;

          holder1.gdName.setText(mLst.get(position).getName());
          holder1.relativeLayout.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  showDialog(mLst.get(position));
              }
          });
      }else if(holder instanceof PodcastAdapter.FooterViewHolder)
      {
          PodcastAdapter.FooterViewHolder  holder1=(FooterViewHolder) holder;
          holder1.loadMore.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Toast.makeText(context, "loading", Toast.LENGTH_SHORT).show();

                  NetworkRequest.SimpleJsonRequest(context, new JSONObject(), NetworkRequest.SongSrc + "?index=" + index + "&language=" + CommanMethod.languageCode(lang), PodcastAdapter.this, RequestCodePodcast, 1);
                //  index++;

              }
          });

      }




    }


    @Override
    public void success(JSONArray response, int responseCode) {

        if(RequestCodePodcast==responseCode)
        {
            System.out.println(response.toString());

            Type listType = new TypeToken<List<Music>>(){}.getType();
           List<Music> dataPodcast=NetworkRequest.gson.fromJson(response.toString(),listType);
            mLst_bk.addAll(dataPodcast);
            mLst.clear();
            mLst.addAll(mLst_bk);
            mLst.add(mLst_bk.size(),new Music());
            notifyDataSetChanged();
        }
    }
    @Override
    public void failer(VolleyError response, int responseCode) {

        Toast.makeText(context, "Problem in loading", Toast.LENGTH_SHORT).show();
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

    private class VIEW_TYPES {

        public static final int Normal = 1;
        public static final int Footer = 2;

    }

    @Override
    public int getItemViewType(int position) {


        if((position+1)==mLst.size())
            return VIEW_TYPES.Footer;
        else
            return VIEW_TYPES.Normal;

    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        protected Button loadMore;
        public FooterViewHolder(View itemView) {

            super(itemView);
            loadMore=(Button) itemView.findViewById(R.id.load_more);

        }
    }

     int back = 0;
    void showDialog(Music verse)
    {
        FragmentManager fm = context.getSupportFragmentManager();
        MusicDialog fragment = new MusicDialog();
        fragment.setCancelable(false);
        fragment.setData(verse, lang);
        fragment.show(fm, "fragment_dialog");


    }

}
