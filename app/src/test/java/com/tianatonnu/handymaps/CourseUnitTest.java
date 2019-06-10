package com.tianatonnu.handymaps;

import org.junit.Test;
import static org.junit.Assert.*;

public class CourseUnitTest {

    @Test
    public void courseCardTest01(){
        String department = "ES";
        String courseNumber = " 350";
        String sectionNumber = "71";
        String courseName = "Gender, Race, Culture, Science and Technology";
        String courseBldg = "Math & Science - 38";
        String courseRoom = "0218";
        double[] coordinates = new double[]{-120.66199690103531,35.30118890411341};
        String time = "5:40 PM to 6:00 PM on MW";

        Course testCourse = new Course(department,courseNumber,sectionNumber,courseName,courseBldg,courseRoom,coordinates,time);

        String expectedCard = department + courseNumber + "-" + sectionNumber + ": " + courseName + "\n";
        expectedCard += "Building: " + courseBldg + "\n";
        expectedCard += "Room: " + courseRoom + "\n";
        expectedCard += "Time: " + time;

        assertEquals(expectedCard,testCourse.createCard());
    }

    @Test
    public void courseCardTest02(){
        String department = "ASCI";
        String courseNumber = " 214";
        String sectionNumber = "02";
        String courseName = "Equine Management";
        String courseBldg = "Horse Unit - 32";
        String courseRoom = "0001";
        double[] coordinates = new double[]{-120.66199690103531,35.30118890411341};
        String time = "8:10 AM to 11:00 AM on TR";

        Course testCourse = new Course(department,courseNumber,sectionNumber,courseName,courseBldg,courseRoom,coordinates,time);

        String expectedCard = department + courseNumber + "-" + sectionNumber + ": " + courseName + "\n";
        expectedCard += "Building: " + courseBldg + "\n";
        expectedCard += "Room: " + courseRoom + "\n";
        expectedCard += "Time: " + time;

        assertEquals(expectedCard,testCourse.createCard());
    }

    // Compare different Courses based on department
    @Test
    public void courseComparatorTest01(){
        String department01 = "ASCI";

        String courseNumber01 = " 214";
        String sectionNumber01 = "02";
        String courseName01 = "Equine Management";
        String courseBldg01 = "Horse Unit - 32";
        String courseRoom01 = "0001";
        double[] coordinates01 = new double[]{-120.66199690103531,35.30118890411341};
        String time01 = "8:10 AM to 11:00 AM on TR";

        String department02 = "BRAE";

        String courseNumber02 = " 214";
        String sectionNumber02 = "02";
        String courseName02 = "Equine Management";
        String courseBldg02 = "Horse Unit - 32";
        String courseRoom02 = "0001";
        double[] coordinates02 = new double[]{-120.66199690103531,35.30118890411341};
        String time02 = "8:10 AM to 11:00 AM on TR";

        Course testCourse01 = new Course(department01,courseNumber01,sectionNumber01,courseName01,courseBldg01,courseRoom01,coordinates01,time01);

        Course testCourse02 = new Course(department02,courseNumber02,sectionNumber02,courseName02,courseBldg02,courseRoom02,coordinates02,time02);

        assertEquals(-1,testCourse01.compareTo(testCourse02));
    }

    // Compare different Courses based on department
    @Test
    public void courseComparatorTest02(){
        String department01 = "ASCI";

        String courseNumber01 = " 214";
        String sectionNumber01 = "02";
        String courseName01 = "Equine Management";
        String courseBldg01 = "Horse Unit - 32";
        String courseRoom01 = "0001";
        double[] coordinates01 = new double[]{-120.66199690103531,35.30118890411341};
        String time01 = "8:10 AM to 11:00 AM on TR";

        String department02 = "AERO";

        String courseNumber02 = " 214";
        String sectionNumber02 = "02";
        String courseName02 = "Equine Management";
        String courseBldg02 = "Horse Unit - 32";
        String courseRoom02 = "0001";
        double[] coordinates02 = new double[]{-120.66199690103531,35.30118890411341};
        String time02 = "8:10 AM to 11:00 AM on TR";

        Course testCourse01 = new Course(department01,courseNumber01,sectionNumber01,courseName01,courseBldg01,courseRoom01,coordinates01,time01);

        Course testCourse02 = new Course(department02,courseNumber02,sectionNumber02,courseName02,courseBldg02,courseRoom02,coordinates02,time02);

        assertEquals(14,testCourse01.compareTo(testCourse02));
    }

    // Compare Courses with same department but different course numbers
    @Test
    public void courseComparatorTest03(){
        String department01 = "ASCI";
        String courseNumber01 = " 214";

        String sectionNumber01 = "02";
        String courseName01 = "Equine Management";
        String courseBldg01 = "Horse Unit - 32";
        String courseRoom01 = "0001";
        double[] coordinates01 = new double[]{-120.66199690103531,35.30118890411341};
        String time01 = "8:10 AM to 11:00 AM on TR";

        String department02 = "ASCI";
        String courseNumber02 = " 101";

        String sectionNumber02 = "02";
        String courseName02 = "Equine Management";
        String courseBldg02 = "Horse Unit - 32";
        String courseRoom02 = "0001";
        double[] coordinates02 = new double[]{-120.66199690103531,35.30118890411341};
        String time02 = "8:10 AM to 11:00 AM on TR";

        Course testCourse01 = new Course(department01,courseNumber01,sectionNumber01,courseName01,courseBldg01,courseRoom01,coordinates01,time01);

        Course testCourse02 = new Course(department02,courseNumber02,sectionNumber02,courseName02,courseBldg02,courseRoom02,coordinates02,time02);

        assertEquals(1,testCourse01.compareTo(testCourse02));
    }

