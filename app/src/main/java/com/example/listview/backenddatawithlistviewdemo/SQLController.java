package com.example.listview.backenddatawithlistviewdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SQLController {
    private DBHelper dbhelper;
    private Context ourcontext;
    private SQLiteDatabase database;

    public SQLController(Context c) {
        ourcontext = c;
    }

    public SQLController open() throws SQLException {
        dbhelper = new DBHelper(ourcontext);
        database = dbhelper.getWritableDatabase();
        return this;

    }

    public void close() {
        dbhelper.close();
    }

    //Inserting Data into table
    public void insertData(String latLngTime) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.KEY_LOCATION, latLngTime);
        database.insert(DBHelper.TABLE_NAME, null, cv);
    }

    //Getting Cursor to read data from table
    public Cursor readData() {
        String[] allColumns = new String[]{DBHelper.KEY_ID,
                DBHelper.KEY_LOCATION};
        Cursor c = database.query(DBHelper.TABLE_NAME, allColumns, null,
                null, null, null, DBHelper.KEY_ID + " DESC");
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    //Updating record data into table by id
    public int updateData(long memberID, String memberLatLngTime) {
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(DBHelper.KEY_LOCATION, memberLatLngTime);
        int i = database.update(DBHelper.TABLE_NAME, cvUpdate,
                DBHelper.KEY_ID + " = " + memberID, null);
        return i;
    }

    // Deleting record data from table by id
    public void deleteData(long memberID) {
        database.delete(DBHelper.TABLE_NAME, DBHelper.KEY_ID + "="
                + memberID, null);
    }

}
