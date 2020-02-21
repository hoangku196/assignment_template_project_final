package com.example.assignmenttemplateproject.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
        float price = 0;
        int total = 0;

        Calendar calendar = Calendar.getInstance();
//
//        int day = calendar.get(Calendar.DAY_OF_WEEK);
//        int month = calendar.get(Calendar.MONTH);
//        int year = calendar.get(Calendar.YEAR);

        SimpleDateFormat sdf = new SimpleDateFormat("DD-MM-YYYY");
        String today = sdf.format(calendar.getTime());

        String sql = "SELECT id.amount, B.price FROM InvoiceDetails As 'ID' " +
                "INNER JOIN BOOK As 'B' " +
                "ON B.idBook = ID.idBook " +
                "INNER JOIN INVOICE AS 'I' " +
                "ON I.idBill = ID.idBill " +
                "WHERE I.dateInvoice LIKE ?";

        String[] selectionArgs = {today};

        Cursor cursor = db.rawQuery(sql, selectionArgs);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            total += cursor.getInt(0);
            price += cursor.getFloat(1);
        }

        cursor.close();

        return total * price;
    }

    public float getTotalDailySaleByMonth() {
        float price = 0;
        int total = 0;

        Calendar calendar = Calendar.getInstance();

        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        String today = month + "-" + year;

        String sql = "SELECT id.amount, B.price FROM InvoiceDetails As 'ID' " +
                "INNER JOIN BOOK As 'B' " +
                "ON B.idBook = ID.idBook " +
                "INNER JOIN INVOICE AS 'I' " +
                "ON I.idBill = ID.idBill " +
                "WHERE I.dateInvoice LIKE ?";

        String[] selectionArgs = {"__-" + today};

        Cursor cursor = db.rawQuery(sql, selectionArgs);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            total += cursor.getInt(0);
            price += cursor.getFloat(1);
        }

        cursor.close();

        return total * price;
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
                "ON I.idBill = ID.idBill " +
                "WHERE I.dateInvoice LIKE ?";

        String[] selectionArgs = {"__-__-" + year};

        Cursor cursor = db.rawQuery(sql, selectionArgs);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            total += cursor.getInt(0);
            price += cursor.getFloat(1);
        }

        cursor.close();

        return total * price;
    }

    public List<ListBestSaleAdapter.SaleItem> searchBestSale(String month) {
        List<ListBestSaleAdapter.SaleItem> saleItems = new ArrayList<>();

        if (Integer.parseInt(month) < 10) {
            month = "0" + month;
        }

        int year = Calendar.getInstance().get(Calendar.YEAR);

        String sql = "SELECT B.IdBook, SUM(ID.amount) As 'Total' from Book As 'B' " +
                "INNER JOIN InvoiceDetails As 'ID' " +
                "ON B.idBook = ID.idBook " +
                "INNER JOIN Invoice as 'I' " +
                "ON I.idInvoice = ID.idInvoice " +
                "WHERE dateInvoice LIKE ? " +
                "GROUP BY B.idBook " +
                "ORDER BY Total DESC LIMIT 10";

        String[] selectionArgs = {"__-" + month + "-" + year};

        Cursor cursor = db.rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String id = cursor.getString(0);
            int amount = cursor.getInt(1);

            ListBestSaleAdapter.SaleItem item = new ListBestSaleAdapter.SaleItem(id, amount);
            saleItems.add(item);
            cursor.moveToNext();
        }

        return saleItems;
    }

    public void connectDatabase() {
        db = dbHelper.getWritableDatabase();
    }

    public void disconnectDatabase() {
        db.close();
    }
}
