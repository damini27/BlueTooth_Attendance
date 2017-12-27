package com.example.ajittiwaris.bluetooth_attendance;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Md Farhan Raja on 2/14/2016.
 */
public class MyFunctions extends Activity

{

    public static Activity activity;
    Bitmap Bmp;

    public MyFunctions(Activity activity)
    {
        this.activity=activity;
    }


    public static  void myToastRed(String Msg)
    {
        Typeface t=Typeface.createFromAsset(activity.getAssets(),"sangli.otf");
        LayoutInflater layoutInflater=activity.getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.mytoast,(ViewGroup)activity.findViewById(R.id.tst));
        TextView text = (TextView) view.findViewById(R.id.textView);
        text.setText(Msg);
        text.setTypeface(t);
        Toast toast = new Toast(activity.getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

    public static  void myToastRedLong(String Msg)
    {
        Typeface t=Typeface.createFromAsset(activity.getAssets(),"sangli.otf");
        LayoutInflater layoutInflater=activity.getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.mytoast,(ViewGroup)activity.findViewById(R.id.tst));
        TextView text = (TextView) view.findViewById(R.id.textView);
        text.setText(Msg);
        text.setTypeface(t);
        Toast toast = new Toast(activity.getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }


    public static void myToastGreen(String Msg)
    {
        Typeface t=Typeface.createFromAsset(activity.getAssets(),"sangli.otf");
        LayoutInflater layoutInflater=activity.getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.mytoast2,(ViewGroup)activity.findViewById(R.id.tst));
        TextView text = (TextView) view.findViewById(R.id.textView);
        text.setText(Msg);
        text.setTypeface(t);
        Toast toast = new Toast(activity.getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }


    public static Typeface setFont(Context context)
    {
        Typeface t=Typeface.createFromAsset(context.getAssets(),"sangli.otf");
        return t;
    }

   public static boolean checkNetworkConnection()
    {
        ConnectivityManager connectivityManager=(ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}