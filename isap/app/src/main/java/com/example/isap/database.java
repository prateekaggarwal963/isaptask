package com.example.isap;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class database extends SQLiteOpenHelper {

    private static final String dbname="userinfo.db";

    public database(@Nullable Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String s = "create table users(id integer primary key autoincrement, name text, address text,mobile text)";
        sqLiteDatabase.execSQL(s);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        //if table already exist then it will not create new table
        sqLiteDatabase.execSQL("drop table if exists users");
        onCreate(sqLiteDatabase);
    }
    //insert data in user database
    public boolean insert_data(String name,String address, String mobile)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c=new ContentValues();
        c.put("name",name);
        c.put("address",address);
        c.put("mobile",mobile);
        long r=db.insert("users",null,c);
        if(r==-1) return false;
        else
            return true;
    }

    //Viewing data from database
    public Cursor getInfo()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from users",null);
        return cursor;
    }
}
//o+@FxHY4{|zOx}48
//password for php