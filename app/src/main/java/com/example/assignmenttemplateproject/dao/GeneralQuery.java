package com.example.assignmenttemplateproject.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.assignmenttemplateproject.adapter.ListBestSaleAdapter;
import com.example.assignmenttemplateproject.database.DatabaseHelper;
import com.example.assignmenttemplateproject.model.Book;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GeneralQuery {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public GeneralQuery(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public float getTotalDailySaleByDay() {
        float total = 0;

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String today = sdf.format(calendar.getTime());
        Log.e("today", today);

        String sql = "SELECT SUM(id.amount)*B.price FROM InvoiceDetails As 'ID' " +
                "INNER JOIN BOOK As 'B' " +
                "ON B.idBook = ID.idBook " +
                "INNER JOIN INVOICE AS 'I' " +
                "ON I.idInvoice = ID.idInvoice " +
                "WHERE I.dateInvoice = ?" +
                "GROUP BY ID.idBook";

        String[] selectionArgs = {today};

        Cursor cursor = db.rawQuery(sql, selectionArgs);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            total += cursor.getFloat(0);
            cursor.moveToNext();
        }

        cursor.close();
        Log.e("total", total + "");

        return total;
    }

    public float getTotalDailySaleByMonth() {
        float total = 0;

        Calendar calendar = Calendar.getInstance();


        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
        String today = sdf.format(calendar.getTime());

        String sql = "SELECT SUM(id.amount)*B.price FROM InvoiceDetails As 'ID' " +
                "INNER JOIN BOOK As 'B' " +
                "ON B.idBook = ID.idBook " +
                "INNER JOIN INVOICE AS 'I' " +
                "ON I.idInvoice = ID.idInvoice " +
                "WHERE I.dateInvoice LIKE ?";

        String[] selectionArgs = {"__-%" + today};

        Cursor cursor = db.rawQuery(sql, selectionArgs);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            total += cursor.getInt(0);
            cursor.moveToNext();
        }

        cursor.close();

        return total;
    }

    public float getTotalDailySaleByYear() {
        float price = 0;
        int total = 0;

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);

        String sql = "SELECT id.amount, B.price FROM InvoiceDetails As 'ID' " +
                "INNER JOIN BOOK As 'B' " +
                "ON B.idBook = ID.idBook " +
                "INNER JOIN INVOICE AS 'I' " +
                "ON I.idInvoice = ID.idInvoice " +
                "WHERE I.dateInvoice LIKE ?";

        String[] selectionArgs = {"__-__-" + year};

        Cursor cursor = db.rawQuery(sql, selectionArgs);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            total += cursor.getInt(0);
            price += cursor.getFloat(1);
            cursor.moveToNext();
        }

        cursor.close();

        return total * price;
    }

    public List<ListBestSaleAdapter.SaleItem> searchBestSale(String month) {
        List<ListBestSaleAdapter.SaleItem> saleItems = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int year = calendar.get(Calendar.YEAR);
        calendar.set(year, Integer.parseInt(month) - 1, day);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
        String today = sdf.format(calendar.getTime());

        String sql = "SELECT idBook, SUM(amount) As 'Total' from InvoiceDetails As 'ID' " +
                "INNER JOIN Invoice as 'I' " +
                "ON I.idInvoice = ID.idInvoice " +
                "WHERE I.dateInvoice LIKE ? " +
                "GROUP BY idBook " +
                "ORDER BY Total DESC LIMIT 10";

        String[] selectionArgs = {"__-%" + today};

        Cursor cursor = db.rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String id = cursor.getString(0);
            int amount = cursor.getInt(1);
            Log.e("id", id+amount);

            ListBestSaleAdapter.SaleItem item = new ListBestSaleAdapter.SaleItem(id, amount);
            saleItems.add(item);
            cursor.moveToNext();
        }

        cursor.close();

        return saleItems;
    }

    public void connectDatabase() {
        db = dbHelper.getWritableDatabase();
    }

    public void disconnectDatabase() {
        db.close();
    }
}
