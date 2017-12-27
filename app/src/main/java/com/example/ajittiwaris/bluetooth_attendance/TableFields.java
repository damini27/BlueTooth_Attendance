package com.example.ajittiwaris.bluetooth_attendance;

/**
 * Created by Md Farhan Raja on 3/15/2016.
 */
public class TableFields
{
    public static abstract class TableColumns
    {
        public TableColumns()
        {

        }

        public static final String Roll = "roll";
        public static final String Name = "name";
        public static final String MacAdrs = "macadrs";
        public static final String Attendance = "atnds";
        public static final String TableName = "studtls";
    }
    public static abstract class TeacherColumns
    {
        public TeacherColumns(){

        }
        public static final String Tid = "Teacher_id";
        public static final String TName = "name";
        public static final String Password = "password";
        public static final String Cid = "Class_id";
        public static final String Subject = "subject";
        public static final String TableName = "teacher";
    }

    public static abstract class ClassColumns
    {
        public ClassColumns(){

        }
        public static final String Cid = "Class_id";
        public static final String Course = "course";
        public static final String Sem = "semester";
        public static final String TableName = "class";
    }
}
