package com.example.assignmenttemplateproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.assignmenttemplateproject.database.DatabaseHelper;
import com.example.assignmenttemplateproject.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    public static final String SQL_USER = "CREATE TABLE USER(UserName TEXT PRIMARY KEY  NOT NULL, " +
            "Password TEXT              NOT NULL, " +
            "Phone    TEXT              NOT NULL, " +
            "FullName TEXT)";
    public static final String TABLE_NAME = "USER";
    private final String TAG = this.getClass().getSimpleName();

    public UserDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public boolean insertUser(User user) {
        ContentValues values = new ContentValues();
        values.put("UserName", user.getUserName());
        values.put("Password", user.getUserPass());
        values.put("Phone", user.getPhone());
        values.put("FullName", user.getFullName());
        try {
            if (db.insert(TABLE_NAME, null, values) == -1) {
                return false;
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        return true;
    }

    public boolean searchUser(String userName) {
        int count = 0;
        String sql = "SELECT * FROM USER WHERE userName = ?";
        String[] selectionArgs = {userName};
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            count++;
            cursor.moveToNext();
        }
        cursor.close();

        return count > 0;
    }

    public List<User> getAllUser() {
        List<User> users = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String userName = cursor.getString(0);
            String userPass = cursor.getString(1);
            String phone = cursor.getString(2);
            String fullName = cursor.getString(3);
            User user = new User(userName, userPass, phone, fullName);
            users.add(user);
            cursor.moveToNext();
        }

        cursor.close();

        return users;
    }

    public boolean updatePassword(User user, String newPassword) {
        ContentValues values = new ContentValues();
        values.put("userName", user.getUserName());
        values.put("userPass", newPassword);

        try {
            if (db.update(TABLE_NAME, values, "UserName=? AND Password=?", new String[]{user.getUserName(), user.getUserPass()}) <= 0)
                return false;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        return true;
    }

    public boolean updateUser(User user) {
        ContentValues values = new ContentValues();
        values.put("userName", user.getUserName());
        values.put("Password", user.getUserPass());
        values.put("phone", user.getPhone());
        values.put("fullName", user.getFullName());

        try {
            if (db.update(TABLE_NAME, values, "USERNAME=? AND Password=?", new String[]{user.getUserName(), user.getUserPass()}) <= 0)
                return false;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        return true;
    }

    public boolean deleteUser(User user) {
        return db.delete(TABLE_NAME, "USERNAME=?", new String[]{user.getUserName()}) > 0;
    }

    public boolean login(String userName, String password) {
        Cursor cursor = db.rawQuery("SELECT USERNAME, PASSWORD FROM " + TABLE_NAME + " WHERE USERNAME=? AND PASSWORD=?", new String[]{userName, password});
        cursor.moveToFirst();

        String checkUserName = null;
        String checkUserPass = null;
        while (!cursor.isAfterLast()) {
            checkUserName = cursor.getString(0);
            checkUserPass = cursor.getString(1);
            cursor.moveToNext();
        }

        cursor.close();

        return checkUserName != null && checkUserPass != null;
    }

    public void connectDatabase() {
        db = dbHelper.getWritableDatabase();
    }

    public void disconnectDatabase() {
        db.close();
    }
}
