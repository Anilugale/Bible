package com.itstest.textselection.model;

/**
    Created by Anil Ugale on 11/09/2015.
 */
public class Verse {

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

    private int id;
    private String name;
    private int bookmar;

    public int getBookmar() {
        return bookmar;
    }

    public void setBookmar(int bookmar) {
        this.bookmar = bookmar;
    }
}
