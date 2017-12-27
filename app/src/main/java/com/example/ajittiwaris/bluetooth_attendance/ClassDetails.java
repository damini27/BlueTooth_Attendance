package com.example.ajittiwaris.bluetooth_attendance;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ClassDetails extends AppCompatActivity {
    Button Submit,ViewData;
    EditText sem,course,class_id;
    MyFunctions m;
    ArrayList<String> AllData,Class_id,Course,Sem;
    ArrayAdapter<String> adapter;
    ListView listView;
    DataBaseOperations dataBaseOperations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        m=new MyFunctions(ClassDetails.this);
        setContentView(R.layout.activity_class_details);

        Submit = (Button)findViewById(R.id.button);
        ViewData = (Button)findViewById(R.id.button2);
        course = (EditText)findViewById(R.id.course);
        sem = (EditText)findViewById(R.id.sem);
        class_id = (EditText)findViewById(R.id.class_id);
        AllData = new ArrayList<String>();
        Class_id=new ArrayList<String>();
        Course=new ArrayList<String>();
        Sem=new ArrayList<String>();
        listView=(ListView)findViewById(R.id.listView);
        dataBaseOperations=new DataBaseOperations(ClassDetails.this);


        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sem.getText().toString().trim().length()<=0 || course.getText().toString().trim().length()<=0 ||class_id.getText().toString().trim().length()<=0)
                {
                    m.myToastRed("Enter All Details Before Save");
                }
                else
                {
                    //DataBaseOperations dataBaseOperations=new DataBaseOperations(ClassDetails.this);

                        String Course=course.getText().toString().trim();
                        String Sem=sem.getText().toString();
                        String CID=class_id.getText().toString();

                        SQLiteDatabase db=dataBaseOperations.getWritableDatabase();
                        String Result= dataBaseOperations.SaveClassDetails(db,CID,Course,Sem);
                        m.myToastGreen(Result);
                        sem.setText("");
                        course.setText("");


                    //BackgroundTask backgroundTask=new BackgroundTask(this,this);
                    //backgroundTask.execute("SaveData",),, ,"0");
                }

            }
        });
        ViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassDetails.this,ViewStudentDetails.class);
                startActivity(intent);

            }

        });
    }
//    public void getDetails(){



}
