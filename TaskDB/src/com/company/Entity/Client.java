package com.company.Entity;

public class Client {
    public int ID;
    public String Name;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Client(){}
    public Client(int id, String n){
        ID = id;
        Name = n;
    }

}

