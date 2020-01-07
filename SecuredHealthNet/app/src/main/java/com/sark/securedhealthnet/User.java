package com.sark.securedhealthnet;

/**
 * Created by delaroy on 3/27/17.
 */
public class User {

    private String name;
    private String email;
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

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
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
