package com.itstest.textselection.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itstest.textselection.R;
import com.itstest.textselection.adapter.BookmarkAdapter;
import com.itstest.textselection.database.DatabaseHelper;
import com.itstest.textselection.model.Verse;

import java.util.List;

/**
 Created by Anil on 11/22/2015.
 */
public class VersesBookmarkFragment extends Fragment {

    char lang;
    int color;
    private BookmarkAdapter adapter;
    RecyclerView recyclerView ;
    LinearLayoutManager linearLayoutManager;
    private static VersesBookmarkFragment instance;
    public static VersesBookmarkFragment newInstance(char lang,int color)
    {
        if(instance==null)
            instance=new VersesBookmarkFragment();
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
            List<Verse> databookmark= db.getBookMarkVerse(lang);
            adapter=new BookmarkAdapter(getActivity(),databookmark,lang,color);
            recyclerView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
    }
}
