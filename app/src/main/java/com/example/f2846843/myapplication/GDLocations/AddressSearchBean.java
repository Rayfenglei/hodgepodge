package com.example.f2846843.myapplication.GDLocations;

import com.amap.api.maps.model.LatLng;

public class AddressSearchBean {
    private String address;
    private String name;
    private boolean isChecked;
    private LatLng checkedLatlng;
    private int loadType;
    private String endCity;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public LatLng getCheckedLatlng() {
        return checkedLatlng;
    }

    public void setCheckedLatlng(LatLng checkedLatlng) {
        this.checkedLatlng = checkedLatlng;
    }

    public int getLoadType() {
        return loadType;
    }

    public void setLoadType(int loadType) {
        this.loadType = loadType;
    }

    public String getEndCity() {
        return endCity;
    }

    public void setEndCity(String endCity) {
        this.endCity = endCity;
    }
}
