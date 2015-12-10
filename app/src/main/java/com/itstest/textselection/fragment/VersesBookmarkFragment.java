package com.itstest.textselection.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itstest.textselection.BookActivity;
import com.itstest.textselection.MainActivity;
import com.itstest.textselection.R;
import com.itstest.textselection.adapter.BookmarkAdapter;
import com.itstest.textselection.database.DatabaseHelper;
import com.itstest.textselection.model.Verse;
import com.itstest.textselection.ui.EmptyRecyclerView;
import com.itstest.textselection.ui.RecyclerItemClickListener;
import com.itstest.textselection.util.CommanMethod;

import java.util.List;

/**
 Created by Anil on 11/22/2015.
 */
public class VersesBookmarkFragment extends Fragment {

    char lang;
    int color;
    private BookmarkAdapter adapter;
    EmptyRecyclerView recyclerView ;
    List<Verse> databookmark;
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
        recyclerView=(EmptyRecyclerView)view.findViewById(R.id.list_verses);
        recyclerView.setEmptyView(view.findViewById(R.id.no_data));
        linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);



        DatabaseHelper db=new DatabaseHelper(getActivity());
        try {
            databookmark= db.getBookMarkVerse(lang);
            adapter=new BookmarkAdapter(getActivity(),databookmark,lang,color);
            recyclerView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();

       /* recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        }));*/
    }
}
