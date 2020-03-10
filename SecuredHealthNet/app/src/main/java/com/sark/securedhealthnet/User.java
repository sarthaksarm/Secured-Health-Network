package com.sark.securedhealthnet;

public class User {

    private String name;
    private String area;
    private String phone;
    private int writings;

    public int getWritings() {
        return writings;
    }

    public void setWritings(int writings) {
        this.writings = writings;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
