package com.company.Entity;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int clientID;
    private int orderID;
    private String productTitle;
    public List<Product> productList;

    public int getClientID()
    { return clientID; }

    public int getOrderID()
    { return orderID; }

    public String getProductTitle()
    { return productTitle; }

    public void setClientID(int value){ clientID = value; }

    public void setOrderID(int value) { orderID = value; }

    public void setProductTitle(String value) { productTitle = value; }

    public Order ()
    {
        productList = new ArrayList<Product>();
    }
}
