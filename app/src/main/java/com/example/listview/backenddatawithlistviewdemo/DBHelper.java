package com.example.listview.backenddatawithlistviewdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    // TABLE INFORMATTION
    public static final String TABLE_NAME = "location";
    public static final String KEY_ID = "_id";
    public static final String KEY_LOCATION = "location";

    // DATABASE INFORMATION
    static final String DB_NAME = "location.db";
    static final int DB_VERSION = 1;

    // TABLE CREATION STATEMENT
    private static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME + " (" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_LOCATION + " TEXT NOT NULL);";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
