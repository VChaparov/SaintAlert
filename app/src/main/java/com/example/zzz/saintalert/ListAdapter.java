package com.example.zzz.saintalert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class ListAdapter extends ArrayAdapter {

    ArrayList<Item> Items = new ArrayList<Item>();
    LayoutInflater ListInflater;
    Context con;

//

    public ListAdapter(Context context, ArrayList<Item> Items){
        super(context,0,Items);
        con=context;
        this.Items = Items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v  = LayoutInflater.from(con).inflate(R.layout._listview_layout,parent,false);
        TextView SaintName = v.findViewById(R.id.SaintNameTxt);
        TextView SaintDate = v.findViewById(R.id.SaintDateTxt);
        TextView SaintDesc = v.findViewById(R.id.SaintDescriptionTxt);

        String Saint = Items.get(position).Saint;
        String Date = Items.get(position).Date;
        String Description = Items.get(position).Description;

        SaintName.setText(Saint);
        SaintDate.setText(Date);
        SaintDesc.setText(Description);

        return v;
    }
}
