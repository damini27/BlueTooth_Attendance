package com.example.ajittiwaris.bluetooth_attendance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.tech.NfcA;
import android.util.Log;

import java.sql.SQLException;
import java.sql.Struct;
import java.util.ArrayList;

/**
 * Created by Md Farhan Raja on 3/15/2016.
 */
public class DataBaseOperations extends SQLiteOpenHelper
{
    public static final int DbVersion=1;
    public static final String DbName = "studatabase.db";
    public static final String CreateTable = "create table " + TableFields.TableColumns.TableName +
            "(id INTEGER primary key autoincrement not null,"+ TableFields.TableColumns.Roll + " text," +
                 TableFields.TableColumns.Name + " text," +
                 TableFields.TableColumns.MacAdrs + " text," +
                 TableFields.TableColumns.Attendance + " INTEGER);";

    public static final String ClassTable = "create table " + TableFields.TableColumns.TableName +
            "("+TableFields.ClassColumns.Cid + "INTEGER primary key,"+
            TableFields.ClassColumns.Course + " text," +
            TableFields.ClassColumns.Sem + " text);";


    public static final String TeacherTable = "create table " + TableFields.TeacherColumns.TableName +
            "("+TableFields.TeacherColumns.Tid + " text primary key not null," +
            TableFields.TeacherColumns.TName + " text," +
            TableFields.TeacherColumns.Password + " text," +
            TableFields.TeacherColumns.Cid + " text,"+
            TableFields.TeacherColumns.Subject + " text,"+
            "FOREIGN KEY("+TableFields.TeacherColumns.Cid+") REFRENCES "+ClassTable+"("+TableFields.ClassColumns.Cid+"));";

