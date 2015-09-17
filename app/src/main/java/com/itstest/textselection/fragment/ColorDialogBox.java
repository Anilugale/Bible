package com.itstest.textselection.fragment;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.itstest.textselection.R;
import com.itstest.textselection.adapter.VersesAdapter;

/**
 Created by Anil Ugale on 16/09/2015.
 */
public class ColorDialogBox extends DialogFragment implements View.OnClickListener {

   private TextView testDemo;
   VersesAdapter adapter;
   int position;

   public static ColorDialogBox newInstance()
   {
      ColorDialogBox f = new ColorDialogBox();

      return f;
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.color_fragment, container,
              false);
      getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
      getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
      getDialog().setTitle("DialogFragment Tutorial");


      rootView.findViewById(R.id.red).setOnClickListener(this);
      rootView.findViewById(R.id.yello).setOnClickListener(this);
      rootView.findViewById(R.id.green).setOnClickListener(this);
      rootView.findViewById(R.id.blue).setOnClickListener(this);
      rootView.findViewById(R.id.purple).setOnClickListener(this);

      return rootView;
   }

   public void setData(TextView data,VersesAdapter adapter,int position) {
      this.testDemo = data;
      this.adapter=adapter;
      this.position=position;
   }


   public void textselected(int color) {
      int startIndex = testDemo.getSelectionStart();
      int endIndex = testDemo.getSelectionEnd();

      adapter.mLst.get(position).setBookmar(color);
      adapter.mLst.get(position).setStart(startIndex);
      adapter.mLst.get(position).setEnd(endIndex);
      adapter.notifyDataSetChanged();


   }

   @Override
   public void onClick(View v) {

      switch (v.getId())
      {
         case R.id.red:
            textselected(R.color.holo_red_light);
            break;
         case R.id.yello:
            textselected(R.color.holo_orange_light);
            break;
         case R.id.green:
            textselected(R.color.holo_green_light);
            break;
         case R.id.blue:
            textselected(R.color.holo_blue_dark);
            break;
         case R.id.purple:
            textselected(R.color.holo_purple);
            break;
    }

      this.dismiss();

   }
}
