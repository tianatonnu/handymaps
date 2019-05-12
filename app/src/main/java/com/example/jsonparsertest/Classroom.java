package com.example.jsonparsertest;

public class Classroom {
    private Building classBldg;
    private String roomNumber;
    private double[] location = new double[2];

    public Classroom(Building bldg, String roomNumber, double[] location){
        classBldg = bldg;
        this.roomNumber = roomNumber;
        this.location[0] = location[0];
        this.location[1] = location[1];
    }

    public Building getClassBldg(){
        return classBldg;
    }

    public String getRoomNumber(){
        return roomNumber;
    }

    public double getClassLatitude(){
        return location[0];
    }

    public double getClassLongitude(){
        return location[1];
    }
}
