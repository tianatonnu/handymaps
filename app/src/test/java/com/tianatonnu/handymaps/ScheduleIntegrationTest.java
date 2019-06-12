package com.tianatonnu.handymaps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import static org.junit.Assert.*;

public class ScheduleIntegrationTest {

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
    // 1 Adds classes correctly
    @Test
    public void scheduleCreationTest(){
        Course[] classes = getCourses();
        Schedule tempSchedule = new Schedule();
        String cStr1 = "CPE 101-01: Fundamentals of Computer Science\nBuilding: Const " +
                "Innovations Center - 186\nRoom: C102\nTime: 8:10 AM to 9:00 AM on MWF";

        tempSchedule.addCourse(classes, cStr1);
        assertTrue(tempSchedule.contains(cStr1));
    }

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


    // - - - - - - - - - - - - - - - - - - - -
    //     - Schedule comparator tests -
    // - - - - - - - - - - - - - - - - - - - -



    // - - - - - - - - - - - - - - - - - - - -
    //         - JSONParser tests -
    // - - - - - - - - - - - - - - - - - - - -

    public Course[] getCourses() {
        Course[] cpCourses = null;

        // Main JSON Object for sections.json
        JSONObject sections = null;

        //JSON Array containing multiple courses
        JSONArray courses = null;
        JSONObject course = null;

        // Objects for Location Parsing
        JSONObject courseLocationJSON = null;
        JSONArray courseLocationCoordinates = null;
        // lat & long
        double[] courseLocation = new double[2];

        // Objects for a Course's information
        JSONObject courseInfo = null;
        // Individual information objects
        String courseDept = null;
        String courseNumber = null;
        String courseName = null;
        String courseBldg = null;
        String courseRoom = null;
        String rawCourseTime = null;
        String courseSectionNumber = null;

        int numberOfSections = 0;

        try {
            sections = new JSONObject(loadJSONFromAsset());
            courses = sections.getJSONArray("features");

            // Get the number of courses
            cpCourses = new Course[courses.length()];
            numberOfSections = courses.length();


            // Loop through the JSON Array to get each Course Info
            for (int i = 0; i < numberOfSections; i++) {
                course = courses.getJSONObject(i);

                //Parsing for Location
                courseLocationJSON = course.getJSONObject("geometry");
                courseLocationCoordinates = courseLocationJSON.getJSONArray("coordinates");
                // Get Longitude
                courseLocation[0] = courseLocationCoordinates.getDouble(0);
                //Get Latitude
                courseLocation[1] = courseLocationCoordinates.getDouble(1);

                //Parsing for Course Information
                courseInfo = course.getJSONObject("properties");
                courseDept = courseInfo.getString("department");
                courseNumber = courseInfo.getString("courseNumber");
                courseName = courseInfo.getString("courseName");
                courseBldg = courseInfo.getString("bldgName");
                courseRoom = courseInfo.getString("room");
                rawCourseTime = courseInfo.getString("time");
                courseSectionNumber = courseInfo.getString("sectionNumber");

                //Initialize Classroom Object with Information
                cpCourses[i] = new Course(courseDept, courseNumber, courseSectionNumber, courseName, courseBldg, courseRoom, courseLocation, rawCourseTime);
            }

            //Printing out each course Card
            /*  for(Course c: cpCourses){
                Log.v("Course: ",c.createCard());
            }*/

            //Log.v("Size Of Course Array", String.valueOf(cpCourses.length));

        } catch (JSONException e) {
            e.printStackTrace();
            //Log.v("Error: ", "Unable to load information");
        }

        return cpCourses;
    }

    private String loadJSONFromAsset(){
        String json = null;

        // Always use try and catch for parsing
        try{
            //Log.d("Phase", "Pre InputStream");
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("sections.json");
            //Log.d("Phase", "Post InputStream");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        //Log.d("Phase", "Pre JSON");
        return json;
    }
}

