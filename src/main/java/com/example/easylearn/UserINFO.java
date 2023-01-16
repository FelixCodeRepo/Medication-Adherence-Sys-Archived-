package com.example.easylearn;

public class UserINFO {
    String Medication;
    String Name;
    int num;

    public UserINFO(String Medication, String Name, int num){
        this.Medication = Medication;
        this.Name = Name;
        this.num = num;
    }

    public String getName() {
        return Name;
    }

    public int getNum() {
        return num;
    }

    public String getMedication() {
        return Medication;
    }

}
