package com.example.assignmenttemplateproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.assignmenttemplateproject.database.DatabaseHelper;
import com.example.assignmenttemplateproject.model.Book;
import com.example.assignmenttemplateproject.model.CategoryBook;

import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public static final String SQL_BOOK = "CREATE TABLE " + BookDAO.TABLE + " (" +
            "idBook NCHAR(50) PRIMARY KEY NOT NULL, " +
            "idCategory NCHAR(5) NOT NULL, " +
            "author NVARCHAR(50), " +
            "publisher NVARCHAR(50), " +
            "price FLOAT NOT NULL, " +
            "inStock INTEGER NOT NULL)";
    public static final String TABLE = "Book";

    private final String TAG = this.getClass().getSimpleName();

    public BookDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public boolean insertNewBook(Book book) {
        ContentValues values = new ContentValues();
        values.put("idBook", book.getIdBook());
        values.put("idCategory", book.getCategoryBook().getId());
        values.put("author", book.getAuthor());
        values.put("price", book.getPrice());
        values.put("inStock", book.getInStock());
        try {
            if (db.insert(BookDAO.TABLE, null, values) <= 0)
                return false;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }
        return true;
    }

    public int getInStockBookById(String id) {

        int inStock = 0;

        String sql = "SELECT inStock FROM Book WHERE idBook=?";
        String[] selectionArgs = {id};

        Cursor cursor = db.rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        inStock += cursor.getInt(0);

        cursor.close();

        return inStock;
    }

    public List<Book> getAllListBook() {
        List<Book> books = new ArrayList<>();

        String sql = "SELECT idBook, B.idCategory, author, price, inStock, CB.Name, CB.Describe, CB.Location , publisher " +
                "FROM " + TABLE + " AS 'B' " +
                "INNER JOIN " + CategoryBookDAO.TABLE_NAME + " AS 'CB' ON B.idCategory = CB.idCategory";

        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            //Lấy thông tin sách
            String idBook = cursor.getString(0);
            String author = cursor.getString(2);
            float price = cursor.getFloat(3);
            int inStock = cursor.getInt(4);
            String publisher = cursor.getString(8);

            //Lấy thông tin Thể loại sách
            String categoryName = cursor.getString(5);
            String idCategory = cursor.getString(1);
            String describe = cursor.getString(6);
            int location = cursor.getInt(7);

            Book book = new Book(idBook, author, publisher, new CategoryBook(idCategory, categoryName, describe, location), price
                    , inStock);

            books.add(book);

            cursor.moveToNext();
        }

        cursor.close();

        return books;
    }

    public boolean updateBook(Book book) {
        ContentValues values = new ContentValues();
        values.put("idBook", book.getIdBook());
        values.put("idCategory", book.getCategoryBook().getId());
        values.put("author", book.getAuthor());
        values.put("price", book.getPrice());
        values.put("inStock", book.getInStock());

        String whereClause = "idBook=?";
        String[] whereArgs = {book.getIdBook()};

        try {
            if (db.update(TABLE, values, whereClause, whereArgs) <= 0)
                return false;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }
        return true;
    }

    public boolean deleteBook(Book book) {
        String whereClause = "idBook=?";
        String[] whereArgs = {book.getIdBook()};

        return db.delete(TABLE, whereClause, whereArgs) > 0;
    }

    public void connectDatabase() {
        db = dbHelper.getWritableDatabase();
    }

    public void disconnectDatabase() {
        db.close();
    }
}
