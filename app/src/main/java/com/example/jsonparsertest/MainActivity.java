package com.example.jsonparsertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        try {
            sections = new JSONObject(loadJSONFromAsset());
            courses = sections.getJSONArray("features");

            // Get the number of courses
            Log.d("Size of Sections: ", String.valueOf(courses.length()));

            // Get the first course in the array
            course = courses.getJSONObject(0);

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

            // Building Course String
            String message = "\n" + courseDept;
            message += " " + courseNumber + "-" + courseSectionNumber + "\n";
            message += courseName + "\n";
            message += courseBldg + " " + courseRoom + "\n";
            message += rawCourseTime;

            Log.d("Course Information", message);

        } catch (JSONException e ){
            Log.d("Error: ", "Unable to load information");
        }
    }

    // Function to first get JSON data as a String from Asset folder
    public String loadJSONFromAsset(){
        String json = null;

        // Always use try and catch for parsing
        try{
            InputStream is = getAssets().open("sections.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
}
