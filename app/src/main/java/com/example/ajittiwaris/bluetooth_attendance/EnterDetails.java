package com.example.ajittiwaris.bluetooth_attendance;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EnterDetails extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener {
    Toolbar toolbar;
    Typeface typeface;
    Button BtSave,BtView,BtScan;
    EditText txtName,txtRoll,txtMacAdrs;
    int SelectedItem;
    ListView BtItemList;
    ArrayAdapter<String> adapter;
    BluetoothAdapter bluetoothAdapter;
    ArrayList<String> arrayList=new ArrayList<String>();
    ArrayList<String> MacList=new ArrayList<String>();
    DataBaseOperations dataBaseOperations;
    MyFunctions m;
    ProgressBar prg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_details);
        m=new MyFunctions(EnterDetails.this);
        typeface= Typeface.createFromAsset(getAssets(),"sangli.otf");
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView textView= (TextView)toolbar.getChildAt(0);
        textView.setTypeface(typeface);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prg=(ProgressBar)findViewById(R.id.prg);
        BtSave=(Button)findViewById(R.id.btsave);
        BtView=(Button)findViewById(R.id.btview);
        BtScan=(Button)findViewById(R.id.btScan);
        BtSave.setTypeface(typeface);
        BtView.setTypeface(typeface);
        BtScan.setTypeface(typeface);
        BtSave.setOnClickListener(this);
        BtView.setOnClickListener(this);

        txtName=(EditText)findViewById(R.id.txtstdname);
        txtRoll=(EditText)findViewById(R.id.txtrlno);
        txtMacAdrs=(EditText)findViewById(R.id.txtmac);
        BtItemList = (ListView) findViewById(R.id.btitemlist);

        dataBaseOperations=new DataBaseOperations(this);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        adapter=new ArrayAdapter<String>(EnterDetails.this,android.R.layout.simple_list_item_1);
        BtItemList.setAdapter(adapter);
        CheckBlueToothState();
        BtItemList.setOnItemClickListener(this);
        BtScan.setOnClickListener(ScanDevice);
        registerReceiver(breceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));


        //Cursor cursor=dataBaseOperations.getAllMac();
       //Log.e("Cursor Size",String.valueOf(cursor.getCount()));
    }


    private Button.OnClickListener ScanDevice=new Button.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.btScan:
                    adapter.clear();
                    MacList.clear();
                    arrayList.clear();
                    txtMacAdrs.setText("");
                    bluetoothAdapter.startDiscovery();
                    prg.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };


    private void CheckBlueToothState(){
        if (bluetoothAdapter == null)
        {
            Toast.makeText(EnterDetails.this, "Your Device Not Support Bluetooth", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (bluetoothAdapter.isEnabled())
            {
                if(bluetoothAdapter.isDiscovering())
                {
                    //txtCount.setText("Device Discovery Process...");
                }
                else
                {
                    //stateBluetooth.setText("Bluetooth is Enabled.");
                    //btnScanDevice.setEnabled(true);
                }
            }
            else
            {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent,1);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode==1)
        {
            CheckBlueToothState();
        }
    }

    private final BroadcastReceiver breceiver=new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action=intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action))
            {
                BluetoothDevice device=intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                 int Response=dataBaseOperations.getAllMacByMac(device.getAddress());
                 if(Response==0)
                 {
                     adapter.add(device.getName() + "\n" + device.getAddress());
                     MacList.add(device.getAddress());
                     adapter.notifyDataSetChanged();
                 }
            }

            if(prg.isShown())
            {
                prg.setVisibility(View.GONE);
            }

        }
    };


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(breceiver);
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
            case R.id.btsave:
                if(txtName.getText().toString().trim().length()<=0 || txtName.getText().toString().trim().length()<=0 || txtRoll.getText().toString().trim().length()<=0 )
                {
                    m.myToastRed("Enter All Details Before Save");
                }
                else
                {
                    DataBaseOperations dataBaseOperations=new DataBaseOperations(this);
                    String Response=dataBaseOperations.getMacAdrs(txtMacAdrs.getText().toString().trim());
                    if(Response.equals("0"))
                    {
                        String Roll=txtRoll.getText().toString().trim();
                        String Name=txtName.getText().toString();
                        String MacAdrs=txtMacAdrs.getText().toString().trim();
                        Integer Atnds=0;
                        SQLiteDatabase db=dataBaseOperations.getWritableDatabase();
                        String Result= dataBaseOperations.SaveDetails(db, Roll, Name, MacAdrs, Atnds);
                        m.myToastGreen(Result);
                        txtMacAdrs.setEnabled(true);
                        txtName.setText("");
                        txtRoll.setText("");
                        txtMacAdrs.setText("");
                        try{
                            String Item=adapter.getItem(SelectedItem);
                            adapter.remove(Item);
                            MacList.remove(SelectedItem);
                            adapter.notifyDataSetChanged();
                        }catch (Exception ee){}

                    }
                    else
                    {
                        m.myToastRed("Duplicate Mac Address Entered");
                    }
                    //BackgroundTask backgroundTask=new BackgroundTask(this,this);
                    //backgroundTask.execute("SaveData",),, ,"0");
                }

                break;

            case R.id.btview:
                startActivity(new Intent(this,ViewStudentDetails.class));
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        for (int i = 0; i < BtItemList.getChildCount(); i++)
        {
            if (position == i)
            {
                //BtItemList.getChildAt(i).setBackgroundColor(Color.parseColor("#FFBEC6F4"));
                BtItemList.getChildAt(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.list_item_background));
            }
            else
            {
                BtItemList.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
            }
            SelectedItem=position;
            txtMacAdrs.setText(MacList.get(position).toUpperCase().toString());
            txtMacAdrs.setEnabled(false);
        }
    }


}
