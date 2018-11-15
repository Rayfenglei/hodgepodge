package com.example.f2846843.myapplication.SQLite;


public class Order {
    public int id;
    public String customName;
    public int orderPrice;
    public String country;

    public Order() {
    }

    public Order(int id, String customName, int orderPrice, String country) {
        this.id = id;
        this.customName = customName;
        this.orderPrice = orderPrice;
        this.country = country;
    }

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
    public void setOrderPrice(int orderPrice){
        this.orderPrice = orderPrice;
    }
    public int getOrderPrice(){
        return orderPrice;
    }

    public void setCustomName(String customName){
        this.customName = customName;
    }
    public String getCustomName(){
        return customName;
    }
    public void setCountry( String country){
        this.country = country;
    }
    public String getCountry(){
        return country;
    }

}
