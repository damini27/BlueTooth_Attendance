package com.example.ajittiwaris.bluetooth_attendance;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Register extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    Typeface typeface;
    Button Regd,TakeAttnd,ViewAttnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        typeface= Typeface.createFromAsset(getAssets(),"sangli.otf");
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView textView= (TextView)toolbar.getChildAt(0);
        textView.setTypeface(typeface);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Regd=(Button)findViewById(R.id.btregister);
        TakeAttnd=(Button)findViewById(R.id.bttakeattendance);
        ViewAttnd=(Button)findViewById(R.id.btviewattendance);

        Regd.setOnClickListener(this);
        TakeAttnd.setOnClickListener(this);
        ViewAttnd.setOnClickListener(this);

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
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btregister:
                startActivity(new Intent(this, EnterDetails.class));
                break;

            case R.id.bttakeattendance:
                startActivity(new Intent(this, TakeAttendance.class));
                break;

            case R.id.btviewattendance:
                startActivity(new Intent(this, ViewAttendance.class));
                break;
        }
    }
}
