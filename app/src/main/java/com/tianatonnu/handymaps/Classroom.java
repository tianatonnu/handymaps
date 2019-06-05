package com.tianatonnu.handymaps;

public class Classroom implements Location, Comparable<Classroom>{
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

    public double getLatitude(){
        return location[1];
    }

    public double getLongitude(){
        return location[0];
    }

    public String createCard(){
        String card = classRoomBldgNumber + "-" + roomNumber + "\n";
        card += "Building: " + classRoomBldgName + "\n";
        card += "Room: " + roomNumber;
        return card;
    }

    @Override
    public int compareTo(Classroom other) {
        // The String compare method works even when building numbers or room numbers contain a Letter
        int compVar = this.classRoomBldgNumber.compareTo(other.getBlgNumber());

        if(compVar < 0){
            return -1;
        }
        else if (compVar > 0){
            return 1;
        }
        // If it is the same building, then compare room numbers
        else {
            compVar = this.roomNumber.compareTo(other.getRoomNumber());
            if(compVar < 0){
                return -1;
            }
            else if(compVar > 0){
                return 1;
            }
            return 0;
        }
    }
}