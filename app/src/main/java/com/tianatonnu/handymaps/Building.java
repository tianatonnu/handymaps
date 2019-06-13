package com.tianatonnu.handymaps;

public class Building implements Location, Comparable<Building>{
    private double[] location = new double[2];
    private String bldgName;
    private String bldgNumber;

    public Building(double[] location, String name, String number){
        this.location[0] = location[0];
        this.location[1] = location[1];

        bldgName = name;
        bldgNumber = number;
    }

    public String getBuildingName(){
        return bldgName;
    }

    public String getBuildingNumber(){
        return bldgNumber;
    }

    public double getLatitude(){
        return location[1];
    }

    public double getLongitude(){
        return location[0];
    }

    public String createCard(){
        return (bldgNumber + ": " + bldgName + "\n");
    }
    @Override
    public int compareTo(Building other){
        boolean b1digit = Character.isDigit(this.bldgNumber.charAt( bldgNumber.length()-1));
        boolean b2digit = Character.isDigit(other.getBuildingNumber().charAt(other.getBuildingNumber().length()-1));

        int b1;
        int b2;
        char b1c;
        char b2c;

        // Create Integers from the String type Building Number
        if(!b1digit){
            String b1sub = this.bldgNumber.substring(0,this.bldgNumber.length()-1);
            b1 = Integer.parseInt(b1sub);
        }
        else{
            b1 = Integer.parseInt(this.bldgNumber);
        }

        if(!b2digit){
            String b2sub = other.getBuildingNumber().substring(0,other.getBuildingNumber().length()-1);
            b2 = Integer.parseInt(b2sub);
        }
        else {
            b2 = Integer.parseInt(other.getBuildingNumber());
        }

        // Compare the Integer type Building Numbers
        if(b1 < b2){
            return -1;
        }
        else if( b1 > b2){
            return 1;
        }

        // Integers are the Same, check if Building has a letter
        if(b1digit && !b2digit){
            return -1;
        }
        else if (!b1digit && b2digit){
            return 1;
        }
        else if(!b1digit && !b2digit){
            b1c = this.bldgNumber.charAt(this.bldgNumber.length() - 1);
            b2c = other.bldgNumber.charAt(other.bldgNumber.length() - 1);
            if(((int) b1c) < ((int) b2c)){
                return -1;
            }
            else if(((int) b1c) > ((int) b2c)){
                return 1;
            }
        }
        return 0;
    }
}
