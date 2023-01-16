package com.example.easylearn;

public class MedicationInfo {
    private String medname;
    private String price;
    private String methods;
    private String date;
    private String month;
    private String year;

    public MedicationInfo(String medname, String price, String methods) {
        this.medname = medname;
        this.price = price;
        this.methods = methods;
        this.date = "";
        this.month = "";
        this.year = "";
    }

    public String getMedname() {
        return medname;
    }

    public String getPrice() {
        return price;
    }

    public String getMethods() {
        return methods;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setMonth(String month) {
        this.month = month;
    }
    public void setYear(String year) {
        this.year = year;
    }

    public String getDate() {
        return date;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }
}
