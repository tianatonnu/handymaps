package com.tianatonnu.handymaps;

import com.mapbox.geojson.Point;

import java.util.ArrayList;

public class Search {

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

    public static Point findCoordinates(ArrayList<Location> locations, String name)
    {
        Point point = null;

        // Search all locations for requested location
        for (Location location:locations)
        {
            if (location.createCard().equals(name))
            {
                point = com.mapbox.geojson.Point.fromLngLat(location.getLongitude(), location.getLatitude());
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
