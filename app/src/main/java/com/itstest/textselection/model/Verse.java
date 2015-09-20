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

    public int getColor() {
        return color;
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
