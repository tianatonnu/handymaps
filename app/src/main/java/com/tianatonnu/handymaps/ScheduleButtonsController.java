package com.tianatonnu.handymaps;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mapbox.geojson.Point;

import java.util.ArrayList;

public class ScheduleButtonsController {
    private ScheduleActivity scheduleActivity;

    // The various buttons for the schedule page
    private Button deleteBtn;
    private Button findBtn;
    private boolean showCourseBtns;

    private Schedule schedule;

    public ScheduleButtonsController(ScheduleActivity scheduleActivity, Schedule schedule)
    {
        this.scheduleActivity = scheduleActivity;
        deleteBtn = scheduleActivity.findViewById(R.id.schedule_delete_button);
        findBtn = scheduleActivity.findViewById(R.id.schedule_route_button);
        showCourseBtns = false;
        this.schedule = schedule;
    }

    public void setButtonListeners(ArrayList<Location> locations)
    {
        setDeleteBtnListener();
        setFindBtnListener(locations);
    }

    private void setDeleteBtnListener()
    {
        int typeDeleteCourse = 1;

        // Set on-click-listener for the delete button
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteDialog(typeDeleteCourse);
            }
        });
    }

    private void setFindBtnListener(ArrayList<Location> locations)
    {
        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseName = scheduleActivity.getPrevCourseName();
                Intent intent = new Intent(scheduleActivity, MainActivity.class);
                Point point = Search.findCoordinates(locations, courseName);
                DestinationPoint destinationPoint = new DestinationPoint(point.latitude(), point.longitude());
                intent.putExtra("classLocation", destinationPoint);
                scheduleActivity.startActivity(intent);
            }
        });
    }

    public void confirmDeleteDialog(int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(scheduleActivity);

        // If type == 0, use confirm delete dialog for deleting entire schedule
        if (type == 0) {
            builder.setTitle("Confirm Delete Schedule");
            builder.setMessage("You are about to delete all classes from your schedule. Do you wish to proceed?");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    schedule.deleteSchedule();
                    scheduleActivity.setScheduleAdapter();
                    disableCourseButtons();

                    scheduleActivity.saveSchedule();
                    Toast.makeText(scheduleActivity.getApplicationContext(), "Schedule deleted", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(scheduleActivity.getApplicationContext(), "Schedule not deleted", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Else, use confirm delete dialog for deleting a single course
        else {
            String courseName = scheduleActivity.getPrevCourseName();
            String courseIdName = courseName.substring(0, courseName.indexOf(":"));
            builder.setTitle("Confirm Delete Course");
            builder.setMessage(String.format("You are about to delete %s from your schedule. Do you wish to proceed?", courseIdName));
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    schedule.removeCourse(courseName);
                    // Update the schedule listview
                    scheduleActivity.setScheduleAdapter();
                    disableCourseButtons();
                    scheduleActivity.saveSchedule();
                    Toast.makeText(scheduleActivity.getApplicationContext(), "Course removed from schedule", Toast.LENGTH_SHORT).show();
                    scheduleActivity.resetSelectedCourse();
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(scheduleActivity.getApplicationContext(), "Course not removed from schedule", Toast.LENGTH_SHORT).show();
                }
            });
        }

        builder.show();
    }


    public void enableButtonsConditionally()
    {
        if (showCourseBtns)
            showCourseButtons();
    }

    public void disableCourseButtons()
    {
        hideCourseButtons();
        showCourseBtns = false;
    }


    public void hideAllButtons()
    {
        hideCourseButtons();
    }

    // Disable remove and find buttons
    public void hideCourseButtons()
    {
        deleteBtn.setEnabled(false);
        deleteBtn.setVisibility(View.INVISIBLE);
        findBtn.setEnabled(false);
        findBtn.setVisibility(View.INVISIBLE);
    }

    // Enable remove and find button
    public void showCourseButtons()
    {
        deleteBtn.setEnabled(true);
        deleteBtn.setVisibility(View.VISIBLE);
        findBtn.setEnabled(true);
        findBtn.setVisibility(View.VISIBLE);
        showCourseBtns = true;
    }

}
