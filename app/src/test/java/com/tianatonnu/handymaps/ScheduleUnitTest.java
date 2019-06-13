package com.tianatonnu.handymaps;

import org.junit.Test;
import static org.junit.Assert.*;

public class ScheduleUnitTest {

    // Initializing test fixtures to use throughout (universe of available courses)
    private Course c1 = new Course("CPE", "357", "01", "Systems Programming", "Graphic Arts - 26",
            "0102", new double[]{-120.661721, 35.299276}, "2:10 PM to 4:00 PM on MWF");
    private Course c2 = new Course("ART", "145", "06", "Ceramics I", "Walter F. Dexter Building - 34",
            "0221", new double[]{-120.663439, 35.301133}, "12:10 PM to 3:00 PM on TR");
    private Course c3 = new Course("AG", "102", "24", "Crop Production II", "Agricultural Science - 11",
            "0119", new double[]{-120.662940, 35.302832}, "7:10 AM to 9:00 AM on MWF");
    private Course c4 = new Course("CHEM", "233", "01", "Small Scale Interactions", "Baker Science - 180",
            "0504", new double[]{-120.660738, 35.301273}, "11:10 AM to 2:00 PM on MW");
    private Course c5 = new Course("EE", "467", "01", "Life Engineering for Graduates", "Engineering West - 21",
            "0306", new double[]{-120.662697, 35.299838}, "6:10 PM to 9:00 PM on MW");

    private Course[] classes = {c1, c2, c3, c4, c5};


    // - - - - - - - - - - - - - - - - - - - -
    //         - addCourse() tests -
    // - - - - - - - - - - - - - - - - - - - -
    // 1. Adds classes correctly

    @Test
    public void addOneNewCourseShouldAdd(){
        Schedule testSched = new Schedule();
        String cStr1 = "ART145-06: Ceramics I\nBuilding: Walter F. Dexter Building - 34" +
                "\nRoom: 0221\nTime: 12:10 PM to 3:00 PM on TR";

        assertEquals(testSched.getCourses().size(), 0);
        assertEquals(testSched.getCourseNames().size(), 0);
        testSched.addCourse(classes, cStr1);
        assertEquals(testSched.getCourses().size(), 1);
        assertEquals(testSched.getCourseNames().size(), 1);
        assertEquals(testSched.getCourseNames().get(0), cStr1);
    }

    @Test
    public void addNewCoursesShouldAdd(){
        Schedule testSched = new Schedule();
        String cStr1 = "ART145-06: Ceramics I\nBuilding: Walter F. Dexter Building - 34" +
                "\nRoom: 0221\nTime: 12:10 PM to 3:00 PM on TR";
        String cStr2 = "AG102-24: Crop Production II\nBuilding: Agricultural Science - 11" +
                "\nRoom: 0119\nTime: 7:10 AM to 9:00 AM on MWF";
        String cStr3 = "CHEM233-01: Small Scale Interactions\nBuilding: Baker Science - 180" +
                "\nRoom: 0504\nTime: 11:10 AM to 2:00 PM on MW";
        String cStr4 = "EE467-01: Life Engineering for Graduates\nBuilding: Engineering West - 21" +
                "\nRoom: 0306\nTime: 6:10 PM to 9:00 PM on MW";

        assertEquals(testSched.getCourses().size(), 0);
        assertEquals(testSched.getCourseNames().size(), 0);
        testSched.addCourse(classes, cStr1);
        assertEquals(testSched.getCourses().size(), 1);
        assertEquals(testSched.getCourseNames().size(), 1);
        testSched.addCourse(classes, cStr2);
        assertEquals(testSched.getCourses().size(), 2);
        assertEquals(testSched.getCourseNames().size(), 2);
        testSched.addCourse(classes, cStr3);
        assertEquals(testSched.getCourses().size(), 3);
        assertEquals(testSched.getCourseNames().size(), 3);
        testSched.addCourse(classes, cStr4);
        assertEquals(testSched.getCourses().size(), 4);
        assertEquals(testSched.getCourseNames().size(), 4);
    }

