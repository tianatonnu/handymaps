package com.tianatonnu.handymaps;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ScheduleIntegrationTest {

    Course course01 = new Course(
            "ES",
            " 350",
            "71",
            "Gender, Race, Culture, Science and Technology",
            "Math & Science - 38",
            "0218",
            new double[]{-120.66199690103531,35.30118890411341},
            "5:40 PM to 6:00 PM on MW");

    //Different course name then course 01
    Course course02 = new Course(
            "AEPS",
            " 313",
            "03",
            "Agricultural Entomology",
            "Agricultural Sciences - 11",
            "0118",
            new double[]{-120.66199690103531,35.30118890411341},
            "3:10 PM to 6:00 PM on M");

    // Same Dept and Course Number as course04, but different section
    Course course03 = new Course(
            "ES",
            " 241",
            "02",
            "Survey of Indigenous Studies",
            "Cochett Education Bldg - 02",
            "0204",
            new double[]{-120.66199690103531,35.30118890411341},
            "2:10 PM to 4:00 PM on TR");

    //Department Similar to course01, but different course number
    Course course04 = new Course(
            "ES",
            " 241",
            "01",
            "Survey of Indigenous Studies",
            "Cochett Education Bldg - 02",
            "0205",
            new double[]{-120.66199690103531,35.30118890411341},
            "12:10 PM to 2:00 PM on TR");


    Course course05 = new Course(
            "PHYS",
            " 423",
            "01",
            "Advanced Optics",
            "Science North - 53",
            "0201",
            new double[]{-120.66199690103531,35.30118890411341},
            "10:10 AM to 11:00 AM on MWF");



    /* Will test the Search, JSONParser code, and Course classes
       Test will make a schedule by using Search class to find Course to add from database
       After Courses have been added check they are contained in the Schedule ArrayLists
     */
    @Test
    public void scheduleCreationTest01(){
        Course[] classes = getCourses();
        Schedule tempSchedule = new Schedule();

        ArrayList<String> courses = new ArrayList<String>(
                Arrays.asList(course01.createCard(),course02.createCard(),
                        course03.createCard(),course04.createCard()));


        // Add all the courses to the schedule;
        for(String c: courses){
            tempSchedule.addCourse(classes,c);
        }

        //Check all the courses in the courses String[] are in the schedule
        for(String c: courses){
            assertTrue(tempSchedule.contains(c));
        }

        // Remove course from the schedule
        tempSchedule.removeCourse(course01.createCard());

        // Remove course from local course list
        courses.remove(course01.createCard());

        //Check all the remaining courses are still there
        for(String c: courses){
            Log.d("SITest Removal Phase: ", c);
            assertTrue(tempSchedule.contains(c));
        }
    }

    /* Will test the Search, JSONParser code, and Course classes
          Test will that the schedule removed exact course in the schedule only.
        */
    @Test
    public void scheduleCreationTest02(){
        Course[] classes = getCourses();
        Schedule tempSchedule = new Schedule();

        ArrayList<String> courses = new ArrayList<String>(
                Arrays.asList(course01.createCard(),course02.createCard(),
                        course03.createCard(),course04.createCard()));


        // Add all the courses to the schedule;
        for(String c: courses){
            tempSchedule.addCourse(classes,c);
        }

        //Check all the courses in the courses String[] are in the schedule
        for(String c: courses){
            assertTrue(tempSchedule.contains(c));
        }

        // Remove course from the schedule
        tempSchedule.removeCourse(course01.createCard());
        tempSchedule.removeCourse(course03.createCard());

        // Remove course from local course list
        courses.remove(course01.createCard());
        courses.remove(course03.createCard());

        //Check all the remaining courses are still there
        for(String c: courses){
            Log.d("SITest Removal Phase: ", c);
            assertTrue(tempSchedule.contains(c));
        }
    }

    /* Will test the Search, JSONParser code, and Course classes
          Test will that the schedule removed exact course in the schedule only,
          And original courses are no longer in the schedule
     */
    @Test
    public void scheduleCreationTest03(){
        Course[] classes = getCourses();
        Schedule tempSchedule = new Schedule();

        ArrayList<String> courses = new ArrayList<String>(
                Arrays.asList(course01.createCard(),course02.createCard(),
                        course03.createCard(),course04.createCard()));


        // Add all the courses to the schedule;
        for(String c: courses){
            tempSchedule.addCourse(classes,c);
        }

        //Check all the courses in the courses String[] are in the schedule
        for(String c: courses){
            assertTrue(tempSchedule.contains(c));
        }

        // Remove courses from the schedule
        tempSchedule.removeCourse(course01.createCard());
        tempSchedule.removeCourse(course03.createCard());

        courses.remove(course01.createCard());
        courses.remove(course03.createCard());

        for(String c: courses){
            Log.d("SITest Removal Phase: ", c);
            assertTrue(tempSchedule.contains(c));
        }

        assertFalse(tempSchedule.contains(course05.createCard()));
    }

    /* Will Test ScheduleCourse and ScheduleString Comparators Classes
       Test will create a new schedule and validate the sorting is correct based on the comparator
       classes
     */
    @Test
    public void scheduleComparatorTest01(){
        Course[] classes = getCourses();
        Schedule tempSchedule = new Schedule();

        ArrayList<String> courses = new ArrayList<String>(
                Arrays.asList(course01.createCard(),course02.createCard(),
                        course03.createCard(),course04.createCard(),course05.createCard()));


        // Add all the courses to the schedule;
        for(String c: courses){
            tempSchedule.addCourse(classes,c);
        }

        //Check all the courses in the courses String[] are in the schedule
        for(String c: courses){
            assertTrue(tempSchedule.contains(c));
        }

        //Now check that the added courses are sorted
        // Should be in this order:
        // course02, course04, course03, course01, course05
        ArrayList<Course> sortedClasses = new ArrayList<Course>(
                Arrays.asList(course02,course04,course03, course01, course05));
        ArrayList<Course> scheduleClasses = tempSchedule.getCourses();

        ArrayList<String> schedule = tempSchedule.getCourseNames();
        ArrayList<String> sortedCourses = new ArrayList<String>(
                Arrays.asList(course02.createCard(),course04.createCard(),
                course03.createCard(),course01.createCard(),course05.createCard()));

        assertTrue(schedule.equals(sortedCourses));

        for(int i = 0; i < scheduleClasses.size(); i++){
            assertTrue(scheduleClasses.get(i).compareTo(sortedClasses.get(i))==0);
        }
    }


    public Course[] getCourses() {
        Course[] cpCourses = null;
        //String[] cpCourses_arr = null;

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
            Log.d("Phase: ", "Pre JSON Object");
            String json = loadJSONFromAsset();
            sections = new JSONObject(json);
            Log.d("Phase: ","Post JSON Object");
            courses = sections.getJSONArray("features");

            // Get the number of courses
            cpCourses = new Course[courses.length()];
            numberOfSections = courses.length();
            Log.d("Section Length:", String.valueOf(numberOfSections));


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
            for(Course c: cpCourses){
                Log.v("Course: ",c.createCard());
            }

            Log.v("Size Of Course Array", String.valueOf(cpCourses.length));

        } catch (JSONException e) {
            e.printStackTrace();
            Log.v("Error: ", "Unable to load information");
        }

        return cpCourses;
    }

    private String loadJSONFromAsset(){
        String json = null;

        // Always use try and catch for parsing
        try{
            Log.d("Phase", "Pre InputStream");
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("sections.json");
            Log.d("Phase", "Post InputStream");
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(reader);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            br.close();
            json = sb.toString();

        } catch (IOException e) {
            Log.d("Error: ", "Could not load JSON");
            return null;
        }
        Log.d("Phase", "Pre JSON");
        Log.v("JSON String", json);

        return json;
    }
}
