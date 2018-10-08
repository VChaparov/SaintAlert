package com.example.zzz.saintalert;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.crypto.Mac;

public class MainActivity extends AppCompatActivity {
    ArrayList<Item> Items = new ArrayList<>();
    ListView MyList;
    ListAdapter MyAdapter;
    String q;
    TextView Error;
    String S ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();
        setContentView(R.layout.activity_main);

        Error = findViewById(R.id.Error);
        Integer ID;
        String Name;
        String Date;
        String Desc;

        //Open DB and Load Listview with its records
        try {
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(getFilesDir().getPath() + "/" + "SaintDB.db", null);
            q = "CREATE TABLE if not exists Saints(";
            q += "ID Integer PRIMARY KEY AUTOINCREMENT NOT NULL, ";
            q += "Name String not null, ";
            q += "Date Date not null, ";
            q += "Description String ); ";
            db.execSQL(q);
            q="SELECT * FROM Saints";
            Cursor c = db.rawQuery(q, null);
            while (c.moveToNext()){
                ID=c.getInt(c.getColumnIndex("ID"));
                Name=c.getString(c.getColumnIndex("Name"));
                Date=c.getString(c.getColumnIndex("Date"));
                Desc=c.getString(c.getColumnIndex("Description"));
                Date Today = Calendar.getInstance().getTime();
                SimpleDateFormat fmt = new SimpleDateFormat("dd.MMM");
                String Dat = fmt.format(Today);
                if(Date.equals(Dat)) {
                    Items.add(new Item(ID, Name, Date, Desc));
                    //String for the notification
                    S += Name + ", ";
                }
            }
            db.close();
        }catch (SQLiteException e){
            Error.setText("Грешка при работа с БД: "+e.getLocalizedMessage()+ e.getStackTrace());
        }
        //Configure custom Adapter for displaying ListView Items using the ListeAdapter Class
        MyAdapter = new ListAdapter(this,Items);
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
        //Onclick to load the Activity with whole Database entries
        Button View =findViewById(R.id.ViewSaintsBtn);
        View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent ViewSaints = new Intent(getApplicationContext(),Saints_Activity.class);
                    startActivity(ViewSaints);
            }
        });


        NotificationCompat.Builder Notification =  new NotificationCompat.Builder(this,"22")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Today's Saints")
                .setContentText(S)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true);

        Intent Mactivity = new Intent(this,MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(Mactivity);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.setContentIntent(resultPendingIntent);
        Notification.setContentIntent(resultPendingIntent);
        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(0,Notification.build());




    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.nChannel);
            String description = getString(R.string.dChannel);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("22", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);


        }
    }


}

