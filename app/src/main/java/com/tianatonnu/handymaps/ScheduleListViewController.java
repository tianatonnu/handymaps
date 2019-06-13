package com.tianatonnu.handymaps;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ScheduleListViewController {
    private ScheduleActivity scheduleActivity;
    private ListView searchListView;
    private Schedule schedule;
    private ScheduleButtonsController buttonsController;
    private ScheduleSearchViewController searchViewController;
    private ListView scheduleListView;

    public ScheduleListViewController(ScheduleActivity scheduleActivity, ScheduleButtonsController buttonsController,
                                      Schedule schedule, ListView searchListView, ScheduleSearchViewController searchViewController,
                                      ListView scheduleListView)
    {
        this.scheduleActivity = scheduleActivity;
        this.searchListView = searchListView;
        this.schedule = schedule;
        this.buttonsController = buttonsController;
        this.searchViewController = searchViewController;
        this.scheduleListView = scheduleListView;
    }
    public void setListViewListeners(Course[] courses)
    {
        setSearchListViewListener(courses);
        setScheduleListViewListener();
    }

    private void setSearchListViewListener(Course[] courses)
    {
        // Adding a selected class to the schedule
        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String courseName = scheduleActivity.getSearchItemString(position);
                Log.d("Schedule", courseName);

                boolean newCourse = schedule.addCourse(courses, courseName);;

                // Update the schedule adapter to show the new course
                scheduleActivity.setScheduleAdapter();

                buttonsController.disableCourseButtons();
                scheduleActivity.resetSelectedCourse();

                if (newCourse)
                {
                    scheduleActivity.saveSchedule();
                    Toast toast = Toast.makeText(scheduleActivity, "Course added to schedule", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    Toast toast = Toast.makeText(scheduleActivity, "Course already in schedule", Toast.LENGTH_SHORT);
                    toast.show();
                }

                searchViewController.closeSearchBar();
            }
        });
    }

    private void setScheduleListViewListener()
    {
        // Deals with selecting a course from the schedule listView
        scheduleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (scheduleActivity.getPrevView() != null)
                {
                    // Set previously selected course back to original background
                    scheduleActivity.setPrevViewBackgroundToNormal();

                    // Unselect the course
                    if (scheduleActivity.getPrevView().equals(view))
                    {
                        scheduleActivity.resetSelectedCourse();
                        buttonsController.hideCourseButtons();

                        return;
                    }
                }

                if (searchViewController.getFocus())
                {
                    searchViewController.removeFocus();
                }

                scheduleActivity.setSelectedCourse(view, scheduleActivity.getScheduleItemString(position));
                view.setBackgroundColor(scheduleActivity.getResources().getColor(R.color.mapboxWhite));
                buttonsController.showCourseButtons();
            }
        });
    }
}
