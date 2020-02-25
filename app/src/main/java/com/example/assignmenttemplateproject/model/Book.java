package com.example.assignmenttemplateproject.model;

import androidx.annotation.NonNull;

public class Book {

    private String idBook, author, publisher;
    private CategoryBook categoryBook;
    private float price;
    private int inStock;

    public Book(String idBook, String author, String publisher, CategoryBook categoryBook, float price, int inStock) {
        this.idBook = idBook;
        this.author = author;
        this.publisher = publisher;
        this.categoryBook = categoryBook;
        this.price = price;
        this.inStock = inStock;
    }

    public String getIdBook() {
        return idBook;
    }

    public void setIdBook(String idBook) {
        this.idBook = idBook;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public CategoryBook getCategoryBook() {
        return categoryBook;
    }

    public void setCategoryBook(CategoryBook categoryBook) {
        this.categoryBook = categoryBook;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    @NonNull
    @Override
    public String toString() {
        return this.idBook;
    }
}
