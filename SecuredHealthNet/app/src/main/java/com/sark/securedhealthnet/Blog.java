package com.sark.securedhealthnet;

public class Blog {
    private String Name;
    private String Age;
    private String Location;
    private String Phone;
    private String Date;
    private String registered;

    public Blog(String Age,String Date, String Location, String Name, String Phone,  String registered) {
        this.Name = Name;
        this.Age = Age;
        this.Location = Location;
        this.Phone=Phone;
        this.Date=Date;
        this.registered=registered;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public Blog()
    {

    }
}
