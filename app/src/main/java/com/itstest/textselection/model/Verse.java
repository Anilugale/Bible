package com.itstest.textselection.model;

/**
    Created by Anil Ugale on 11/09/2015.
 */
public class Verse {


    private int id;
    private String name;
    private int bookmar;
    private int start;
    private int end;
    private int book_id;
    private int chapter_id;
    private int verses_id;


    public int getColor() {
        return color;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(int chapter_id) {
        this.chapter_id = chapter_id;
    }

    public int getVerses_id() {
        return verses_id;
    }

    public void setVerses_id(int verses_id) {
        this.verses_id = verses_id;
    }

    public void setColor(int color) {
        this.color = color;
    }

    int color ;

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public int getBookmar() {
        return bookmar;
    }

    public void setBookmar(int bookmar) {
        this.bookmar = bookmar;
    }
}
