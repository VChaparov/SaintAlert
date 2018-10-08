package com.example.zzz.saintalert;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Insert_Activity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    EditText editName;
    EditText editDesc;
    Button Month;
    Button Day;
    Button AddSaint;
    Button Back;
    TextView Error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        Month=findViewById(R.id.Month);
        Day=findViewById(R.id.Day);
        editName = findViewById(R.id.editName);
        editDesc = findViewById(R.id.editDesc);
        Error = findViewById(R.id.Error);
        AddSaint = findViewById(R.id.AddSaintBtn);
        Back=findViewById(R.id.Back);

        AddSaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            try {
                SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(getFilesDir().getPath() + "/" + "SaintDB.db", null);
                String SaintName = editName.getText().toString();
                Integer MonthNum=0;
                if(Month.getText().equals("January"))
                    MonthNum=0;
                else if(Month.getText().equals("February"))
                    MonthNum=1;
                else if(Month.getText().equals("March"))
                    MonthNum=2;
                else if(Month.getText().equals("April"))
                    MonthNum=3;
                else if(Month.getText().equals("May"))
                    MonthNum=4;
                else if(Month.getText().equals("June"))
                    MonthNum=5;
                else if(Month.getText().equals("July"))
                    MonthNum=6;
                else if(Month.getText().equals("August"))
                    MonthNum=7;
                else if(Month.getText().equals("September"))
                    MonthNum=8;
                else if(Month.getText().equals("October"))
                    MonthNum=9;
                else if(Month.getText().equals("November"))
                    MonthNum=10;
                else if(Month.getText().equals("December"))
                    MonthNum=11;
                Calendar Cal= Calendar.getInstance();
                Cal.set(Calendar.MONTH, MonthNum);
                Cal.set(Calendar.DAY_OF_MONTH,Integer.parseInt(Day.getText().toString()));
                SimpleDateFormat fmt = new SimpleDateFormat("dd.MMM");
                Date Date = Cal.getTime();
                String SaintDate = fmt.format(Date);
                String SaintDesc = editDesc.getText().toString();
                if (SaintName.equals("")) {
                    throw new Exception("Няма въведено Име");
                }
                String q = "INSERT INTO Saints(Name, Date, Description) VALUES('" + SaintName + "', '" + SaintDate + "', '" + SaintDesc + "');";
                db.execSQL(q);
                Error.setText("Uspeh");
                db.close();
                Intent MainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(MainActivity);
            } catch (SQLiteException e) {
                Error.setText("Грешка при работа с БД: " + e.getLocalizedMessage() + e.getStackTrace());
            } catch (Exception e) {
                Error.setText("Грешка: " + e.getLocalizedMessage());
            }
        }
    });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ViewSaint = new Intent(getApplicationContext(), Saints_Activity.class);
                startActivity(ViewSaint);
            }
        });
    }

    public void showMonthPopup(View view) {
        final PopupMenu MonthPopup = new PopupMenu(this,view);
        MonthPopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Month.setText(item.getTitle());
                return true;
            }
        });
        Day.setText("1");
        MonthPopup.inflate(R.menu.month_menu);
        MonthPopup.show();

    }

    public void showDayPopup(View view) {
        PopupMenu DayPopup = new PopupMenu(this, view);
        if (Month.getText().equals("January") || Month.getText().equals("March") || Month.getText().equals("May") || Month.getText().equals("July") || Month.getText().equals("August") ||
                Month.getText().equals("October") || Month.getText().equals("December")) {
            for (Integer i = 1; i <= 31; i++) {
                DayPopup.getMenu().add(Menu.NONE, i, Menu.NONE, i.toString());
            }
        } else if (Month.getText().equals("April") || Month.getText().equals("June") || Month.getText().equals("September") || Month.getText().equals("November")) {

            for (Integer i = 1; i <= 30; i++) {
                DayPopup.getMenu().add(Menu.NONE, i, Menu.NONE, i.toString());
            }

        } else {
            for (Integer i = 1; i <= 29; i++) {
                DayPopup.getMenu().add(Menu.NONE, i, Menu.NONE, i.toString());
            }
        }
        DayPopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Day.setText(item.getTitle());
                return true;
            }
        });
        DayPopup.show();
    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }



}
