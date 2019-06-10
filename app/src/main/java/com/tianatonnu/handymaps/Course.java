package com.tianatonnu.handymaps;

public class Course implements Location, Comparable<Course> {
    private String dept;
    private String courseNumber;
    private String sectionNumber;
    private String courseName;
    private String courseBldg;
    private String courseRoom;
    private double[] location = new double[2];
    private String rawTime;

    public Course(String department, String courseNumber, String sectionNumber, String courseName, String courseBldg, String courseRoom, double[] coordinates, String time){
        dept = department;
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        this.sectionNumber = sectionNumber;
        this.courseBldg = courseBldg;
        this.courseRoom = courseRoom;
        this.location[0] = coordinates[0];
        this.location[1] = coordinates[1];
        rawTime = time;
    }

    public String getDept(){
        return dept;
    }

    public String getCourseNumber(){
        return courseNumber;
    }

    public String getSectionNumber(){
        return sectionNumber;
    }

    public String getCourseName(){
        return courseName;
    }

    public String getCourseBldg(){
        return courseBldg;
    }

    public String getCourseRoom(){
        return courseRoom;
    }

    public String getRawTime(){
        return rawTime;
    }

    public double getLatitude(){
        return location[1];
    }

    public double getLongitude(){
        return location[0];
    }

    public String createCard(){
        String card = dept + courseNumber + "-" + sectionNumber + ": " + courseName + "\n";
        card += "Building: " + courseBldg + "\n";
        card += "Room: " + courseRoom + "\n";
        card += "Time: " + rawTime;
        return card;
    }

    public int compareTo(Course other)
    {
        return (this.createCard().compareTo(other.createCard()));
    }
}