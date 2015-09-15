package com.itstest.textselection.model;


/**
     Created by anil on 12/08/2015.
 */
public class Story implements Comparable {
    private int id;
    private String ttl;
    private String stry;
    public int getId ()
    {
        return id;
    }
    public void setId (int id)
    {
        this.id = id;
    }
    public String getTtl ()
    {
        return ttl;
    }
    public void setTtl (String ttl)
    {
        this.ttl = ttl;
    }
    public String getStry ()
    {
        return stry;
    }
    public void setStry (String stry)
    {
        this.stry = stry;
    }
    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", ttl = "+ttl+", stry = "+stry+"]";
    }


    @Override
    public int compareTo(Object another) {

        Story story=(Story) another;
        if(this.id>story.getId())
        return 0;
        else if(this.id==story.getId())
            return 1;
        else
            return -1;
    }
}