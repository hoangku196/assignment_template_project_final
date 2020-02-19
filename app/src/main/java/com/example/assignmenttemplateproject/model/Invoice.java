package com.example.assignmenttemplateproject.model;

public class Invoice {
    private String idInvoice, date;

    public Invoice(String idInvoice, String date) {
        this.idInvoice = idInvoice;
        this.date = date;
    }

    public String getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(String idInvoice) {
        this.idInvoice = idInvoice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
