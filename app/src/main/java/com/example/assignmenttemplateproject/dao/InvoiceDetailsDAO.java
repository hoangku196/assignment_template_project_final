package com.example.assignmenttemplateproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.assignmenttemplateproject.database.DatabaseHelper;
import com.example.assignmenttemplateproject.model.Book;
import com.example.assignmenttemplateproject.model.CategoryBook;
import com.example.assignmenttemplateproject.model.Invoice;
import com.example.assignmenttemplateproject.model.InvoiceDetails;

import java.util.ArrayList;
import java.util.List;

public class InvoiceDetailsDAO {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public static final String SQL_INVOICE_DETAILS = "CREATE TABLE InvoiceDetails(" +
            "idDetails INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "idInvoice NCHAR(7) NOT NULL, " +
            "idBook NCHAR(5) NOT NULL," +
            "amount INTEGER NOT NULL)";
    public static final String TABLE = "InvoiceDetails";

    private final String TAG = this.getClass().getSimpleName();

    public InvoiceDetailsDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public boolean insertNewInvoiceDetails(InvoiceDetails detail) {
        ContentValues values = new ContentValues();
        values.put("idInvoice", detail.getInvoice().getIdInvoice());
        values.put("idBook", detail.getBook().getIdBook());
        values.put("amount", detail.getAmount());
        try {
            if (db.insert(TABLE, null, values) <= 0) {
                return false;
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }

        return true;
    }

    public List<InvoiceDetails> getAllInvoiceDetails(String id) {
        List<InvoiceDetails> detailsList = new ArrayList<>();

        String sql = "SELECT idDetails, ID.idInvoice, ID.idBook, amount, " +
                "B.idCategory, B.author, B.publisher, B.price, B.inStock, " +
                "CB.Name, CB.Describe, CB.Location, " +
                "I.dateInvoice " +
                "FROM InvoiceDetails As 'ID' " +
                "INNER JOIN Invoice As 'I' " +
                "ON I.idInvoice = ID.idInvoice " +
                "INNER JOIN Book As 'B' " +
                "ON B.idBook = ID.idBook " +
                "INNER JOIN CategoryBook As 'CB' " +
                "ON B.idCategory = CB.idCategory " +
                "WHERE ID.idInvoice = ?";

        String[] selectionArgs = {id};

        Cursor cursor = db.rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            String idCategory = cursor.getString(4);
            String categoryName = cursor.getString(9);
            String categoryDescribe = cursor.getString(10);
            int categoryLocation = cursor.getInt(11);
            CategoryBook categoryBook = new CategoryBook(idCategory, categoryName, categoryDescribe, categoryLocation);

            String idBook = cursor.getString(2);
            String author = cursor.getString(5);
            String publisher = cursor.getString(6);
            float price = cursor.getFloat(7);
            int inStock = cursor.getInt(8);
            Book book = new Book(idBook, author, publisher, categoryBook, price, inStock);

            String idInvoice = cursor.getString(1);
            String dateInvoice = cursor.getString(12);
            Invoice invoice = new Invoice(idInvoice, dateInvoice);

            String idDetails = cursor.getString(0);
            int amount = cursor.getInt(3);
            InvoiceDetails detail = new InvoiceDetails(idDetails, amount, book, invoice);

            detailsList.add(detail);

            cursor.moveToNext();
        }

        cursor.close();

        return detailsList;
    }

    public boolean deleteInvoiceDetails(int idDetails) {
        return db.delete(TABLE, "idDetails=" + idDetails, null) > 0;
    }

    public void connectDatabase() {
        db = dbHelper.getWritableDatabase();
    }

    public void disconnectDatabase() {
        db.close();
    }
}