    // Compare Courses with same department but different course numbers
    @Test
    public void courseComparatorTest04(){
        String department01 = "ASCI";
        String courseNumber01 = " 214";

        String sectionNumber01 = "02";
        String courseName01 = "Equine Management";
        String courseBldg01 = "Horse Unit - 32";
        String courseRoom01 = "0001";
        double[] coordinates01 = new double[]{-120.66199690103531,35.30118890411341};
        String time01 = "8:10 AM to 11:00 AM on TR";

        String department02 = "ASCI";
        String courseNumber02 = " 319";

        String sectionNumber02 = "02";
        String courseName02 = "Equine Management";
        String courseBldg02 = "Horse Unit - 32";
        String courseRoom02 = "0001";
        double[] coordinates02 = new double[]{-120.66199690103531,35.30118890411341};
        String time02 = "8:10 AM to 11:00 AM on TR";

        Course testCourse01 = new Course(department01,courseNumber01,sectionNumber01,courseName01,courseBldg01,courseRoom01,coordinates01,time01);

        Course testCourse02 = new Course(department02,courseNumber02,sectionNumber02,courseName02,courseBldg02,courseRoom02,coordinates02,time02);

        assertEquals(-1,testCourse01.compareTo(testCourse02));
    }

    // Compare Courses with same department & course number but different sections
    @Test
    public void courseComparatorTest05(){
        String department01 = "ASCI";
        String courseNumber01 = " 214";
        String sectionNumber01 = "01";

        String courseName01 = "Equine Management";
        String courseBldg01 = "Horse Unit - 32";
        String courseRoom01 = "0001";
        double[] coordinates01 = new double[]{-120.66199690103531,35.30118890411341};
        String time01 = "8:10 AM to 11:00 AM on TR";

        String department02 = "ASCI";
        String courseNumber02 = " 214";
        String sectionNumber02 = "02";

        String courseName02 = "Equine Management";
        String courseBldg02 = "Horse Unit - 32";
        String courseRoom02 = "0001";
        double[] coordinates02 = new double[]{-120.66199690103531,35.30118890411341};
        String time02 = "8:10 AM to 11:00 AM on TR";

        Course testCourse01 = new Course(department01,courseNumber01,sectionNumber01,courseName01,courseBldg01,courseRoom01,coordinates01,time01);

        Course testCourse02 = new Course(department02,courseNumber02,sectionNumber02,courseName02,courseBldg02,courseRoom02,coordinates02,time02);

        assertEquals(-1,testCourse01.compareTo(testCourse02));
    }

    // Compare Courses with same department & course number but different sections
    @Test
    public void courseComparatorTest06(){
        String department01 = "ASCI";
        String courseNumber01 = " 214";
        String sectionNumber01 = "07";

        String courseName01 = "Equine Management";
        String courseBldg01 = "Horse Unit - 32";
        String courseRoom01 = "0001";
        double[] coordinates01 = new double[]{-120.66199690103531,35.30118890411341};
        String time01 = "8:10 AM to 11:00 AM on TR";

        String department02 = "ASCI";
        String courseNumber02 = " 214";
        String sectionNumber02 = "02";

        String courseName02 = "Equine Management";
        String courseBldg02 = "Horse Unit - 32";
        String courseRoom02 = "0001";
        double[] coordinates02 = new double[]{-120.66199690103531,35.30118890411341};
        String time02 = "8:10 AM to 11:00 AM on TR";

        Course testCourse01 = new Course(department01,courseNumber01,sectionNumber01,courseName01,courseBldg01,courseRoom01,coordinates01,time01);

        Course testCourse02 = new Course(department02,courseNumber02,sectionNumber02,courseName02,courseBldg02,courseRoom02,coordinates02,time02);

        assertEquals(5,testCourse01.compareTo(testCourse02));
    }

    // Compare two Identical Courses
    @Test
    public void courseComparatorTest07(){
        String department01 = "ASCI";
        String courseNumber01 = " 214";
        String sectionNumber01 = "01";
        String courseName01 = "Equine Management";
        String courseBldg01 = "Horse Unit - 32";
        String courseRoom01 = "0001";
        double[] coordinates01 = new double[]{-120.66199690103531,35.30118890411341};
        String time01 = "8:10 AM to 11:00 AM on TR";

        String department02 = "ASCI";
        String courseNumber02 = " 214";
        String sectionNumber02 = "01";
        String courseName02 = "Equine Management";
        String courseBldg02 = "Horse Unit - 32";
        String courseRoom02 = "0001";
        double[] coordinates02 = new double[]{-120.66199690103531,35.30118890411341};
        String time02 = "8:10 AM to 11:00 AM on TR";

        Course testCourse01 = new Course(department01,courseNumber01,sectionNumber01,courseName01,courseBldg01,courseRoom01,coordinates01,time01);

        Course testCourse02 = new Course(department02,courseNumber02,sectionNumber02,courseName02,courseBldg02,courseRoom02,coordinates02,time02);

        assertEquals(0,testCourse01.compareTo(testCourse02));
    }
}
