package com.example.ajittiwaris.bluetooth_attendance;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewClassDetails extends Activity {
    ListView listView;
    DataBaseOperations dataBaseOperations;
    ArrayList<String> AllData,Class_id,Course,Sem;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = (ListView)findViewById(R.id.listView);
        dataBaseOperations= new DataBaseOperations(this);
        setContentView(R.layout.activity_view_class_details);

        Cursor cursor=dataBaseOperations.getClassData();
        if (cursor.moveToFirst())
        {

            do
            {
                Class_id.add(cursor.getString(0));
                Course.add(cursor.getString(1));
                Sem.add(cursor.getString(2));
                AllData.add("\nClass Id: " + cursor.getString(0) + "\nCourse: " + cursor.getString(1) + "\nSemester: " + cursor.getString(2));
            } while (cursor.moveToNext());
        }
        else
        {
            Toast.makeText(ViewClassDetails.this, "No Data Found", Toast.LENGTH_SHORT).show();
        }
        adapter=new ArrayAdapter<String>(ViewClassDetails.this,android.R.layout.simple_list_item_1,AllData);
        listView.setAdapter(adapter);

    }

}
