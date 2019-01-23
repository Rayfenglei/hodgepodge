package com.example.ray.myapplication.UIView.StickyRecycler;

public class CityBean {
    private String name;
    private String province;

    CityBean(String name, String province) {
        this.name = name;
        this.province = province;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public String toString() {
        return "CityBean{" +
                "name='" + name + '\'' +
                ", province='" + province + '\'' +
                '}';
    }
}
