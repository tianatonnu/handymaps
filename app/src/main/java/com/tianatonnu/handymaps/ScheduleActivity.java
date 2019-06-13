package com.tianatonnu.handymaps;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import android.widget.Toast;

public class ScheduleActivity extends AppCompatActivity {

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
    private View prevView = null;
    private String prevCourseName = null;

    private ScheduleButtonsController buttonsController;
    private ScheduleSearchViewController searchViewController;
    private ScheduleListViewController listViewController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_page);

        // Set up tool bar as the action bar menu
        Toolbar toolBar = findViewById(R.id.schedule_toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Adds back button to get back to map
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Parse courses from JSON data
        courses = JSONParser.getCourses("sections.json");
        Arrays.sort(courses);
        courseStrings = JSONParser.makeStrings(courses);
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

        buttonsController = new ScheduleButtonsController(ScheduleActivity.this, schedule);

        searchViewController = new ScheduleSearchViewController(ScheduleActivity.this, searchListView,
                                                                searchView, buttonsController, searchAdapter);

        listViewController = new ScheduleListViewController(ScheduleActivity.this, buttonsController, schedule, searchListView,
                                                            searchViewController, scheduleListView);

        // Set the observers for the search bar
        searchViewController.setSearchViewListeners(listViewData, courseStrings, listViewController);

        // Set on click listeners for the search bar and schedule
        listViewController.setListViewListeners(courses);

        // Set on click listeners for all the buttons
        buttonsController.setButtonListeners(locations);
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
        int typeDeleteSchedule = 0;

        switch (item.getItemId()) {
            case android.R.id.home:
                // Return to map
                onBackPressed();
                return true;
            case R.id.delete:
                // Delete entire schedule dialog
                buttonsController.confirmDeleteDialog(typeDeleteSchedule);
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

    public String getSearchItemString(int position)
    {
        return searchAdapter.getItem(position);
    }

    public String getScheduleItemString(int position)
    {
        return scheduleAdapter.getItem(position);
    }

    public View getPrevView()
    {
        return prevView;
    }

    public String getPrevCourseName()
    {
        return prevCourseName;
    }

    public void setPrevViewBackgroundToNormal()
    {
        prevView.setBackgroundColor(getResources().getColor(R.color.activityBackground));
    }

    public void resetSelectedCourse()
    {
        prevCourseName = null;
        prevView = null;
    }

    public void setSelectedCourse(View newView, String newCourse)
    {
        prevCourseName = newCourse;
        prevView = newView;
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
        buttonsController.enableButtonsConditionally();

        findViewById(R.id.schedule_search_lv).setVisibility(View.INVISIBLE);
    }

    // Save the changes made to the schedule to the device
    public void saveSchedule() {
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
    public void setSearchAdapter(String[] results)
    {
        searchAdapter = new ArrayAdapter<>(
                ScheduleActivity.this,
                android.R.layout.simple_list_item_1,
                results);
        searchListView.setAdapter(searchAdapter);
    }

    // Set adapter for search bar listview
    public void setSearchAdapter(ArrayList<String> results)
    {
        searchAdapter = new ArrayAdapter<>(
                ScheduleActivity.this,
                android.R.layout.simple_list_item_1,
                results);
        searchListView.setAdapter(searchAdapter);
    }

    // Set adapter for schedule listview
    public void setScheduleAdapter()
    {
        scheduleAdapter = new ArrayAdapter<>(
                ScheduleActivity.this,
                android.R.layout.simple_list_item_1,
                schedule.getCourseNames());
        scheduleListView.setAdapter(scheduleAdapter);
    }
}