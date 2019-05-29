package com.tianatonnu.handymaps;

import com.mapbox.geojson.Point;

import java.util.ArrayList;

public class Search {
    public Search(){}

    // Make filter more robust?
    public static ArrayList<String> filter(String newText, ArrayList<String> allData)
    {
        ArrayList<String> lstFound = new ArrayList<>();

        // Split search into separate words (key words)
        String[] keyWords = newText.split(" ");
        for (String item:allData)
        {
            boolean valid = true;
            for (String word:keyWords)
            {
                // item does not contain the key word
                if (!item.toLowerCase().contains(word.toLowerCase()))
                {
                    valid = false;
                    break;
                }
            }
            // Item contains all key words
            if (valid)
            {
                lstFound.add(item);
            }
        }

        return lstFound;
    }

    public static Point findCoordinates(Building[] buildings, Course[] courses, Classroom[] classRooms, String card)
    {
        Point point = null;

        // Search buildings
        for (Building building:buildings)
        {
            if (building.createCard().equals(card))
            {
                point = com.mapbox.geojson.Point.fromLngLat(building.getBuildingLong(), building.getBuildingLat());
                break;
            }
        }

        // Search courses
        for (Course course:courses)
        {
            // Location was found, do not need to search further
            if (point != null)
                return point;

            // Need to still search for location
            if (course.createCard().equals(card))
            {
                point = com.mapbox.geojson.Point.fromLngLat(course.getCourseLong(), course.getCourseLat());
                break;
            }
        }

        // Search classRooms
        for (Classroom classroom:classRooms)
        {
            // Location was found, do not need to search further
            if (point != null)
                return point;

            // Need to still search for location
            if (classroom.createCard().equals(card))
            {
                point = com.mapbox.geojson.Point.fromLngLat(classroom.getClassLong(), classroom.getClassLat());
                break;
            }
        }

        return point;
    }

    public static Course findCourse(Course[] courses, String name)
    {
        Course course = null;
        for (Course c:courses)
        {
            if (c.createCard().equals(name))
            {
                course = c;
                break;
            }
        }

        return course;
    }
}
