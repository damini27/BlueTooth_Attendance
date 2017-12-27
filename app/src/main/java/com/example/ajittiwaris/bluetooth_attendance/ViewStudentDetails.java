package com.example.ajittiwaris.bluetooth_attendance;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewStudentDetails extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    Toolbar toolbar;
    Typeface typeface;
    Button Regd,TakeAttnd,ViewAttnd;
    ArrayList<String> AllData,StuId,Name,Roll,MacAdrs;
    ArrayAdapter<String> adapter;
    ListView listView;
    DataBaseOperations dataBaseOperations;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_details);
        Initilize();
        BindListView();
    }

    public void Initilize()
    {
        typeface= Typeface.createFromAsset(getAssets(),"sangli.otf");
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView textView= (TextView)toolbar.getChildAt(0);
        textView.setTypeface(typeface);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView=(ListView)findViewById(R.id.itemlist);
        listView.setOnItemClickListener(this);
        dataBaseOperations=new DataBaseOperations(this);
        AllData=new ArrayList<String>();
        StuId=new ArrayList<String>();
        Name=new ArrayList<String>();
        Roll=new ArrayList<String>();
        MacAdrs=new ArrayList<String>();

    }


    public void BindListView()
    {
        Cursor cursor=dataBaseOperations.getData();
        if (cursor.moveToFirst())
        {
            int Slno=0;
            do
            {
                Slno=Slno+1;
                StuId.add(cursor.getString(0));
                Roll.add(cursor.getString(1));
                Name.add(cursor.getString(2));
                MacAdrs.add(cursor.getString(3));
                AllData.add("# " + Slno  + "\nRoll: " + cursor.getString(1) + "\nName: " + cursor.getString(2) + "\nMac Address: " + cursor.getString(3));
            } while (cursor.moveToNext());
        }
        else
        {
            Toast.makeText(ViewStudentDetails.this, "No Data Found", Toast.LENGTH_SHORT).show();
        }
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,AllData);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
    {
        for (int i = 0; i < listView.getChildCount(); i++) {
            if (position == i) {
                listView.getChildAt(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.list_item_background));
            } else {
                listView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
            }
        }

        AlertDialog.Builder mDialog = new AlertDialog.Builder(this);
        mDialog.setTitle("Selection");
        mDialog.setMessage("Select Any Action");
        mDialog.setPositiveButton("Edit Details", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                Intent intent = new Intent(ViewStudentDetails.this, EditStuDetails.class);
                intent.putExtra("Id", StuId.get(position).toString());
                intent.putExtra("Roll", Roll.get(position).toString());
                intent.putExtra("Name", Name.get(position).toString());
                intent.putExtra("Mac", MacAdrs.get(position).toString());
                startActivity(intent);
                finish();
            }
        });

        mDialog.setNegativeButton("Delete Record", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                boolean status = dataBaseOperations.DeleteRecord(Integer.parseInt(StuId.get(position)));
                if (status)
                {
                    Toast.makeText(ViewStudentDetails.this, "Record Deleted", Toast.LENGTH_SHORT).show();
                    adapter.clear();
                    StuId.clear();
                    Roll.clear();
                    MacAdrs.clear();
                    Name.clear();
                    BindListView();
                } else
                {
                    Toast.makeText(ViewStudentDetails.this, "Unable To Delete Record", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog alert = mDialog.create();
        alert.getWindow().getAttributes().windowAnimations =R.style.Animations_SmileWindow;
        alert.show();
    }
}
