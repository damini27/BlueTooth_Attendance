package com.example.ajittiwaris.bluetooth_attendance;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 *
 */
public class BackgroundTask extends AsyncTask<String,String,String>
{
    Context context;
    Activity activity;
    MyFunctions m;
    public BackgroundTask(Context ctx,Activity act)
    {
        this.context=ctx;
        this.activity=act;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        m=new MyFunctions(activity);
    }

    @Override
    protected String doInBackground(String... params)
    {
        int state=0;
        String Method=params[0];
        DataBaseOperations dataBaseOperations=new DataBaseOperations(context);
        if(Method.equals("SaveData"))
        {
            String Response=dataBaseOperations.getMacAdrs(params[3].toString());
            if(Response.equals("0"))
            {
                String Roll=params[1];
                String Name=params[2];
                String MacAdrs=params[3];
                Integer Atnds=Integer.parseInt(params[4]);
                SQLiteDatabase db=dataBaseOperations.getWritableDatabase();
                String Result= dataBaseOperations.SaveDetails(db, Roll, Name, MacAdrs, Atnds);
                return Result;
            }
            else
            {
                return "0";
            }

        }
       else if(Method.equals("UpdateData"))
         {
             Integer Id=Integer.parseInt(params[1]);
        String Response=dataBaseOperations.getMacAdrsForUpdate(Id,params[4].toString());
        if(Response.equals("0"))
        {
            String Roll=params[2];
            String Name=params[3];
            String MacAdrs=params[4];
            SQLiteDatabase db=dataBaseOperations.getWritableDatabase();
            String Result= dataBaseOperations.UpdateDetails(db, Id, Roll, Name, MacAdrs);
            return Result;
        }
        else
        {
            return "0";
        }

    }
        return null;
    }

    @Override
    protected void onPostExecute(String msg)
    {
        if(msg.equals("0"))
        {
            m.myToastRed("Duplicate Mac Address Entered");
        }
        else
        {
            m.myToastGreen(msg);
        }
    }
}
