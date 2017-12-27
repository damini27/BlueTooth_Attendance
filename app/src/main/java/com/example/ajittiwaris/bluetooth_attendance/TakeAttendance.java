package com.example.ajittiwaris.bluetooth_attendance;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TakeAttendance extends AppCompatActivity {
    Toolbar toolbar;
    Typeface typeface;
    Button BtSearchDevice, BtMarkAttnd;
    ListView BtItemList;
    ArrayAdapter<String> adapter;
    BluetoothAdapter bluetoothAdapter;
    ArrayList<Integer> RollList=new ArrayList<Integer>();
    BroadcastReceiver b;
    TextView txtCount;
    DataBaseOperations dataBaseOperations;
    ProgressBar prg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);
        typeface = Typeface.createFromAsset(getAssets(), "sangli.otf");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView textView = (TextView) toolbar.getChildAt(0);
        textView.setTypeface(typeface);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        prg=(ProgressBar)findViewById(R.id.prg);
        BtSearchDevice = (Button) findViewById(R.id.btsearch);
        BtMarkAttnd = (Button) findViewById(R.id.btmarkattn);
        BtItemList = (ListView) findViewById(R.id.btitemlist);
        txtCount=(TextView)findViewById(R.id.txtcount);
        txtCount.setTypeface(typeface);
        BtSearchDevice.setTypeface(typeface);
        BtMarkAttnd.setTypeface(typeface);
        BtMarkAttnd.setEnabled(false);
        dataBaseOperations=new DataBaseOperations(this);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        adapter=new ArrayAdapter<String>(TakeAttendance.this,android.R.layout.simple_list_item_1);
        BtItemList.setAdapter(adapter);
        CheckBlueToothState();

        BtSearchDevice.setOnClickListener(ScanDevice);
        BtMarkAttnd.setOnClickListener(ScanDevice);
        registerReceiver(breceiver,new IntentFilter(BluetoothDevice.ACTION_FOUND));

    }


    private void CheckBlueToothState(){
        if (bluetoothAdapter == null)
        {
            Toast.makeText(TakeAttendance.this, "Your Device Not Support Bluetooth", Toast.LENGTH_SHORT).show();
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


    private Button.OnClickListener ScanDevice=new Button.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.btsearch:
                    txtCount.setText("");
                    adapter.clear();
                    bluetoothAdapter.startDiscovery();
                    prg.setVisibility(View.VISIBLE);
                    break;

                case R.id.btmarkattn:
                    SQLiteDatabase db=dataBaseOperations.getWritableDatabase();
                    for(int i=0;i<RollList.size();i++)
                    {
                        dataBaseOperations.UpdateAttendance(db,RollList.get(i));
                    }
                    Toast.makeText(TakeAttendance.this, "Attendance Update Successfully", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };


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
//                int Response=dataBaseOperations.getAllMacByMac(device.getAddress());
//                if(Response==1)
//                {
//                    adapter.add(device.getName() + "\n" + device.getAddress());
//                    //MacList.add(device.getAddress());
//                    adapter.notifyDataSetChanged();
//                }

                Cursor cursor=dataBaseOperations.getAllMac();
                if (cursor.moveToFirst())
                {
                    do
                    {
                      if(cursor.getString(2).equals(device.getAddress()))
                      {
                          RollList.add(Integer.parseInt(cursor.getString(3)));
                          adapter.add("Roll: " + cursor.getString(0) + "\nName: " + cursor.getString(1) + "\nMAC Address: " + device.getAddress());
                          adapter.notifyDataSetChanged();
                          BtMarkAttnd.setEnabled(true);
                      }
                    } while (cursor.moveToNext());
                }
                else
                {
                    Toast.makeText(TakeAttendance.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }

                String TotDevice="Total Device: " + BtItemList.getAdapter().getCount();
                txtCount.setText(TotDevice);
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



}