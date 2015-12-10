package  com.itstest.textselection.util;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;


import com.itstest.textselection.model.ChapterBookmark;
import com.itstest.textselection.model.Verse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
   Created by Anil on 13/08/2015.
 */
public  class CommanMethod {


    public static ChapterBookmark bookmarkCahpter;
    public static Verse versesBookmark;
    public static boolean  ischapter_book,isVerses_BookMark;
    public static void share(Context context,String shareSting)
 {
     Intent shareIntent = new Intent();
     shareIntent.setAction(Intent.ACTION_SEND);
     shareIntent.putExtra(Intent.EXTRA_TEXT, shareSting);
     shareIntent.setType("text/plain");
     context.startActivity(shareIntent);
 }



  public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


    public  static  String changeDateFormate(String dateString) throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); // 2015-09-14 01:48:32
        Date date = fmt.parse(dateString);

        SimpleDateFormat fmtOut = new SimpleDateFormat("dd MMM yyyy");
        return fmtOut.format(date);
    }

    public static int
    languageCode(char lang)
    {

         switch (lang)
         {
             case 'E':
                 return 1;
             case 'T':
                 return 4;

             case 'H':
                 return 3;
             case 't':
                return 2;

             case 'K':
                 return 5;

             case 'M':
                 return 6;

              case 'm':
                 return 7;

             default:
                 return 1;




         }

    }


}
