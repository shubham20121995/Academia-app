package com.example.shu.attendance1;

/**
 * Created by Shu on 26/09/2016.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Database";




    Context l;


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        l = context;
        //3rd argument to be passed is CursorFactory instance
    }


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ATTENDANCE_TABLE = "CREATE TABLE " + "attendance_table" + "( "
                + "course_code" + " TEXT," + "title" + " TEXT," + "category" + " TEXT," + "faculty" + " TEXT," + "slot" + " TEXT," + "room_no" + " TEXT," + "hours_conducted" + " TEXT," + "hours_absent" + " TEXT," + "attn" + " TEXT" + ")";

        String CREATE_LOGIN_TABLE = "CREATE TABLE " + "login_table" + "( "
                + "token" + " TEXT" + ")";

        String CREATE_INFO_TABLE = "CREATE TABLE " + "info_table" + "( "
                + "name" + " TEXT," + "regno" + " TEXT," + "prog" + " TEXT," + "dept" + " TEXT," + "sem" + " TEXT," + "batch" + " TEXT" + ")";

        String CREATE_MARKS_TABLE = "CREATE TABLE " + "marks_table" + "( "
                + "coursecode" + " TEXT," + "subtype" + " TEXT," + "ct1" + " TEXT," + "ct2" + " TEXT" + ")";
        db.execSQL(CREATE_ATTENDANCE_TABLE);
        db.execSQL(CREATE_LOGIN_TABLE);
        db.execSQL(CREATE_INFO_TABLE);
        db.execSQL(CREATE_MARKS_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + "attendance_table");
        db.execSQL("DROP TABLE IF EXISTS " + "login_table");
        db.execSQL("DROP TABLE IF EXISTS " + "info_table");
        db.execSQL("DROP TABLE IF EXISTS " + "marks_table");
        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    long addInfo(String a, String b, String c, String d, String e, String f) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", a);
        values.put("regno", b);
        values.put("prog", c);
        values.put("dept", d);
        values.put("sem", e);
        values.put("batch", f);


        // Inserting Row
        long insert = db.insert("info_table", null, values);
        //2nd argument is String containing nullColumnHack

        return insert;
    }
    long addMarks(String a, String b, String c, String d) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("coursecode", a);
        values.put("subtype", b);
        values.put("ct1", c);
        values.put("ct2", d);



        // Inserting Row
        long insert = db.insert("marks_table", null, values);
        //2nd argument is String containing nullColumnHack

        return insert;
    }
    long addLogin(String a) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("token", a);

        // Inserting Row
        long insert = db.insert("login_table", null, values);
        //2nd argument is String containing nullColumnHack

        return insert;
    }

    long addAttendance(String a, String b, String c, String d, String e, String f, String g,String h,String i) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("course_code", a);
        values.put("title", b);
        values.put("category", c);
        values.put("faculty", d);
        values.put("slot", e);
        values.put("room_no", f);
        values.put("hours_conducted", g);
        values.put("hours_absent", h);
        values.put("attn", i);

        // Inserting Row
        long insert = db.insert("attendance_table", null, values);
        //2nd argument is String containing nullColumnHack

        return insert;
    }
    Cursor viewInfo() {
        SQLiteDatabase db = this.getReadableDatabase();
        String qwery = "SELECT * FROM info_table  ";
        Cursor cursor = db.rawQuery(qwery, null);
        return cursor;
    }
    Cursor viewMarks() {
        SQLiteDatabase db = this.getReadableDatabase();
        String qwery = "SELECT * FROM marks_table  ";
        Cursor cursor = db.rawQuery(qwery, null);
        return cursor;
    }
    void refreshMarks(){
        SQLiteDatabase db=this.getReadableDatabase();;
        db.execSQL("DROP TABLE IF EXISTS " + "marks_table");
        String CREATE_MARKS_TABLE = "CREATE TABLE " + "marks_table" + "( "
                + "coursecode" + " TEXT," + "subtype" + " TEXT," + "ct1" + " TEXT," + "ct2" + " TEXT" + ")";
        db.execSQL(CREATE_MARKS_TABLE);

    }
    void refreshInfo(){
        SQLiteDatabase db=this.getReadableDatabase();;
        db.execSQL("DROP TABLE IF EXISTS " + "info_table");
        String CREATE_INFO_TABLE = "CREATE TABLE " + "info_table" + "( "
                + "name" + " TEXT," + "regno" + " TEXT," + "prog" + " TEXT," + "dept" + " TEXT," + "sem" + " TEXT," + "batch" + " TEXT" + ")";

        db.execSQL(CREATE_INFO_TABLE);

    }

    Cursor viewLogin() {
        SQLiteDatabase db = this.getReadableDatabase();
        String qwery = "SELECT * FROM login_table  ";
        Cursor cursor = db.rawQuery(qwery, null);
        return cursor;
    }

    void deleteLogin(){
        SQLiteDatabase db=this.getReadableDatabase();;
        db.execSQL("DROP TABLE IF EXISTS " + "login_table");
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + "login_table" + "( "
                + "token" + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
    }
    Cursor viewAttendance() {
        SQLiteDatabase db = this.getReadableDatabase();
        String qwery = "SELECT * FROM attendance_table  ";
        Cursor cursor = db.rawQuery(qwery, null);
        return cursor;
    }
   void refreshAttendance(){
       SQLiteDatabase db=this.getReadableDatabase();;
       db.execSQL("DROP TABLE IF EXISTS " + "attendance_table");
       String CREATE_ATTENDANCE_TABLE = "CREATE TABLE " + "attendance_table" + "( "
               + "course_code" + " TEXT," + "title" + " TEXT," + "category" + " TEXT," + "faculty" + " TEXT," + "slot" + " TEXT," + "room_no" + " TEXT," + "hours_conducted" + " TEXT," + "hours_absent" + " TEXT," + "attn" + " TEXT" + ")";

       db.execSQL(CREATE_ATTENDANCE_TABLE);

}



}
