package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "logbookDb";
    private static final String LOGBOOK_TABLE = "logbook";

    public static final String ID = "id";
    public static final String URL = "url";


    private SQLiteDatabase database;

    private static final String LOGBOOK_TABLE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "   %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "   %s TEXT)",
                    LOGBOOK_TABLE, ID, URL);

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LOGBOOK_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + LOGBOOK_TABLE_CREATE);

        Log.v(this.getClass().getName(), DATABASE_NAME + " database upgrade to version " +
                i1 + " - old data lost");
        onCreate(db);
    }

    public long addNewImage(String url) {
        ContentValues rowValues = new ContentValues();

        rowValues.put(URL, url);

        return database.insertOrThrow(LOGBOOK_TABLE, null, rowValues);
    }

    public ArrayList getImages() {
        Cursor results = database.query(LOGBOOK_TABLE, new String[]{ID, URL},
                null, null, null, null, "url");
        ArrayList<LogbookEntity> result =new ArrayList<LogbookEntity>();

        results.moveToFirst();
        while (!results.isAfterLast()) {
            int id = results.getInt(0);
            String url = results.getString(1);

            LogbookEntity trip = new LogbookEntity(id, url);
            result.add(trip);

            results.moveToNext();
        }
        return result;
    }

}