    public DataBaseOperations(Context context)
    {
        super(context, DbName,null,DbVersion);
        //Log.e("Database "," Created Successfully...");
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CreateTable);
        //Log.e("Table ", CreateTable.toString() + "...Created Successfully...");
    }

    public String SaveTeacherDetails(SQLiteDatabase db,String Roll,String Name,String MacAdrs,Integer Attndnc)
    {
        try
        {
            ContentValues contentValues=new ContentValues();
            contentValues.put(TableFields.TableColumns.Roll,Roll);
            contentValues.put(TableFields.TableColumns.Name, Name);
            contentValues.put(TableFields.TableColumns.MacAdrs,MacAdrs);
            contentValues.put(TableFields.TableColumns.Attendance,Attndnc);
            db.insert(TableFields.TableColumns.TableName, null, contentValues);
            return "Data Saved Successfully";
        }
        catch(Exception e)
        {
            return e.getMessage().toString();
        }
    }
    public String SaveClassDetails(SQLiteDatabase db,String CID,String Course,String Sem)
    {
        try
        {
            ContentValues contentValues=new ContentValues();
            contentValues.put(TableFields.ClassColumns.Cid,CID);
            contentValues.put(TableFields.ClassColumns.Course,Course);
            contentValues.put(TableFields.ClassColumns.Sem, Sem);
            Log.i("CID",CID);
            Log.i("Course",Course);
            Log.i("sem",Sem);
            db.insert(TableFields.ClassColumns.TableName, null, contentValues);
            return "Data Saved Successfully";
        }
        catch(Exception e)
        {
            return e.getMessage().toString();
        }
    }
   public String SaveDetails(SQLiteDatabase db,String Roll,String Name,String MacAdrs,Integer Attndnc)
   {
       try
       {
           ContentValues contentValues=new ContentValues();
           contentValues.put(TableFields.TableColumns.Roll,Roll);
           contentValues.put(TableFields.TableColumns.Name, Name);
           contentValues.put(TableFields.TableColumns.MacAdrs,MacAdrs);
           contentValues.put(TableFields.TableColumns.Attendance,Attndnc);
           db.insert(TableFields.TableColumns.TableName, null, contentValues);
           return "Data Saved Successfully";
       }
       catch(Exception e)
       {
           return e.getMessage().toString();
       }
   }

    public String getMacAdrs(String Mac)
    {
            SQLiteDatabase db =this.getReadableDatabase();
            String Query="Select macadrs from studtls where macadrs=" +"'"+ Mac + "'"+";";
            Cursor c=db.rawQuery(Query,null);
            if(c.getCount()<=0)
            {
                c.close();
                return "0";
            }
            else
            {
                c.close();
                return "1";
            }
    }

    public String getMacAdrsForUpdate(Integer Id,String Mac)
    {
        SQLiteDatabase db =this.getReadableDatabase();
        String Query="Select macadrs from studtls where id not in(" + Id + ") and macadrs=" + "'" + Mac + "'" +";";
        Cursor c=db.rawQuery(Query,null);
        if(c.getCount()<=0)
        {
            c.close();
            return "0";
        }
        else
        {
            c.close();
            return "1";
        }
    }


    public int getAllMacByMac(String Mac)
    {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "select * from studtls where macadrs=" + "'" + Mac + "'" + ";";
            Cursor cursor = db.rawQuery(query, null);
            if(cursor.getCount()<=0)
            {
                return 0;
            }
        else
            {
                return 1;
            }
    }


    public Cursor getAllMac()
    {
        try
        {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "select roll,name,macadrs,id from studtls";
            Cursor cursor = db.rawQuery(query, null);
            return cursor;
        } catch (Exception e)
        {
            return null;
        }
    }

    public Cursor getData()
    {
        try
        {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "select * from studtls order by id desc";
            Cursor cursor = db.rawQuery(query, null);
            return cursor;
        } catch (Exception e)
        {
            return null;
        }
    }

    public Cursor getClassData()
    {
        try
        {
            SQLiteDatabase db = this.getReadableDatabase();
            Log.d("Before","before");
            String query = "select * from class";

            Cursor cursor = db.rawQuery(query, null);
            Log.d("IN GET", String.valueOf(cursor));
            return cursor;
        } catch (Exception e)
        {
            return null;
        }
    }

    public boolean UpdateDetails(Integer Id,String Roll,String Name,String Mac)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put(TableFields.TableColumns.Roll,Roll);
        contentValues.put(TableFields.TableColumns.Name,Name);
        contentValues.put(TableFields.TableColumns.MacAdrs,Mac);
        SQLiteDatabase db =this.getReadableDatabase();
        return db.update("studtls",contentValues,"id" + "=" +Id,null)>0;
    }


    public String UpdateDetails(SQLiteDatabase db,Integer ID,String Roll,String Name,String MacAdrs)
    {
        try
        {
            ContentValues contentValues=new ContentValues();
            contentValues.put(TableFields.TableColumns.Roll,Roll);
            contentValues.put(TableFields.TableColumns.Name, Name);
            contentValues.put(TableFields.TableColumns.MacAdrs,MacAdrs);
            db.update(TableFields.TableColumns.TableName, contentValues, "id" + "=" +ID,null);
            return "Data Updated Successfully";
        }
        catch(Exception e)
        {
            return e.getMessage().toString();
        }
    }

    public void UpdateAttendance(SQLiteDatabase db,int Id)
    {
       db.execSQL("UPDATE studtls SET atnds=atnds+1 where Id=" + Id +";");
    }




    public boolean DeleteRecord(Integer Id)
    {
        SQLiteDatabase db =this.getReadableDatabase();
        return db.delete("studtls","id" + "=" +Id,null)>0;
    }


    public ArrayList<String> getAllStudent()
    {
        SQLiteDatabase db =this.getReadableDatabase();
        String Query="Select * from studtls;";
        Cursor c=db.rawQuery(Query,null);
        ArrayList<String> Items=new ArrayList<String>();
        if(c.getCount()>0)
        {
            do
            {
                Items.add(c.getString(0)+c.getString(1)+c.getString(2));
            }while (c.moveToNext());
            c.close();
            return Items;
        }
        return null;
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
