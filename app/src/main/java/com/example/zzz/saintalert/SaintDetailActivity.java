package com.example.zzz.saintalert;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SaintDetailActivity extends AppCompatActivity {



    int SaintID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saint_detail);

        Intent I = getIntent();
        final TextView Error = findViewById(R.id.Error);

        TextView SaintName = (TextView) findViewById(R.id.SaintNameTxt);
        TextView SaintDate = (TextView) findViewById(R.id.SaintDateTxt);
        TextView SaintDesc = (TextView) findViewById(R.id.SaintDescriptionTxt);

        SaintID = I.getIntExtra("ID",-1);
        final String Saint = I.getStringExtra("Saint");
        String Date = I.getStringExtra("Date");
        String Description = I.getStringExtra("Description");
        Button Delete = findViewById(R.id.DeleteBtn);

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(getFilesDir().getPath() + "/" + "SaintDB.db", null);
                    String q = "DELETE FROM Saints";
                    q += " WHERE ID ="+ SaintID +";";
                    db.execSQL(q);
                    db.close();
                    Intent MainActivity = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(MainActivity);


                }catch (SQLiteException e){
                    Error.setText("Грешка при работа с БД: "+e.getLocalizedMessage()+ e.getStackTrace());
                    }
            }
        });

        SaintName.setText(Saint);
        SaintDate.setText(Date);
        SaintDesc.setText(Description);
    }
}
