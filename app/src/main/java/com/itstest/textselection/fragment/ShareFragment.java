package com.itstest.textselection.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itstest.textselection.R;

/**
 Created by Anil Ugale 1989 on 9/15/2015.
 */
public class ShareFragment extends DialogFragment {

    ShareFragment shareFragment;

    public static ShareFragment newInstance()
    {
        ShareFragment f = new ShareFragment();

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.share_fragment, container,
                false);
        getDialog().setTitle("DialogFragment Tutorial");
        // Do something else
        return rootView;
    }
}