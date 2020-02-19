package com.example.assignmenttemplateproject.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.assignmenttemplateproject.database.DatabaseHelper;

public class GeneralQuery {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public GeneralQuery(Context context) {
        dbHelper = new DatabaseHelper(context);
    }



    public void connectDatabase() {
        db = dbHelper.getWritableDatabase();
    }

    public void disconnectDatabase() {
        db.close();
    }
}
