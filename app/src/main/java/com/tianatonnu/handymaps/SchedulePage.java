package com.tianatonnu.handymaps;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mapbox.geojson.Point;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class SchedulePage extends AppCompatActivity {

    // For displaying the search bar and serach results
    private ArrayAdapter<String> searchAdapter;
    private ListView searchListView;
    private SearchView searchView;

    // For displaying the schedule
    private ArrayAdapter<String> scheduleAdapter;
    private ListView scheduleListView;

    // Hold the all course data
    private Course[] courses;
    private String[] courseStrings;
    private ArrayList<String> listViewData = new ArrayList<>();
    private ArrayList<Location> locations = new ArrayList<>();

    // Used for displaying and holding the schedule
    private Schedule schedule;
    private int prevCourseCard = -1;
    private View prevView = null;
    private String prevCourseName = null;

    // The various buttons for the schedule page
    private Button deleteBtn;
    private Button findBtn;
    private Button saveBtn;
    private boolean showSave = false;
    private boolean showBtns = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_page);

        // Set up tool bar as the action bar menu
        Toolbar toolBar = (Toolbar) findViewById(R.id.schedule_toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Adds back button to get back to map
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        deleteBtn = findViewById(R.id.schedule_delete_button);
        findBtn = findViewById(R.id.schedule_route_button);
        saveBtn = findViewById(R.id.schedule_save_button);

        // Parse courses from JSON data
        courses = JSONParser.getCourses();
        courseStrings = JSONParser.makeStrings(courses);
        Arrays.sort(courseStrings);
        Collections.addAll(listViewData, courseStrings);
        Collections.addAll(locations, courses);

        // Load in the schedule
        scheduleListView = findViewById(R.id.course_lv);
        loadSchedule();

        // Set the schedule list view to display the courses
        scheduleListView.setVisibility(View.VISIBLE);
        setScheduleAdapter();

        // Set up search bar
        searchView = findViewById(R.id.schedule_search);
        searchView.setQueryHint(getResources().getString(R.string.schedule_hint));
        exitSearchBar();
        searchListView = findViewById(R.id.schedule_search_lv);

        // Set close listener for search bar
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                closeSearchBar();
                return true;
            }
        });

        // Set text listener for search bar to enable search filtering and suggestions
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Display results
                searchListView.setVisibility(View.VISIBLE);

                // Disable buttons
                hideButtons();
                hideSave();

                //adapter.getFilter().filter(newText);
                if (newText != null && !newText.isEmpty())
                {
                    // Filter search
                    ArrayList<String> lstFound = Search.filter(newText, listViewData);

                    // Return the filtered results
                    setSearchAdapter(lstFound);
                }

                else
                {
                    // If no search, return all options
                    setSearchAdapter(courseStrings);
                }

                return false;
            }
        });

        // Adding a selected class to the schedule
        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String courseName = searchAdapter.getItem(position);
                Log.d("Schedule", courseName);

                if (!schedule.contains(courseName))
                    schedule.addCourse(Search.findCourse(courses, courseName));

                // Update the schedule adapter to show the new course
                setScheduleAdapter();

                hideButtons();
                prevCourseCard = -1;
                prevCourseName = null;
                prevView = null;

                showSaveBtn();

                closeSearchBar();
            }
        });

        // Deals with selecting a course from the schedule listView
        scheduleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //if (prevCourseCard != -1)
                if (prevView != null)
                {
                    // Set previously selected course back to original background
                    prevView.setBackgroundColor(getResources().getColor(R.color.activityBackground));
                }

                prevView = view;
                prevCourseCard = position;
                prevCourseName = scheduleAdapter.getItem(position);
                Log.d("Selecting", "Expected Card: " + prevCourseName);
                view.setBackgroundColor(getResources().getColor(R.color.mapboxWhite));

                showButtons();
            }
        });

        // Set on-click-listener for the delete button
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBtns = false;
                hideButtons();
                schedule.removeCourse(prevCourseName);
                // Update the schedule listview
                setScheduleAdapter();
                showSaveBtn();

                prevCourseCard = -1;
                prevCourseName = null;
                prevView = null;
            }
        });

        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SchedulePage.this, MainActivity.class);
                Point point = Search.findCoordinates(locations, prevCourseName);
                DestinationPoint destinationPoint = new DestinationPoint(point.latitude(), point.longitude());
                intent.putExtra("classLocation", destinationPoint);
                startActivity(intent);
            }
        });

        // Set on-click-listener for the save button
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSave = false;
                hideSave();
                saveSchedule();
            }
        });
    }

    // Used to load the buttons in the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Loads the items in the toolbar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.schedule_menu, menu);

        return true;
    }

    // What to do for the different options in the toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Return to map
                onBackPressed();
                return true;
            case R.id.delete:
                // Delete entire schedule
                schedule.deleteSchedule();
                setScheduleAdapter();
                hideButtons();
                showSaveBtn();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }

    // Keep search bar expanded, but remove the focus
    private void exitSearchBar()
    {
        searchView.setIconified(false);
        searchView.setFocusable(false);
        searchView.clearFocus();
    }

    // Hide search suggestions
    private void closeSearchBar()
    {
        exitSearchBar();

        // Re-enable buttons
        if (showBtns)
            showButtons();
        if (showSave)
            showSaveBtn();

        findViewById(R.id.schedule_search_lv).setVisibility(View.INVISIBLE);
    }

    // Disable remove and find buttons
    private void hideButtons()
    {
        deleteBtn.setEnabled(false);
        deleteBtn.setVisibility(View.INVISIBLE);
        findBtn.setEnabled(false);
        findBtn.setVisibility(View.INVISIBLE);
    }

    // Disable save button
    public void hideSave()
    {
        saveBtn.setEnabled(false);
        saveBtn.setVisibility(View.INVISIBLE);
    }

    // Enable save button
    public void showSaveBtn()
    {
        saveBtn.setEnabled(true);
        saveBtn.setVisibility(View.VISIBLE);
        showSave = true;
    }

    // Enable remove and find button
    private void showButtons()
    {
        deleteBtn.setEnabled(true);
        deleteBtn.setVisibility(View.VISIBLE);
        findBtn.setEnabled(true);
        findBtn.setVisibility(View.VISIBLE);
        showBtns = true;
    }

    // Save the changes made to the schedule to the device
    private void saveSchedule() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        // Convert schedule to JSON equivalent
        String json = gson.toJson(schedule);

        Log.d("file", json);

        // Commit the json to be saved
        editor.putString("schedule", json);
        editor.commit();

        // Quick pop-up message on the bottom of the screen to indicate changes were saved
        Toast toast = Toast.makeText(this, "Schedule Saved", Toast.LENGTH_SHORT);
        toast.show();
    }

    // Load a previously saved schdeule, or make a new one if there is no saved schedule
    private void loadSchedule() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("schedule", null);

        // There is a previously saved schedule
        if(json != null)
        {
            schedule = gson.fromJson(json, Schedule.class);
            // Set the adapter for the schedule listview
            setScheduleAdapter();
        }
        // There is no previously saved schedule
        else
        {
            schedule = new Schedule();
            // Set the adapter for the schedule listview
            setScheduleAdapter();
        }
    }

    // Set adapter for search bar listview
    private void setSearchAdapter(String[] results)
    {
        searchAdapter = new ArrayAdapter<>(
                SchedulePage.this,
                android.R.layout.simple_list_item_1,
                results);
        searchListView.setAdapter(searchAdapter);
    }

    // Set adapter for search bar listview
    private void setSearchAdapter(ArrayList<String> results)
    {
        searchAdapter = new ArrayAdapter<>(
                SchedulePage.this,
                android.R.layout.simple_list_item_1,
                results);
        searchListView.setAdapter(searchAdapter);
    }

    // Set adapter for schedule listview
    private void setScheduleAdapter()
    {
        scheduleAdapter = new ArrayAdapter<>(
                SchedulePage.this,
                android.R.layout.simple_list_item_1,
                schedule.getCourseNames());
        scheduleListView.setAdapter(scheduleAdapter);
    }

}