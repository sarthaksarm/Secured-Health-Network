package com.sark.securedhealthnet;

import java.util.HashMap;

public class BlogPatientPro {
    int Appointment;
    String Phone;
    private String Name;
    private String Location;
    String Date;

    public BlogPatientPro()
    {

    }
    public BlogPatientPro(String Location, String Name, String Phone, int Appointment, String Date){
        this.Name = Name;
        this.Location = Location;
        this.Phone=Phone;
        this.Appointment=Appointment;
        this.Date=Date;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getAppointment() {
        return Appointment;
    }

    public void setAppointment(int appointment) {
        Appointment = appointment;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

//    public HashMap<String, String> getReports() {
//        return reports;
//    }
//
//    public void setReports(HashMap<String, String> reports) {
//        this.reports = reports;
//    }

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

}
