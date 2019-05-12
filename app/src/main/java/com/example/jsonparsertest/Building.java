package com.example.jsonparsertest;

public class Building {
    private double[] location = new double[2];
    private String bldgName;
    private String bldNumber;

    public Building(double[] location, String name, String number){
        this.location[0] = location[0];
        this.location[1] = location[1];

        bldgName = name;
        bldNumber = number;
    }

    public String getBuildingName(){
        return bldgName;
    }

    public String getBuildingNumber(){
        return bldNumber;
    }

    public double getBuildingLatitude(){
        return location[0];
    }

    public double getBuildingLongitude(){
        return location[1];
    }
}
