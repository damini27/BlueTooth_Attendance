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

public class afterLogin extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    Typeface typeface;
    Button Class1,Class2,Class3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        typeface= Typeface.createFromAsset(getAssets(),"sangli.otf");
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView textView= (TextView)toolbar.getChildAt(0);
        textView.setTypeface(typeface);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Class1=(Button)findViewById(R.id.btclass1);
        Class2=(Button)findViewById(R.id.btclass1);
        Class3=(Button)findViewById(R.id.btclass1);

        Class1.setOnClickListener(this);
        Class2.setOnClickListener(this);
        Class3.setOnClickListener(this);
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
            case R.id.btclass1:
                startActivity(new Intent(this, Register.class));
                break;

            case R.id.btclass2:
                startActivity(new Intent(this, Register.class));
                break;

            case R.id.btclass3:
                startActivity(new Intent(this, Register.class));
                break;
        }
    }
}
