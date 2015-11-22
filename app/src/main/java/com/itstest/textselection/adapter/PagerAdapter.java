package com.itstest.textselection.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.itstest.textselection.fragment.ChapterBookmarkFragment;
import com.itstest.textselection.fragment.VersesBookmarkFragment;

/**
 Created by Anil on 11/22/2015.
 */
public class PagerAdapter extends FragmentPagerAdapter {


    char lang;
    int color;

    public PagerAdapter(FragmentManager fm,char lang,int color) {
        super(fm);
        this.lang=lang;
        this.color=color;
    }

    @Override
    public Fragment getItem(int position) {

        if(position==0)
            return ChapterBookmarkFragment.newInstance(lang,color);
        else
            return VersesBookmarkFragment.newInstance(lang,color);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
