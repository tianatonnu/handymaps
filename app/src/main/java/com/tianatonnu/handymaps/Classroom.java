package com.tianatonnu.handymaps;

public class Classroom implements Location, Comparable<Classroom>{
    private String classRoomBldgName;
    private String classRoomBldgNumber;
    private String roomNumber;
    private double[] location = new double[2];

    public Classroom(String bldgName, String bldgNumber, String roomNumber, double[] location){
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
        int bldgCompare = compareBuildings(other);

        if(bldgCompare == -1){
            return -1;
        }
        else if (bldgCompare == 1){
            return 1;
        }

        // Buildings are the same, so check rooms
        int compVar = this.roomNumber.compareTo(other.getRoomNumber());

        if(compVar < 0){
            return -1;
        }
        else if(compVar > 0){
            return 1;
        }
        return 0;
    }

    private int compareBuildings(Classroom other){
        boolean c1Digit = Character.isDigit(this.classRoomBldgNumber.charAt(classRoomBldgNumber.length()-1));
        boolean c2Digit = Character.isDigit(other.getBlgNumber().charAt(other.getBlgNumber().length()-1));

        int c1;
        int c2;

        // Create Integers from the String type Building Number
        if(!c1Digit){
            String c1Sub = this.classRoomBldgNumber.substring(0,this.classRoomBldgNumber.length()-1);
            c1 = Integer.parseInt(c1Sub);
        }
        else{
            c1 = Integer.parseInt(this.classRoomBldgNumber);
        }

        if(!c2Digit){
            String c2Sub = other.getBlgNumber().substring(0,other.getBlgNumber().length()-1);
            c2 = Integer.parseInt(c2Sub);
        }
        else {
            c2 = Integer.parseInt(other.getBlgNumber());
        }

        // Compare the Integer type Building Numbers
        if(c1 < c2){
            return -1;
        }
        else if( c1 > c2){
            return 1;
        }

        // Integers are the Same, check if Building has a letter
        if(c1Digit && !c2Digit){
            return -1;
        }
        else if (!c1Digit && c2Digit){
            return 1;
        }
        return 0;
    }
}