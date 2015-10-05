package com.itstest.textselection.model;

/**
    Created by Anil Ugale on 11/09/2015.
 */
public class Search {


   int verse_id,book_id,chapter_id;
    String verse_text, chapterName;

    public int getVerse_id() {
        return verse_id;
    }

    public void setVerse_id(int verse_id) {
        this.verse_id = verse_id;
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

    public String getVerse_text() {
        return verse_text;
    }

    public void setVerse_text(String verse_text) {
        this.verse_text = verse_text;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
}
