package com.company.Entity;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderID;
    public List<Product> productList;

    public int getOrderID()
    { return orderID; }

    public void setOrderID(int value) { orderID = value; }

    public Order ()
    {
        productList = new ArrayList<Product>();
    }
}
