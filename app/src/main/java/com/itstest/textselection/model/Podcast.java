package com.itstest.textselection.model;


/**
     Created by anil on 12/08/2015.
 */
public class Podcast implements Comparable {

    private int id;
    private String ttl;
    private String url;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }




    public String toString()
    {
        return "ClassPojo [id = "+id+", ttl = "+ttl+", stry = ";
    }


    @Override
    public int compareTo(Object another) {

        Podcast story=(Podcast) another;
        if(this.id>story.getId())
        return 0;
        else if(this.id==story.getId())
            return 1;
        else
            return -1;
    }
}