package com.application.adaptersdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Олег on 04.08.2016.
 */
public class DB {

    private static final String DB_NAME = "mydb";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "mytab";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_NAME = "name";

    private static final String CREATE_DB = "create table " + DB_TABLE + "(" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_EMAIL + " integer, " +
            COLUMN_NAME + " text" +
            ");";

    private Context mCtx;

    private DBHelper myDBHelper;
    private SQLiteDatabase myDB;


    public DB(Context ctx) {
        mCtx = ctx;
    }

    public void open(){
        myDBHelper = new DBHelper(mCtx);
        myDB =  myDBHelper.getWritableDatabase();
    }

    public void close() {
        if (myDB != null)
            myDBHelper.close();
    }

    public Cursor getAllData (){
        return myDB.query(DB_TABLE, null, null, null, null, null, null);
    }

    public void addRec (String name, String email) {                 // Переписывается под свои нужды и данные
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_EMAIL, email);
        myDB.insert(DB_TABLE, null, cv);
    }

    public void delRec(long id){
        myDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
    }

    public void delAllRec(){
        myDB.delete(DB_TABLE, null, null);
    }

    public class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {    // Переписывается под свои нужды и данные
            sqLiteDatabase.execSQL(CREATE_DB);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            sqLiteDatabase.execSQL("drop table if exists " + DB_TABLE);
            onCreate(sqLiteDatabase);
        }
    }




}
