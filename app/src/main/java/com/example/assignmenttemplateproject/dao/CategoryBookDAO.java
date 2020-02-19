package com.example.assignmenttemplateproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.assignmenttemplateproject.database.DatabaseHelper;
import com.example.assignmenttemplateproject.model.CategoryBook;

import java.util.ArrayList;
import java.util.List;

public class CategoryBookDAO {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public static final String SQL_CATEGORY = "CREATE TABLE CategoryBook(IdCategory NCHAR(5) PRIMARY KEY NOT NULL, " +
            "Name NVARCHAR(50) NOT NULL, " +
            "Describe NVARCHAR(255), " +
            "Location INTEGER)";
    public static final String TABLE_NAME = "CategoryBook";

    private final String TAG = this.getClass().getSimpleName();

    public CategoryBookDAO(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    public boolean insertCategory(CategoryBook category) {
        ContentValues values = new ContentValues();
        values.put("IdCategory", category.getId());
        values.put("Name", category.getName());
        values.put("Describe", category.getDescribe());
        values.put("Location", category.getLocation());
        try {
            if (db.insert(TABLE_NAME, null, values) <= 0) {
                return false;
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }

        return true;
    }

    public List<CategoryBook> getAllCategory() {
        List<CategoryBook> bookTypes = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String describe = cursor.getString(2);
            int location = cursor.getInt(3);

            CategoryBook category = new CategoryBook(id, name, describe, location);
            bookTypes.add(category);
            cursor.moveToNext();
        }

        cursor.close();

        return bookTypes;
    }

    public boolean updateCategoryBook(CategoryBook category) {
        ContentValues values = new ContentValues();
        values.put("idCategory", category.getId());
        values.put("name", category.getName());
        values.put("describe", category.getDescribe());
        values.put("location", category.getLocation());

        try {
            if (db.update(TABLE_NAME, values, "ID=?", new String[]{category.getId()}) <= 0)
                return false;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        return true;
    }

    public boolean deleteCategory(CategoryBook category) {
        return db.delete(TABLE_NAME, "IdCategory=?", new String[]{category.getId()}) > 0;
    }

    public void connectDatabase() {
        db = dbHelper.getWritableDatabase();
    }

    public void disconnectDatabase() {
        db.close();
    }
}
