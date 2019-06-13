package com.tianatonnu.handymaps;

import com.mapbox.geojson.Point;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SearchUnitTest {
    // Test filter()
    @Test
    public void testFilterNoSearchQuery()
    {
        ArrayList<String> data = new ArrayList<>();
        data.add("CSC 307-01 Introduction to Software Engineering\n");
        data.add("CSC 307-02 Introduction to Software Engineering\n");
        data.add("KINE 250-04 Healthy Living");

        ArrayList<String> expectedResults = new ArrayList<>();
        expectedResults.add("CSC 307-01 Introduction to Software Engineering\n");
        expectedResults.add("CSC 307-02 Introduction to Software Engineering\n");
        expectedResults.add("KINE 250-04 Healthy Living");

        ArrayList<String> results = Search.filter("", data);
        assertTrue(results.equals(expectedResults));
    }

    @Test
    public void testFilterExactSearchQuery()
    {
        ArrayList<String> data = new ArrayList<>();
        data.add("CSC 307-01 Introduction to Software Engineering\n");
        data.add("CSC 307-02 Introduction to Software Engineering\n");
        data.add("KINE 250-04 Healthy Living");

        ArrayList<String> expectedResults = new ArrayList<>();
        expectedResults.add("CSC 307-01 Introduction to Software Engineering\n");

        ArrayList<String> results = Search.filter("CSC 307-01 Introduction to Software Engineering\n", data);
        assertTrue(results.equals(expectedResults));
    }

    @Test
    public void testFilterCloseSearchQuery()
    {
        ArrayList<String> data = new ArrayList<>();
        data.add("CSC 307-01 Introduction to Software Engineering\n");
        data.add("CSC 307-02 Introduction to Software Engineering\n");
        data.add("KINE 250-04 Healthy Living");

        ArrayList<String> expectedResults = new ArrayList<>();
        expectedResults.add("CSC 307-01 Introduction to Software Engineering\n");

        ArrayList<String> results = Search.filter("CSC 307-01", data);
        assertTrue(results.equals(expectedResults));
    }

    @Test
    public void testFilterCourseDepartmentAndNumberWithoutSection()
    {
        ArrayList<String> data = new ArrayList<>();
        data.add("CSC 307-01 Introduction to Software Engineering\n");
        data.add("CSC 307-02 Introduction to Software Engineering\n");
        data.add("KINE 250-04 Healthy Living");
        data.add("CSC 225-01 Introduction to Assembly");

        ArrayList<String> expectedResults = new ArrayList<>();
        expectedResults.add("CSC 307-01 Introduction to Software Engineering\n");
        expectedResults.add("CSC 307-02 Introduction to Software Engineering\n");

        ArrayList<String> results = Search.filter("CSC 307", data);
        assertTrue(results.equals(expectedResults));
    }

    @Test
    public void testFilterCourseDepartment()
    {
        ArrayList<String> data = new ArrayList<>();
        data.add("CSC 307-01 Introduction to Software Engineering\n");
        data.add("CSC 307-02 Introduction to Software Engineering\n");
        data.add("KINE 250-04 Healthy Living");
        data.add("CSC 225-01 Introduction to Assembly\n");

        ArrayList<String> expectedResults = new ArrayList<>();
        expectedResults.add("CSC 307-01 Introduction to Software Engineering\n");
        expectedResults.add("CSC 307-02 Introduction to Software Engineering\n");
        expectedResults.add("CSC 225-01 Introduction to Assembly\n");

        ArrayList<String> results = Search.filter("CSC", data);
        assertTrue(results.equals(expectedResults));
    }

    @Test
    public void testFilterLowercaseAndUppercaseSearch()
    {
        ArrayList<String> data = new ArrayList<>();
        data.add("CSC 307-01 Introduction to Software Engineering\n");
        data.add("CSC 307-02 Introduction to Software Engineering\n");
        data.add("KINE 250-04 Healthy Living");
        data.add("CSC 225-01 Introduction to Assembly\n");

        ArrayList<String> expectedResults = new ArrayList<>();
        expectedResults.add("CSC 307-01 Introduction to Software Engineering\n");

        ArrayList<String> results = Search.filter("csc 307-01 Introduction to software Engineering", data);
        assertTrue(results.equals(expectedResults));
    }

    @Test
    public void testFilterCourseNameSearch()
    {
        ArrayList<String> data = new ArrayList<>();
        data.add("CSC 307-01 Introduction to Software Engineering\n");
        data.add("CSC 307-02 Introduction to Software Engineering\n");
        data.add("KINE 250-04 Healthy Living");
        data.add("CSC 225-01 Introduction to Assembly\n");

        ArrayList<String> expectedResults = new ArrayList<>();
        expectedResults.add("CSC 307-01 Introduction to Software Engineering\n");
        expectedResults.add("CSC 307-02 Introduction to Software Engineering\n");

        ArrayList<String> results = Search.filter("Introduction to Software Engineering", data);
        assertTrue(results.equals(expectedResults));
    }

    @Test
    public void testFilterLowercasePartialNameSearch()
    {
        ArrayList<String> data = new ArrayList<>();
        data.add("CSC 307-01 Introduction to Software Engineering\n");
        data.add("CSC 307-02 Introduction to Software Engineering\n");
        data.add("KINE 250-04 Healthy Living");
        data.add("CSC 225-01 Introduction to Assembly\n");

        ArrayList<String> expectedResults = new ArrayList<>();
        expectedResults.add("CSC 307-01 Introduction to Software Engineering\n");
        expectedResults.add("CSC 307-02 Introduction to Software Engineering\n");

        ArrayList<String> results = Search.filter("Intro to software Eng", data);
        assertTrue(results.equals(expectedResults));
    }

    @Test
    public void testFilterLowercaseSingleWordSearch()
    {
        ArrayList<String> data = new ArrayList<>();
        data.add("CSC 307-01 Introduction to Software Engineering\n");
        data.add("CSC 307-02 Introduction to Software Engineering\n");
        data.add("KINE 250-04 Healthy Living");
        data.add("CSC 225-01 Introduction to Assembly\n");

        ArrayList<String> expectedResults = new ArrayList<>();
        expectedResults.add("CSC 307-01 Introduction to Software Engineering\n");
        expectedResults.add("CSC 307-02 Introduction to Software Engineering\n");
        expectedResults.add("CSC 225-01 Introduction to Assembly\n");

        ArrayList<String> results = Search.filter("introduction", data);
        assertTrue(results.equals(expectedResults));
    }

    // Test findCoordinates()
    @Test
    public void testFindingBuildingLocation()
    {
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(new Building(new double[]{-120.653077, 35.297469},"Yosemite Hall Tower 8","1"));
        locations.add(new Building(new double[]{-120.650125, 35.019283}, "Engineering IV", "192"));

        Point expectedLocation = Point.fromLngLat(-120.653077, 35.297469);

        Point actualResult = Search.findCoordinates(locations, "1: Yosemite Hall Tower 8\n");

        assertEquals(expectedLocation.longitude(), actualResult.longitude(), 0.0001);
        assertEquals(expectedLocation.latitude(), actualResult.latitude(), 0.0001);
    }

    @Test
    public void testFindingClassroomLocation()
    {
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(new Building(new double[]{-120.653077, 35.297469},"Yosemite Hall Tower 8","1"));
        locations.add(new Building(new double[]{-120.650125, 35.019283}, "Engineering IV", "192"));

        locations.add(new Classroom("Math & Science", "38", "101", new double[] {-120.631245, 35.369874}));
        locations.add(new Classroom("Baker Science", "180", "305", new double[] {-120.612358, 35.123456}));

        Point expectedLocation = Point.fromLngLat(-120.631245, 35.369874);

        Point actualResult = Search.findCoordinates(locations, "38-101\nBuilding: Math & Science\nRoom: 101");

        assertEquals(expectedLocation.longitude(), actualResult.longitude(), 0.0001);
        assertEquals(expectedLocation.latitude(), actualResult.latitude(), 0.0001);
    }

    @Test
    public void testFindingCourseLocation()
    {
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(new Building(new double[]{-120.653077, 35.297469},"Yosemite Hall Tower 8","1"));
        locations.add(new Building(new double[]{-120.650125, 35.019283}, "Engineering IV", "192"));

        locations.add(new Classroom("Math & Science", "38", "101", new double[] {-120.631245, 35.369874}));
        locations.add(new Classroom("Baker Science", "180", "305", new double[] {-120.612358, 35.123456}));

        // Make 1st Course
        String department = "ES";
        String courseNumber = " 350";
        String sectionNumber = "71";
        String courseName = "Gender, Race, Culture, Science and Technology";
        String courseBldg = "Math & Science - 38";
        String courseRoom = "0218";
        double[] coordinates = new double[]{-120.66199690103531,35.30118890411341};
        String time = "5:40 PM to 6:00 PM on MW";

        locations.add(new Course(department,courseNumber,sectionNumber,courseName,courseBldg,courseRoom,coordinates,time));

        // Make 2nd Course
        department = "ASCI";
        courseNumber = " 214";
        sectionNumber = "02";
        courseName = "Equine Management";
        courseBldg = "Horse Unit - 32";
        courseRoom = "0001";
        coordinates = new double[]{-120.66199690103531,35.30118890411341};
        time = "8:10 AM to 11:00 AM on TR";

        locations.add(new Course(department,courseNumber,sectionNumber,courseName,courseBldg,courseRoom,coordinates,time));

        Point expectedLocation = Point.fromLngLat(-120.661996, 35.301188);

        Point actualResult = Search.findCoordinates(locations, "ASCI 214-02: Equine Management\nBuilding: Horse Unit - 32\nRoom: 0001\nTime: 8:10 AM to 11:00 AM on TR");

        assertEquals(expectedLocation.longitude(), actualResult.longitude(), 0.0001);
        assertEquals(expectedLocation.latitude(), actualResult.latitude(), 0.0001);
    }

    @Test
    public void testFindingInvalidLocation()
    {
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(new Building(new double[]{-120.653077, 35.297469},"Yosemite Hall Tower 8","1"));
        locations.add(new Building(new double[]{-120.650125, 35.019283}, "Engineering IV", "192"));

        locations.add(new Classroom("Math & Science", "38", "101", new double[] {-120.631245, 35.369874}));
        locations.add(new Classroom("Baker Science", "180", "305", new double[] {-120.612358, 35.123456}));

        // Make 1st Course
        String department = "ES";
        String courseNumber = " 350";
        String sectionNumber = "71";
        String courseName = "Gender, Race, Culture, Science and Technology";
        String courseBldg = "Math & Science - 38";
        String courseRoom = "0218";
        double[] coordinates = new double[]{-120.667513,35.325647};
        String time = "5:40 PM to 6:00 PM on MW";

        locations.add(new Course(department,courseNumber,sectionNumber,courseName,courseBldg,courseRoom,coordinates,time));

        // Make 2nd Course
        department = "ASCI";
        courseNumber = " 214";
        sectionNumber = "02";
        courseName = "Equine Management";
        courseBldg = "Horse Unit - 32";
        courseRoom = "0001";
        coordinates = new double[]{-120.661789,35.3301245};
        time = "8:10 AM to 11:00 AM on TR";

        locations.add(new Course(department,courseNumber,sectionNumber,courseName,courseBldg,courseRoom,coordinates,time));

        Point actualResult = Search.findCoordinates(locations, "CSC 307-01: Equine Management\nBuilding: Engineering East - 32\nRoom: 0127\nTime: 8:10 AM to 11:00 AM on TR");

        assertNull(actualResult);
    }

    // Test findCourse()
    @Test
    public void testFindingValidCourse()
    {
        Course[] courses = new Course[2];

        // Make 1st Course
        String department = "ES";
        String courseNumber = " 350";
        String sectionNumber = "71";
        String courseName = "Gender, Race, Culture, Science and Technology";
        String courseBldg = "Math & Science - 38";
        String courseRoom = "0218";
        double[] coordinates = new double[]{-120.657894,35.789654};
        String time = "5:40 PM to 6:00 PM on MW";

        courses[0] = new Course(department,courseNumber,sectionNumber,courseName,courseBldg,courseRoom,coordinates,time);

        // Make 2nd Course
        department = "ASCI";
        courseNumber = " 214";
        sectionNumber = "02";
        courseName = "Equine Management";
        courseBldg = "Horse Unit - 32";
        courseRoom = "0001";
        coordinates = new double[]{-120.654723,35.123456};
        time = "8:10 AM to 11:00 AM on TR";

        courses[1] = new Course(department,courseNumber,sectionNumber,courseName,courseBldg,courseRoom,coordinates,time);

        Course expectedResult = new Course(department,courseNumber,sectionNumber,courseName,courseBldg,courseRoom,coordinates,time);

        Course actualResult = Search.findCourse(courses, "ASCI 214-02: Equine Management\nBuilding: Horse Unit - 32\nRoom: 0001\nTime: 8:10 AM to 11:00 AM on TR");

        assertEquals(expectedResult.getDept(), actualResult.getDept());
        assertEquals(expectedResult.getCourseNumber(), actualResult.getCourseNumber());
        assertEquals(expectedResult.getSectionNumber(), actualResult.getSectionNumber());
        assertEquals(expectedResult.getCourseName(), actualResult.getCourseName());
        assertEquals(expectedResult.getCourseBldg(), actualResult.getCourseBldg());
        assertEquals(expectedResult.getCourseRoom(), actualResult.getCourseRoom());
        assertEquals(expectedResult.getLongitude(), actualResult.getLongitude(), 0.00001);
        assertEquals(expectedResult.getLatitude(), actualResult.getLatitude(), 0.00001);
        assertEquals(expectedResult.getRawTime(), actualResult.getRawTime());
    }

    @Test
    public void testFindingInvalidCourse()
    {
        Course[] courses = new Course[2];

        // Make 1st Course
        String department = "ES";
        String courseNumber = " 350";
        String sectionNumber = "71";
        String courseName = "Gender, Race, Culture, Science and Technology";
        String courseBldg = "Math & Science - 38";
        String courseRoom = "0218";
        double[] coordinates = new double[]{-120.657894,35.789654};
        String time = "5:40 PM to 6:00 PM on MW";

        courses[0] = new Course(department,courseNumber,sectionNumber,courseName,courseBldg,courseRoom,coordinates,time);

        // Make 2nd Course
        department = "ASCI";
        courseNumber = " 214";
        sectionNumber = "02";
        courseName = "Equine Management";
        courseBldg = "Horse Unit - 32";
        courseRoom = "0001";
        coordinates = new double[]{-120.654723,35.123456};
        time = "8:10 AM to 11:00 AM on TR";

        courses[1] = new Course(department,courseNumber,sectionNumber,courseName,courseBldg,courseRoom,coordinates,time);

        Course actualResult = Search.findCourse(courses, "CSC 307-01: Equine Management\nBuilding: Engineering East - 32\nRoom: 0127\nTime: 8:10 AM to 11:00 AM on MWF");

        assertNull(actualResult);
    }
}
