package com.tianatonnu.handymaps;

import java.util.Comparator;

public class ScheduleCourseComparator implements Comparator<Course>{

    public int compare(Course c1, Course c2)
    {
        return c1.compareTo(c2);
    }

}