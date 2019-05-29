package com.tianatonnu.handymaps;

import java.util.ArrayList;

public class Schedule
{
    private ArrayList<Course> courses;
    private ArrayList<String> courseStrings;

    public Schedule()
    {
        this.courses = new ArrayList<>();
        this.courseStrings = new ArrayList<>();
    }


    public Schedule(ArrayList<Course> courses, ArrayList<String> courseStrings)
    {
        this.courses = courses;
        this.courseStrings = courseStrings;
    }

    public ArrayList<Course> getCourses()
    {
        return courses;
    }

    public ArrayList<String> getCourseNames()
    {
        return courseStrings;
    }

    public void addCourse(Course course)
    {
        courses.add(course);
        courseStrings.add(course.createCard());
    }

    public void removeCourse(String course)
    {
        for (Course c:courses)
        {
            if (c.createCard().equals(course))
            {
                courses.remove(c);
                courseStrings.remove(course);
                return;
            }
        }
    }

    public boolean contains(String courseName)
    {
        return courseStrings.contains(courseName);
    }


}
