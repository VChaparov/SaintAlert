package com.example.zzz.saintalert;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Saints_Activity extends AppCompatActivity {
    ArrayList<Item> Items = new ArrayList<>();
    ListView MyList;
    ListAdapter MyAdapter;
    String q;
    TextView Error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saints_);

        Error = findViewById(R.id.Error);

        Integer ID;
        String Name;
        String Date;
        String Desc;

        //Open DB and Load ListView with its records
        try {
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(getFilesDir().getPath() + "/" + "SaintDB.db", null);
            q = "CREATE TABLE if not exists Saints(";
            q += "ID Integer PRIMARY KEY AUTOINCREMENT NOT NULL, ";
            q += "Name String not null, ";
            q += "Date Date not null, ";
            q += "Description String ); ";
            db.execSQL(q);


            q = "SELECT * FROM Saints";

            Cursor c = db.rawQuery(q, null);
            while (c.moveToNext()) {
                ID = c.getInt(c.getColumnIndex("ID"));
                Name = c.getString(c.getColumnIndex("Name"));
                Date = c.getString(c.getColumnIndex("Date"));
                Desc = c.getString(c.getColumnIndex("Description"));
                Items.add(new Item(ID, Name, Date, Desc));
            }
            db.close();

        } catch (SQLiteException e) {
            Error.setText("Грешка при работа с БД: " + e.getLocalizedMessage() + e.getStackTrace());

        }

        //Set Onclick to go to Insert_Activity
        Button Add = findViewById(R.id.AddSaintBtn);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent InsertSaint = new Intent(getApplicationContext(), Insert_Activity.class);
                startActivity(InsertSaint);
            }
        });
        Button Back = findViewById(R.id.Back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(MainActivity);
            }
        });
        //Configure custom Adapter for displaying ListView Items using the ListeAdapter Class
        MyAdapter = new ListAdapter(this, Items);
        MyList = findViewById(R.id.DayList);
        MyList.setAdapter(MyAdapter);

        //Onclick to show a detailed View on the clicked item
        MyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent ShowSaintDetail = new Intent(getApplicationContext(), SaintDetailActivity.class);
                ShowSaintDetail.putExtra("ClickedIndex", position);
                ShowSaintDetail.putExtra("ID", Items.get(position).ID);
                ShowSaintDetail.putExtra("Saint", Items.get(position).Saint);
                ShowSaintDetail.putExtra("Date", Items.get(position).Date.toString());
                ShowSaintDetail.putExtra("Description", Items.get(position).Description);
                startActivity(ShowSaintDetail);
            }


        });

        }
    }
