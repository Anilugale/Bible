package com.itstest.textselection.model;

/**
 Created by Anil on 11/21/2015.
 */
public class Priest
{
    private String pr_title;

    private String pr_id;

    private String pr_name;

    public String getPr_title ()
    {
        return pr_title;
    }

    public void setPr_title (String pr_title)
    {
        this.pr_title = pr_title;
    }

    public String getPr_id ()
    {
        return pr_id;
    }

    public void setPr_id (String pr_id)
    {
        this.pr_id = pr_id;
    }

    public String getPr_name ()
    {
        return pr_name;
    }

    public void setPr_name (String pr_name)
    {
        this.pr_name = pr_name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [pr_title = "+pr_title+", pr_id = "+pr_id+", pr_name = "+pr_name+"]";
    }
}
