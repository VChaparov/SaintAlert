package com.example.zzz.saintalert;

import java.util.Calendar;

public class Item {
    Integer ID;
    String Saint;
    String Date;
    String Description;

    public Item(Integer ID,String Name, String Date, String Desc)
    {   this.ID= ID;
        this.Saint = Name;
        this.Date = Date;
        this.Description = Desc;
    }

    public Item(){
        this(0,"","","");
    }
    public Item(Integer ID){this(ID,"","","");}

    public Item(Integer ID,String Name){
        this(ID,Name,"","");
    }

    public Item(Integer ID,String Name, String Date){
        this(ID,Name,Date,"");

    }


}