    // 2. Does not add duplicate classes
    @Test
    public void addingDuplicateCoursesShouldNotAdd() {
        Schedule testSched = new Schedule();
        String cStr1 = "CPE357-01: Systems Programming\nBuilding: Graphic Arts - 26" +
                "\nRoom: 0102\nTime: 2:10 PM to 4:00 PM on MWF";
        String cStr2 = "AG102-24: Crop Production II\nBuilding: Agricultural Science - 11" +
                "\nRoom: 0119\nTime: 7:10 AM to 9:00 AM on MWF";
        String cStr3 = "EE467-01: Life Engineering for Graduates\nBuilding: Engineering West - 21" +
                "\nRoom: 0306\nTime: 6:10 PM to 9:00 PM on MW";
        String cStr4 = "CPE357-01: Systems Programming\nBuilding: Graphic Arts - 26" +
                "\nRoom: 0102\nTime: 2:10 PM to 4:00 PM on MWF";
        String cStr5 = "AG102-24: Crop Production II\nBuilding: Agricultural Science - 11" +
                "\nRoom: 0119\nTime: 7:10 AM to 9:00 AM on MWF";

        assertEquals(testSched.getCourses().size(), 0);
        assertEquals(testSched.getCourseNames().size(), 0);
        testSched.addCourse(classes, cStr1);
        assertEquals(testSched.getCourses().size(), 1);
        assertEquals(testSched.getCourseNames().size(), 1);
        testSched.addCourse(classes, cStr2);
        assertEquals(testSched.getCourses().size(), 2);
        assertEquals(testSched.getCourseNames().size(), 2);
        testSched.addCourse(classes, cStr4);
        assertEquals(testSched.getCourses().size(), 2);
        assertEquals(testSched.getCourseNames().size(), 2);
        testSched.addCourse(classes, cStr3);
        assertEquals(testSched.getCourses().size(), 3);
        assertEquals(testSched.getCourseNames().size(), 3);
        testSched.addCourse(classes, cStr5);
        assertEquals(testSched.getCourses().size(), 3);
        assertEquals(testSched.getCourseNames().size(), 3);
    }

    // - - - - - - - - - - - - - - - - - - - -
    //       - removeCourse() tests -
    // - - - - - - - - - - - - - - - - - - - -
    // 1. Does not remove from empty schedule
    @Test
    public void removeFromEmptyScheduleShouldNotRemove() {
        Schedule testSched = new Schedule();
        String cStr1 = "CPE357-01: Systems Programming\nBuilding: Graphic Arts - 26" +
                "\nRoom: 0102\nTime: 2:10 PM to 4:00 PM on MWF";

        testSched.removeCourse(cStr1);
        assertEquals(testSched.getCourses().size(), 0);
        assertEquals(testSched.getCourseNames().size(), 0);
    }

    // 2. Removes correctly when present
    @Test
    public void removePresentCourseShouldRemove() {
        Schedule testSched = new Schedule();
        String cStr1 = "AG102-24: Crop Production II\nBuilding: Agricultural Science - 11" +
                "\nRoom: 0119\nTime: 7:10 AM to 9:00 AM on MWF";
        String cStr2 = "CHEM233-01: Small Scale Interactions\nBuilding: Baker Science - 180" +
                "\nRoom: 0504\nTime: 11:10 AM to 2:00 PM on MW";

        testSched.addCourse(classes, cStr1);
        testSched.addCourse(classes, cStr2);
        assertEquals(testSched.getCourses().size(), 2);
        assertEquals(testSched.getCourseNames().size(), 2);
        assertEquals(testSched.getCourseNames().get(0), cStr1);

        testSched.removeCourse(cStr1);
        assertEquals(testSched.getCourses().size(), 1);
        assertEquals(testSched.getCourseNames().size(), 1);
        assertEquals(testSched.getCourseNames().get(0), cStr2);

        testSched.removeCourse(cStr2);
        assertEquals(testSched.getCourses().size(), 0);
        assertEquals(testSched.getCourseNames().size(), 0);
    }

    // 3. Does not remove if not present
    @Test
    public void removeCourseNotPresentShouldNotRemove() {
        Schedule testSched = new Schedule();
        String cStr1 = "CPE357-01: Systems Programming\nBuilding: Graphic Arts - 26" +
                "\nRoom: 0102\nTime: 2:10 PM to 4:00 PM on MWF";
        String cStr2 = "ART145-06: Ceramics I\nBuilding: Walter F. Dexter Building - 34" +
                "\nRoom: 0221\nTime: 12:10 PM to 3:00 PM on TR";
        String cStr3 = "EE467-01: Life Engineering for Graduates\nBuilding: Engineering West - 21" +
                "\nRoom: 0306\nTime: 6:10 PM to 9:00 PM on MW";
        String cStr4 = "ART145-06: Ceramics I\nBuilding: Walter F. Dexter Building - 34" +
                "\nRoom: 0221\nTime: 12:10 PM to 3:00 PM on TR";

        testSched.addCourse(classes, cStr1);
        testSched.addCourse(classes, cStr2);
        assertEquals(testSched.getCourses().size(), 2);
        assertEquals(testSched.getCourseNames().size(), 2);
        assertEquals(testSched.getCourseNames().get(0), cStr2);

        testSched.removeCourse(cStr3);
        assertEquals(testSched.getCourses().size(), 2);
        assertEquals(testSched.getCourseNames().size(), 2);
        assertEquals(testSched.getCourseNames().get(0), cStr2);

        testSched.removeCourse(cStr2);
        assertEquals(testSched.getCourses().size(), 1);
        assertEquals(testSched.getCourseNames().size(), 1);
        assertEquals(testSched.getCourseNames().get(0), cStr1);

        testSched.removeCourse(cStr4);
        assertEquals(testSched.getCourses().size(), 1);
        assertEquals(testSched.getCourseNames().size(), 1);
        assertEquals(testSched.getCourseNames().get(0), cStr1);
    }


