package com.itstest.textselection.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itstest.textselection.MainActivity;
import com.itstest.textselection.R;
import com.itstest.textselection.adapter.BookmarkAdapter;
import com.itstest.textselection.adapter.ChapterBookmarkAdapter;
import com.itstest.textselection.database.DatabaseHelper;
import com.itstest.textselection.model.Chapter;
import com.itstest.textselection.model.ChapterBookmark;
import com.itstest.textselection.model.Verse;

import java.util.List;

/**
 Created by Anil on 11/22/2015.
 */
public class ChapterBookmarkFragment extends Fragment {

    private ChapterBookmarkAdapter adapter;
    RecyclerView recyclerView ;
    LinearLayoutManager linearLayoutManager;
    private static ChapterBookmarkFragment instance;
    char lang;
    int color;
    public static ChapterBookmarkFragment newInstance(char lang,int color)
    {
        if(instance==null) {
            instance = new ChapterBookmarkFragment();
        }
        instance.lang=lang;
        instance.color=color;
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.chapper_bookmark,container,false);
        init(view);
        return view;
    }

    private void init(View view) {

        recyclerView=(RecyclerView)view.findViewById(R.id.list_verses);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);



        DatabaseHelper db=new DatabaseHelper(getActivity());
        try {
            List<ChapterBookmark> databookmark= db.getChapter(lang);
            adapter=new ChapterBookmarkAdapter(getActivity(),databookmark,lang,color);
            recyclerView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
    }
}