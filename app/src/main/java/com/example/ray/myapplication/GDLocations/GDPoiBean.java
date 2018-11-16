package com.example.ray.myapplication.GDLocations;

public class GDPoiBean {
    private String address;
    private String name;
    private double lat;
    private double lon;
    GDPoiBean(String address,String name,double lat,double lon){
        this.address = address;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setLat(double lat){
        this.lat = lat;
    }
    public void setLon(double lon){
        this.lon = lon;
    }
    public String getAddress(){
        return address;
    }
    public String getName(){
        return name;
    }
    public double getLat(){
        return lat;
    }
    public double getLon(){
        return lat;
    }

}
