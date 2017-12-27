package com.example.ajittiwaris.bluetooth_attendance;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class frmLogin extends AppCompatActivity {
    Button b1;
    TextView t1,t2;
    Typeface typeface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_login);
        typeface= Typeface.createFromAsset(getAssets(),"sangli.otf");
        b1=(Button)findViewById(R.id.btlogin);
        b1.setTypeface(typeface);
        t1=(TextView)findViewById(R.id.txttitle);
        t1.setTypeface(typeface);
        t2=(TextView)findViewById(R.id.txtheader);
        t2.setTypeface(typeface);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(frmLogin.this, Teacher_account.class));
            }
        });
    }
}
