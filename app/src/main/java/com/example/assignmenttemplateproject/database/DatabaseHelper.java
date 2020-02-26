package com.example.assignmenttemplateproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.assignmenttemplateproject.dao.BookDAO;
import com.example.assignmenttemplateproject.dao.CategoryBookDAO;
import com.example.assignmenttemplateproject.dao.InvoiceDAO;
import com.example.assignmenttemplateproject.dao.InvoiceDetailsDAO;
import com.example.assignmenttemplateproject.dao.UserDAO;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "assignment_template_project_final.db";
    private static final int VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserDAO.SQL_USER);
        db.execSQL(CategoryBookDAO.SQL_CATEGORY);
        db.execSQL(BookDAO.SQL_BOOK);
        db.execSQL(InvoiceDAO.SQL_INVOICE);
        db.execSQL(InvoiceDetailsDAO.SQL_INVOICE_DETAILS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
