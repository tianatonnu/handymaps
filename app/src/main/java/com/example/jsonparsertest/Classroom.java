package com.example.jsonparsertest;

public class Classroom {
    private String classRoomBldgName;
    private String classRoomBldgNumber;
    private String roomNumber;
    private double[] location = new double[2];

    public Classroom(String bldgName,String bldgNumber, String roomNumber, double[] location){
        this.classRoomBldgName = bldgName;
        this.classRoomBldgNumber = bldgNumber;
        this.roomNumber = roomNumber;
        this.location[0] = location[0];
        this.location[1] = location[1];
    }

    public String getBldgName(){
        return classRoomBldgName;
    }

    public String getBlgNumber(){
        return classRoomBldgNumber;
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

    public String createCard(){
        String card = classRoomBldgNumber + "-" + roomNumber + "\n";
        card += "Buidling: " + classRoomBldgName + "\n";
        card += "Room: " + roomNumber;
        return card;
    }
}
