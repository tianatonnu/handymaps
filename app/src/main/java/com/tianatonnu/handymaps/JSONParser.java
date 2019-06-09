package com.tianatonnu.handymaps;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class JSONParser extends Application {

    private static Context context;

    private static int numberOfSections;
    private static int numberOfBuildings;
    private static int numberOfClassrooms;

    public void onCreate() {
        super.onCreate();
        JSONParser.context = getApplicationContext();
    }

    public static Context getAppContext(){
        return JSONParser.context;
    }

    public static Course[] getCourses(String sectionsJSONFile) {
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

        numberOfSections = 0;

        try {
            sections = new JSONObject(loadJSONFromAsset(sectionsJSONFile));
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

    public static String[] makeStrings(Course[] cpCourses)
    {
        //creating a String Array to be used in the ListView
        String[] cpCourses_arr = new String[cpCourses.length];

        //Populating the String Array with each Course Card
        for(int i = 0; i < numberOfSections; i++){
            cpCourses_arr[i] = cpCourses[i].createCard();
        }

        return cpCourses_arr;
    }

    public static Building[] getBuildings(String buildingsJSONFile) {
        Building[] cpBuildings = null;
        //String[] cpBuildings_arr = null;

        // Main JSON Object for buildings.json
        JSONObject buildingsJSON = null;

        //JSON Array containing multiple buildings
        JSONArray buildings = null;
        JSONObject building = null;

        //Objects for Location Parsing
        JSONObject buildingLocationJSON = null;
        JSONArray buildingLocationCoordinates = null;
        double[] buildingLocation = new double[2];

        //Objects for Building's information
        JSONObject buildingInfo = null;
        String buildingName = null;
        String buildingNumber = null;

        numberOfBuildings = 0;

        try {
            buildingsJSON = new JSONObject(loadJSONFromAsset(buildingsJSONFile));
            buildings = buildingsJSON.getJSONArray("features");

            //Get the number of buildings
            cpBuildings = new Building[buildings.length()];
            numberOfBuildings = buildings.length();

            for (int i = 0; i < numberOfBuildings; i++) {
                building = buildings.getJSONObject(i);

                //Parsing for Location
                buildingLocationJSON = building.getJSONObject("geometry");
                buildingLocationCoordinates = buildingLocationJSON.getJSONArray("coordinates");
                // Get Longitude
                buildingLocation[0] = buildingLocationCoordinates.getDouble(0);
                // Get Latitude
                buildingLocation[1] = buildingLocationCoordinates.getDouble(1);

                //Parsing for Building Information
                buildingInfo = building.getJSONObject("properties");
                buildingName = buildingInfo.getString("name");
                buildingNumber = buildingInfo.getString("ref_en");

                //Initialize Building Object with Information
                cpBuildings[i] = new Building(buildingLocation, buildingName, buildingNumber);
            }

            //Printing out each building card
          /*  for (Building b : cpBuildings) {
                Log.v("Building: ", b.createCard());
            }*/

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cpBuildings;
    }

    public static String[] makeStrings(Building[] cpBuildings)
    {
        //Creating a String Array to be used in the ListView
        String[] cpBuildings_arr = new String[cpBuildings.length];

        //Populating the String Array with each Building Card
        for(int i = 0; i < numberOfBuildings; i++){
            cpBuildings_arr[i] = cpBuildings[i].createCard();
        }

        return cpBuildings_arr;
    }

    public static Classroom[] getClassrooms(String classroomJSONFile) {
        Classroom[] cpClassrooms = null;
        //String[] cpClassrooms_arr = null;

        //Main JSON Object for classrooms.json
        JSONObject classroomsJSON = null;

        //JSON Array containing multiple classrooms
        JSONArray classrooms = null;
        JSONObject classroom = null;

        //Objects for Location Parsing
        JSONObject classroomLocationJSON = null;
        JSONArray classroomLocationCoordinates = null;
        double[] classroomLocation = new double[2];

        //Objects for Classroom's information
        JSONObject classroomInfo = null;
        String classroomBldgName = null;
        String classroomBldgNumber = null;
        String classroomNumber = null;

        numberOfClassrooms = 0;

        try {

            classroomsJSON = new JSONObject(loadJSONFromAsset(classroomJSONFile));
            classrooms = classroomsJSON.getJSONArray("features");

            //Get the number of classrooms
            cpClassrooms = new Classroom[classrooms.length()];
            numberOfClassrooms = classrooms.length();

            //Log.d("Number of classrooms is: ", String.valueOf(numberOfClassrooms));

            for (int i = 0; i < numberOfClassrooms; i++) {
                classroom = classrooms.getJSONObject(i);

                //Parsing for Location
                classroomLocationJSON = classroom.getJSONObject("geometry");
                classroomLocationCoordinates = classroomLocationJSON.getJSONArray("coordinates");
                // Get Longitude
                classroomLocation[0] = classroomLocationCoordinates.getDouble(0);
                // Get Latitude
                classroomLocation[1] = classroomLocationCoordinates.getDouble(1);

                //Parsing for Classroom Information
                classroomInfo = classroom.getJSONObject("properties");
                classroomBldgName = classroomInfo.getString("bldgName");
                classroomBldgNumber = classroomInfo.getString("bldg");
                classroomNumber = classroomInfo.getString("room");

                //Initialize Classroom Object with Information
                cpClassrooms[i] = new Classroom(classroomBldgName, classroomBldgNumber, classroomNumber, classroomLocation);
            }

            //Printing out each building card
         /*   for (Classroom c : cpClassrooms) {
                Log.v("Classroom: ", c.createCard());
            }*/
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cpClassrooms;
    }

    public static String[] makeStrings(Classroom[] cpClassrooms)
    {

        //Creating a String Array to be used in the ListView
        String[] cpClassrooms_arr = new String[cpClassrooms.length];

        //Populating the String Array with each Classroom Card
        for(int i = 0; i < numberOfClassrooms; i++){
            cpClassrooms_arr[i] = cpClassrooms[i].createCard();
        }
        return cpClassrooms_arr;
    }

    // Function to first get JSON data as a String from Asset folder
    private static String loadJSONFromAsset(String asset){
        String json = null;

        // Always use try and catch for parsing
        try{
            //Log.d("Phase", "Pre InputStream");
            InputStream is = JSONParser.getAppContext().getAssets().open(asset);
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