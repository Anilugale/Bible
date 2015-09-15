package com.itstest.textselection.model;

/**
     Created by Anil Ugale on 11/09/2015.
 */
public class Chapter {
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getChapter_num() {
        return chapter_num;
    }

    public void setChapter_num(int chapter_num) {
        this.chapter_num = chapter_num;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    private  int bookId;
    private int chapter_num;
    private String Name;

}
