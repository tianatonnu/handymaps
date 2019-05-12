package com.example.jsonparsertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    String[] cpCourse_arr;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Main JSON Object for sections.json

        Course[] cpCourses;
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
            courses = sections.getJSONArray("features");

            // Get the number of courses
            Log.d("Number of Courses", String.valueOf(courses.length()));
            cpCourses = new Course[courses.length()];
            numberOfSections = courses.length();

            // Loop through the JSON Array to get each Course Info
            for(int x = 0; x < numberOfSections; x ++) {
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

                cpCourses[x] = new Course(courseDept,courseNumber,courseSectionNumber,courseName,courseBldg,courseRoom,courseLocation,rawCourseTime);
            }

            //Printing out each course Card
            for(Course c: cpCourses){
                Log.v("Course: ",c.createCard());
            }
            Log.v("Size Of Course Array", String.valueOf(cpCourses.length));

            //creating a String Array to be used in the ListView
            cpCourse_arr = new String[cpCourses.length];

            //Populating the String Array with each Course Card
            for(int i = 0; i < cpCourses.length; i++){
                cpCourse_arr[i] = cpCourses[i].createCard();
            }

            //Setting the ListView object with the ListView element in activity_main.xml
            lv =(ListView) findViewById(R.id.listView);

            //Add the values from the String Array to the layout type
            ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cpCourse_arr);
            lv.setAdapter(adapter);

            Log.v("Success: ", "Completed list adapter");
        } catch (JSONException e ){
            Log.v("Error: ", "Unable to load information");
        }
    }

    // Function to first get JSON data as a String from Asset folder
    public String loadJSONFromAsset(String asset){
        String json = null;

        // Always use try and catch for parsing
        try{
            InputStream is = getAssets().open(asset);
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