    // - - - - - - - - - - - - - - - - - - - -
    //       - deleteSchedule() tests -
    // - - - - - - - - - - - - - - - - - - - -
    // 1. Check all courses removed from schedule
    @Test
    public void deleteEmptyScheduleDoesNothing() {
        Schedule testSched = new Schedule();

        testSched.deleteSchedule();
        assertEquals(testSched.getCourses().size(), 0);
        assertEquals(testSched.getCourseNames().size(), 0);
    }

    @Test
    public void deleteScheduleShouldEmptySchedule() {
        Schedule testSched = new Schedule();
        String cStr1 = "CPE357-01: Systems Programming\nBuilding: Graphic Arts - 26" +
                "\nRoom: 0102\nTime: 2:10 PM to 4:00 PM on MWF";
        String cStr2 = "ART145-06: Ceramics I\nBuilding: Walter F. Dexter Building - 34" +
                "\nRoom: 0221\nTime: 12:10 PM to 3:00 PM on TR";
        String cStr3 = "AG102-24: Crop Production II\nBuilding: Agricultural Science - 11" +
                "\nRoom: 0119\nTime: 7:10 AM to 9:00 AM on MWF";
        String cStr4 = "CHEM233-01: Small Scale Interactions\nBuilding: Baker Science - 180" +
                "\nRoom: 0504\nTime: 11:10 AM to 2:00 PM on MW";
        String cStr5 = "EE467-01: Life Engineering for Graduates\nBuilding: Engineering West - 21" +
                "\nRoom: 0306\nTime: 6:10 PM to 9:00 PM on MW";

        testSched.addCourse(classes, cStr1);
        testSched.addCourse(classes, cStr2);
        testSched.addCourse(classes, cStr3);
        assertEquals(testSched.getCourses().size(), 3);
        assertEquals(testSched.getCourseNames().size(), 3);

        testSched.deleteSchedule();
        assertEquals(testSched.getCourses().size(), 0);
        assertEquals(testSched.getCourseNames().size(), 0);

        testSched.addCourse(classes, cStr1);
        testSched.addCourse(classes, cStr2);
        testSched.addCourse(classes, cStr3);
        testSched.addCourse(classes, cStr4);
        testSched.addCourse(classes, cStr5);
        assertEquals(testSched.getCourses().size(), 5);
        assertEquals(testSched.getCourseNames().size(), 5);

        testSched.deleteSchedule();
        assertEquals(testSched.getCourses().size(), 0);
        assertEquals(testSched.getCourseNames().size(), 0);
    }


    // - - - - - - - - - - - - - - - - - - - -
    //         - contains() tests -
    // - - - - - - - - - - - - - - - - - - - -
    // 1. Returns FALSE if schedule is empty
    @Test
    public void containsEmptyScheduleShouldReturnFalse() {
        Schedule testSched = new Schedule();
        String cStr1 = "CHEM233-01: Small Scale Interactions\nBuilding: Baker Science - 180" +
                "\nRoom: 0504\nTime: 11:10 AM to 2:00 PM on MW";

        assertFalse(testSched.contains(cStr1));
    }

    // 2. Returns TRUE if it contains class
    @Test
    public void containsPresentCourseShouldReturnTrue() {
        Schedule testSched = new Schedule();
        String cStr1 = "CPE357-01: Systems Programming\nBuilding: Graphic Arts - 26" +
                "\nRoom: 0102\nTime: 2:10 PM to 4:00 PM on MWF";
        String cStr2 = "ART145-06: Ceramics I\nBuilding: Walter F. Dexter Building - 34" +
                "\nRoom: 0221\nTime: 12:10 PM to 3:00 PM on TR";

        testSched.addCourse(classes, cStr1);
        assertTrue(testSched.contains(cStr1));

        testSched.addCourse(classes, cStr2);
        assertTrue(testSched.contains(cStr1));
        assertTrue(testSched.contains(cStr2));

    }

    // 3. Returns FALSE if it does not contain class
    @Test
    public void containsCourseNotPresentShouldReturnFalse() {
        Schedule testSched = new Schedule();
        String cStr1 = "AG102-24: Crop Production II\nBuilding: Agricultural Science - 11" +
                "\nRoom: 0119\nTime: 7:10 AM to 9:00 AM on MWF";
        String cStr2 = "CHEM233-01: Small Scale Interactions\nBuilding: Baker Science - 180" +
                "\nRoom: 0504\nTime: 11:10 AM to 2:00 PM on MW";
        String cStr3 = "EE467-01: Life Engineering for Graduates\nBuilding: Engineering West - 21" +
                "\nRoom: 0306\nTime: 6:10 PM to 9:00 PM on MW";

        testSched.addCourse(classes, cStr1);
        assertTrue(testSched.contains(cStr1));
        assertFalse(testSched.contains(cStr2));

        testSched.addCourse(classes, cStr2);
        assertTrue(testSched.contains(cStr1));
        assertTrue(testSched.contains(cStr2));
        assertFalse(testSched.contains(cStr3));

        testSched.deleteSchedule();
        assertFalse(testSched.contains(cStr1));
        assertFalse(testSched.contains(cStr2));
        assertFalse(testSched.contains(cStr3));
    }

}
