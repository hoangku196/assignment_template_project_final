package com.example.assignmenttemplateproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.assignmenttemplateproject.database.DatabaseHelper;
import com.example.assignmenttemplateproject.model.Invoice;

import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private String TAG = this.getClass().getSimpleName();

    public static final String SQL_INVOICE = "CREATE TABLE Invoice(" +
            "idInvoice NCHAR(7) PRIMARY KEY NOT NULL, " +
            "dateInvoice DATE NOT NULL)";
    public static final String TABLE = "Invoice";

    public InvoiceDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public boolean insertNewInvoice(Invoice invoice) {
        ContentValues values = new ContentValues();
        values.put("idInvoice", invoice.getIdInvoice());
        values.put("dateInvoice", invoice.getDate());
        try {
            if (db.insert(TABLE, null, values) <= 0)
                return false;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }

        return true;
    }

    public List<Invoice> getAllInvoice() {
        List<Invoice> invoices = new ArrayList<>();

        Cursor cursor = db.query(TABLE, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String idInvoice = cursor.getString(0);
            String date = cursor.getString(1);

            Invoice invoice = new Invoice(idInvoice, date);
            invoices.add(invoice);

            cursor.moveToNext();
        }

        cursor.close();

        return invoices;
    }

    public boolean deleteInvoice(Invoice invoice) {
        String whereClause = "idInvoice=?";
        String[] whereArgs = {invoice.getIdInvoice()};

        return db.delete(TABLE, whereClause, whereArgs) > 0;
    }

    public boolean updateInvoice(Invoice invoice) {
        ContentValues values = new ContentValues();
        values.put("idInvoice", invoice.getIdInvoice());
        values.put("dateInvoice", invoice.getDate());

        String whereClause = "idInvoice=?";
        String[] whereArgs = {invoice.getIdInvoice()};

        try {
            if (db.update(TABLE, values, whereClause, whereArgs) <= 0)
                return false;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }

        return true;
    }

    public final void connectDatabase() {
        db = dbHelper.getWritableDatabase();
    }

    public final void disconnectDatabase() {
        db.close();
    }
}
