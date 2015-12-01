package com.itstest.textselection.model;


/**
     Created by anil on 12/08/2015.
 */
public class Music
{
    private String singer_mobile_no;

    private String singer_details;

    private String name;

    private String singer_name;

    private String singer_email;

    private String url;

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    private String date_added;

    public String getSinger_mobile_no ()
    {
        return singer_mobile_no;
    }

    public void setSinger_mobile_no (String singer_mobile_no)
    {
        this.singer_mobile_no = singer_mobile_no;
    }

    public String getSinger_details ()
    {
        return singer_details;
    }

    public void setSinger_details (String singer_details)
    {
        this.singer_details = singer_details;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getSinger_name ()
    {
        return singer_name;
    }

    public void setSinger_name (String singer_name)
    {
        this.singer_name = singer_name;
    }

    public String getSinger_email ()
    {
        return singer_email;
    }

    public void setSinger_email (String singer_email)
    {
        this.singer_email = singer_email;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [singer_mobile_no = "+singer_mobile_no+", singer_details = "+singer_details+", name = "+name+", singer_name = "+singer_name+", singer_email = "+singer_email+", url = "+url+"]";
    }
}