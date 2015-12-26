package com.company.Entity;

public class Product {
    public String Title;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Product(){}
    public Product(String t)
    {
        Title = t;
    }
}
