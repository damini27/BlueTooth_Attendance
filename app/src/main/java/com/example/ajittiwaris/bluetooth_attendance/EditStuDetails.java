package com.example.ajittiwaris.bluetooth_attendance;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditStuDetails extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    Typeface typeface;
    Button BtSave,BtView;
    EditText txtName,txtRoll,txtMacAdrs;
    int Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stu_details);

        typeface= Typeface.createFromAsset(getAssets(),"sangli.otf");
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView textView= (TextView)toolbar.getChildAt(0);
        textView.setTypeface(typeface);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BtSave=(Button)findViewById(R.id.btsave);
        BtView=(Button)findViewById(R.id.btview);
        BtSave.setTypeface(typeface);
        BtView.setTypeface(typeface);
        BtSave.setOnClickListener(this);
        BtView.setOnClickListener(this);

        txtName=(EditText)findViewById(R.id.txtstdname);
        txtRoll=(EditText)findViewById(R.id.txtrlno);
        txtMacAdrs=(EditText)findViewById(R.id.txtmac);

        Intent intent=getIntent();
        Id=Integer.parseInt(intent.getStringExtra("Id").toString());
        txtRoll.setText(intent.getStringExtra("Roll"));
        txtName.setText(intent.getStringExtra("Name"));
        txtMacAdrs.setText(intent.getStringExtra("Mac"));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                //onBackPressed();
                startActivity(new Intent(this,ViewStudentDetails.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btsave:
                BackgroundTask backgroundTask=new BackgroundTask(this,this);
                backgroundTask.execute("UpdateData",String.valueOf(Id).toString(),txtRoll.getText().toString(),txtName.getText().toString(), txtMacAdrs.getText().toString(),"0");
                break;

            case R.id.btview:
                startActivity(new Intent(this,ViewStudentDetails.class));
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,ViewStudentDetails.class));
        finish();
    }
}
