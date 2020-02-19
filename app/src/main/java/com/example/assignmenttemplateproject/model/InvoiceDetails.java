package com.example.assignmenttemplateproject.model;

public class InvoiceDetails {

    private String idDetails;
    private int amount;
    private Book book;
    private Invoice invoice;

    public InvoiceDetails(String idDetails, int amount, Book book, Invoice invoice) {
        this.idDetails = idDetails;
        this.amount = amount;
        this.book = book;
        this.invoice = invoice;
    }

    public String getIdDetails() {
        return idDetails;
    }

    public void setIdDetails(String idDetails) {
        this.idDetails = idDetails;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
