package com.example.jsonparsertest;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class JSONParser extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        JSONParser.context = getApplicationContext();
    }

    public static Context getAppContext(){
        return JSONParser.context;
    }

    public static String[] getCourses() {
        // Main JSON Object for sections.json

        Course[] cpCourses = null;
        String[] cpCourses_arr = null;

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

        int numberOfSections;

        try {
            sections = new JSONObject(loadJSONFromAsset("sections.json"));
            Log.d("Phase", "Post JSON");
            courses = sections.getJSONArray("features");

            // Get the number of courses
            Log.d("Number of Courses", String.valueOf(courses.length()));
            cpCourses = new Course[courses.length()];
            numberOfSections = courses.length();

            Log.d("Phase", "Pre Course Array Population ");
            // Loop through the JSON Array to get each Course Info
            for (int x = 0; x < numberOfSections; x++) {
                course = courses.getJSONObject(x);
                courseLocationJSON = course.getJSONObject("geometry");
                courseLocationCoordinates = courseLocationJSON.getJSONArray("coordinates");

                courseLocation[0] = courseLocationCoordinates.getDouble(0);
                courseLocation[1] = courseLocationCoordinates.getDouble(1);

                courseInfo = course.getJSONObject("properties");
                courseDept = courseInfo.getString("department");
                courseNumber = courseInfo.getString("courseNumber");
                courseName = courseInfo.getString("courseName");
                courseBldg = courseInfo.getString("bldgName");
                courseRoom = courseInfo.getString("room");
                rawCourseTime = courseInfo.getString("time");
                courseSectionNumber = courseInfo.getString("sectionNumber");

                cpCourses[x] = new Course(courseDept, courseNumber, courseSectionNumber, courseName, courseBldg, courseRoom, courseLocation, rawCourseTime);
            }
            Log.d("Phase", "Post Course Array Population");
            //Printing out each course Card
          /*  for(Course c: cpCourses){
                Log.v("Course: ",c.createCard());
            }*/

            Log.v("Size Of Course Array", String.valueOf(cpCourses.length));

        }  catch (JSONException e ) {
            Log.v("Error: ", "Unable to load information");
        }

        //creating a String Array to be used in the ListView
        cpCourses_arr = new String[cpCourses.length];

        //Populating the String Array with each Course Card
        for(int i = 0; i < cpCourses.length; i++){
            cpCourses_arr[i] = cpCourses[i].createCard();
        }

        return cpCourses_arr;
    }


    // Function to first get JSON data as a String from Asset folder
    private static String loadJSONFromAsset(String asset){
        String json = null;

        // Always use try and catch for parsing
        try{
            Log.d("Phase", "Pre InputStream");
            InputStream is = JSONParser.getAppContext().getAssets().open(asset);
            Log.d("Phase", "Post InputStream");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Log.d("Phase", "Pre JSON");
        return json;
    }
}
