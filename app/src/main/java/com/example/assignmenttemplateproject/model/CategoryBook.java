package com.example.assignmenttemplateproject.model;

import androidx.annotation.NonNull;

public class CategoryBook {
    private String id, name, describe;
    private int location;

    public CategoryBook(String id, String name, String describe, int location) {
        this.id = id;
        this.name = name;
        this.describe = describe;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
