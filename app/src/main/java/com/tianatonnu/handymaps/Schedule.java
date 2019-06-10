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

    //public void addCourse(Course course)
    public boolean addCourse(Course[] classes, String courseName)
    {
        Course course;

        // Only want to add the course if it is already not in the schedule
        if (!courseStrings.contains(courseName))
        {
            course = Search.findCourse(classes, courseName);
            courses.add(course);
            courses.sort(new ScheduleCourseComparator());
            courseStrings.add(course.createCard());
            courseStrings.sort(new ScheduleStringComparator());

            return true;
        }

        return false;
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

    public void deleteSchedule()
    {
        courses.clear();
        courseStrings.clear();
    }

    public boolean contains(String courseName)
    {
        return courseStrings.contains(courseName);
    }


}
