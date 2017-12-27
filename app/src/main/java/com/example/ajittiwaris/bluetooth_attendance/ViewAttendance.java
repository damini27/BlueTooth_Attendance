package com.example.ajittiwaris.bluetooth_attendance;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewAttendance extends AppCompatActivity implements AdapterView.OnItemClickListener
{

    Toolbar toolbar;
    Typeface typeface;
    Button Regd,TakeAttnd,ViewAttnd;
    ArrayList<String> AllData,StuId,Name,Roll,MacAdrs,Atndns;
    ArrayAdapter<String> adapter;
    ListView listView;
    DataBaseOperations dataBaseOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);
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
        //listView.setOnItemClickListener(this);
        dataBaseOperations=new DataBaseOperations(this);
        AllData=new ArrayList<String>();
        StuId=new ArrayList<String>();
        Name=new ArrayList<String>();
        Roll=new ArrayList<String>();
        MacAdrs=new ArrayList<String>();
        listView.setOnItemClickListener(this);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
      /* mSearchAction.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {


           @Override
           public boolean onMenuItemClick(MenuItem item) {
               handleMenuSearch();
               return true;
           }
    });*/
        return true;
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
                AllData.add("# " + Slno  + "\nRoll: " + cursor.getString(1) + "\nName: " + cursor.getString(2) + "\nMac Address: " + cursor.getString(3) + "\nAttendance: " + cursor.getString(4));
            } while (cursor.moveToNext());
        }
        else
        {
           // Toast.makeText(ViewStudentDetails.this, "No Data Found", Toast.LENGTH_SHORT).show();
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
            case R.id.action_search:
                 return true;

        }
        return super.onOptionsItemSelected(item);
    }


    public void showUpdateDialog()
    {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.edittext_dialog, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        final EditText edt = (EditText) dialogView.findViewById(R.id.txtAttnds);

        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                Toast.makeText(ViewAttendance.this,edt.getText() , Toast.LENGTH_SHORT).show();
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                dialog.dismiss();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        //showUpdateDialog();
    }
}
