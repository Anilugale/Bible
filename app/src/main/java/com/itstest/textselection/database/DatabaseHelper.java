package com.itstest.textselection.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.itstest.textselection.model.Chapter;
import com.itstest.textselection.model.ChapterBookmark;
import com.itstest.textselection.model.Search;
import com.itstest.textselection.model.Verse;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 Created by Anil Ugale on 10/09/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.itstest.textselection/databases/";

    private static String DB_NAME = "mydb.db";

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    public void createDataBase() throws IOException{
        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
        }else{

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

        }catch(SQLiteException e){

            //database does't exist yet.
            e.printStackTrace();
        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException {

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        if(myDataBase==null) {
            try {
                createDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

    }

    public List<Chapter> getDataBook(char langugae) throws IOException {
        openDataBase();

        String langCol=getColoumnBook(langugae);
        Cursor cursor = myDataBase.rawQuery("select book_id,"+langCol+",num_chptr from books", new String[]{});
        List<Chapter>  data=new ArrayList<>();

        while (cursor.moveToNext()) {
            Chapter chapter=new Chapter();
            chapter.setBookId(cursor.getInt(0));
            chapter.setName(cursor.getString(1));
            chapter.setChapter_num(cursor.getInt(2));
            data.add(chapter);
        }
        cursor.close();
        return data;
    }


    public List<Verse> getVerses(int book_id,int chapterId,char langugae)
    {
        String langCol=getColoumnName(langugae);

        openDataBase();
        Cursor cursor = myDataBase.rawQuery(" select id, verse_text , bookmark,start,end ,book_id ,chapter_id ,verse_id,color  from "+langCol+" where book_id =="+book_id+" and chapter_id=="+chapterId, new String[]{});
        List<Verse>  data=new ArrayList<>();

        while (cursor.moveToNext()) {
            Verse verse=new Verse();
            verse.setId(cursor.getInt(0));
            verse.setName(cursor.getString(1));
            verse.setBookmar(cursor.getInt(2));
            verse.setStart(cursor.getInt(3));
            verse.setEnd(cursor.getInt(4));
            verse.setBook_id(cursor.getInt(5));
            verse.setChapter_id(cursor.getInt(6));
            verse.setVerses_id(cursor.getInt(7));
            verse.setColor(cursor.getInt(8));
            data.add(verse);
        }
        cursor.close();
        return data;
    }



    public List<Search> getSearch(char langugae,String query)
    {
        System.out.println(langugae);
        String langCol=getColoumnName(langugae);
        String bookCol=getColoumnBook(langugae);

        openDataBase();

        Cursor cursor = myDataBase.rawQuery("select  "+langCol+".verse_id,"+langCol+".book_id,"+langCol+".chapter_id,books."+bookCol+", "+langCol+".verse_text from "+langCol+"  join books on  "+langCol+".verse_text like '%"+query+"%' and books.book_id=="+langCol+".book_id", new String[]{});
        List<Search>  data=new ArrayList<>();

        while (cursor.moveToNext()) {
            Search verse=new Search();
            verse.setVerse_id(cursor.getInt(0));
            verse.setBook_id(cursor.getInt(1));
            verse.setChapter_id(cursor.getInt(2));
            verse.setChapterName(cursor.getString(3));
            verse.setVerse_text(cursor.getString(4));

            data.add(verse);
        }
        cursor.close();
        return data;
    }



    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void updateBookmark(char langugae,int verseID,int update) {
        String langCol=getColoumnName(langugae);

        openDataBase();
        Log.e("q","update " + langCol + "  set bookmark=" + update + " where id=" + verseID);
        Cursor cursor=myDataBase.rawQuery(" update " + langCol + " set bookmark = ? where id = ? ",
                new String[]{String.valueOf(update)
                        ,
                        String.valueOf(verseID)
                });
        System.out.print("Count" + cursor.getCount());
        cursor.close();


    }

    public List<Verse> getBookMarkVerse(char langugae)
    {
        String langCol=getColoumnName(langugae);

        openDataBase();
        Cursor cursor = myDataBase.rawQuery("select  id,verse_text,bookmark,start,end ,book_id ,chapter_id ,verse_id from "+langCol+" where bookmark=1", new String[]{});
        List<Verse>  data=new ArrayList<>();

        while (cursor.moveToNext()) {
            Verse verse=new Verse();
            verse.setId(cursor.getInt(0));
            verse.setName(cursor.getString(1));
            verse.setBookmar(cursor.getInt(2));
            verse.setBook_id(cursor.getInt(5));
            verse.setChapter_id(cursor.getInt(6));
            verse.setVerses_id(cursor.getInt(7));
            data.add(verse);
        }
        cursor.close();
        return data;
    }

    public void updateHightLight(char lang,int id,int start,int end,int color) {

        String langCol=getColoumnName(lang);

        openDataBase();
        ContentValues cv = new ContentValues();
        cv.put("start", start);
        cv.put("end", end);
        cv.put("color", color);
        int i= myDataBase.update(langCol,cv,"id="+id,null);

        System.out.println("update b" + i);
    }

    public List<Verse> getChapter(int bookId, char langugae) {
        //
        String langCol="verses_asv";
        if(langugae=='M')
            langCol="verses";

        openDataBase();
        Cursor cursor = myDataBase.rawQuery("select DISTINCT chapter_id  from "+langCol+" where book_id="+bookId+"  order by id  ", new String[]{});
        List<Verse>  data=new ArrayList<>();

        while (cursor.moveToNext()) {
            Verse verse=new Verse();
            verse.setId(cursor.getInt(0));

            data.add(verse);
        }
        cursor.close();
        return data;

    }

    public List<ChapterBookmark> getChapter( char langugae) {


        openDataBase();
        Cursor cursor = myDataBase.rawQuery("select chapter_bookmark.id,chapter_bookmark.book_id,chapter_bookmark.chapter_id,books."+getColoumnBook(langugae)+"  chapter_bookmark from chapter_bookmark join books on lang = '"+langugae+"' and books.book_id=chapter_bookmark.book_id", new String[]{});
        List<ChapterBookmark>  data=new ArrayList<>();

        while (cursor.moveToNext()) {
            ChapterBookmark verse=new ChapterBookmark();
            verse.setId(cursor.getInt(0));
            verse.setBook_id(cursor.getInt(1));
            verse.setChapter_id(cursor.getInt(2));
            verse.setBookName(cursor.getString(3));


            data.add(verse);
        }
        cursor.close();
        return data;

    }


    String getColoumnName(char c)
    {
        String column;

        switch (c)
        {
            case 'M':
                column= "verses";
                break;

            case 'H':
                column= "verses_hindi";
                break;
            case 'm':
                column= "verses_marathi";
                break;
            case 'T':
                column= "verses_telugu";
                break;
            case 't':
                column= "verses_tamil";
                break;

            default:
                return "verses_asv";
        }
        return column;
    }

    String getColoumnBook(char c)
    {
        String column;

        switch (c)
        {
            case 'M':
                column= "MalayalamShortName";
                break;

            case 'H':
                column= "HindiShortName";
                break;
            case 'm':
                column= "MarathiShortName";
                break;
            case 'T':
                column= "TeluguShortName";
                break;
            case 't':
                column= "TamilShortName";
                break;

            default:
                return "EnglishShortName";
        }
        return column;
    }

    public void deleteCbook(int id) {

        String sql="delete from chapter_bookmark where id ="+id;
        System.out.println(sql);
        openDataBase();

        System.out.println(myDataBase.delete("chapter_bookmark", "id" + "=" + id, null));
    }

    public void makeChapterBookmark(int bookId,int chapterId,char lang) {


        String sql="insert into chapter_bookmark ('book_id','chapter_id','lang')" +
                "values("+bookId+","+chapterId+",'"+lang+"')";
        openDataBase();
        myDataBase.execSQL(sql,new String[]{});
    }
}
