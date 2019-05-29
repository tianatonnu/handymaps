package com.tianatonnu.handymaps;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class SchedulePage extends AppCompatActivity {

    private ArrayAdapter<String> searchAdapter;
    private ListView searchListView;
    private SearchView searchView;
    private ArrayAdapter<String> scheduleAdapter;
    private ListView scheduleListView;

    private Course[] courses;
    private String[] courseStrings;
    private ArrayList<String> listViewData = new ArrayList<>();

    /*private ArrayList<Course> schedule = new ArrayList<>();
    private ArrayList<String> scheduleStrings = new ArrayList<>();*/
    private Schedule schedule;
    private int prevCourseCard = -1;
    private String prevCourseName = null;

    private Button deleteBtn;
    private Button findBtn;
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

        // Parse courses from JSON data
        courses = JSONParser.getCourses();
        courseStrings = JSONParser.makeStrings(courses);
        Arrays.sort(courseStrings);
        Collections.addAll(listViewData, courseStrings);

        // Make sample schedule
        /*for (int i = 0; i < 6; i ++)
        {
            schedule.add(courses[i]);
            scheduleStrings.add(courses[i].createCard());
        }*/
        schedule = new Schedule();


        // Set the schedule list view to display the courses
        scheduleListView = findViewById(R.id.course_lv);
        scheduleListView.setVisibility(View.VISIBLE);
        scheduleAdapter = new ArrayAdapter<>(
                            SchedulePage.this,
                            android.R.layout.simple_list_item_1,
                            schedule.getCourseNames());
        scheduleListView.setAdapter(scheduleAdapter);

        // Set up search bar
        searchView = findViewById(R.id.schedule_search);
        searchView.setQueryHint(getResources().getString(R.string.schedule_hint));
        /*searchView.setIconified(false);
        searchView.setFocusable(false);
        searchView.clearFocus();*/
        exitSearchBar();
        searchListView = findViewById(R.id.schedule_search_lv);

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                /*searchView.setIconified(false);
                searchView.setFocusable(false);
                searchView.clearFocus();*/
                exitSearchBar();

                // Re-enable buttons
                if (showBtns)
                {
                    showButtons();
                }

                findViewById(R.id.schedule_search_lv).setVisibility(View.INVISIBLE);
                return true;
            }
        });

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

                //adapter.getFilter().filter(newText);
                if (newText != null && !newText.isEmpty())
                {
                    // Filter search
                    ArrayList<String> lstFound = Search.filter(newText, listViewData);

                    // Return the filtered results
                    searchAdapter = new ArrayAdapter<>(
                            SchedulePage.this,
                            android.R.layout.simple_list_item_1,
                            lstFound);

                    searchListView.setAdapter(searchAdapter);
                }

                else
                {
                    // If no search, return all options
                    searchAdapter = new ArrayAdapter<>(
                            SchedulePage.this,
                            android.R.layout.simple_list_item_1,
                            courseStrings);

                    searchListView.setAdapter(searchAdapter);
                }

                return false;
            }
        });

        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String courseName = searchAdapter.getItem(position);
                Log.d("Schedule", courseName);

                if (!schedule.contains(courseName))
                    schedule.addCourse(Search.findCourse(courses, courseName));

                // Update the schedule adapter to show the new course
                scheduleAdapter = new ArrayAdapter<>(
                                SchedulePage.this,
                                android.R.layout.simple_list_item_1,
                                schedule.getCourseNames());
                scheduleListView.setAdapter(scheduleAdapter);

                closeSearchBar();
            }
        });

        // Deals with selecting a course from the schedule listView
        scheduleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String card = scheduleAdapter.getItem(position);
                if (prevCourseCard != -1)
                {
                    // Set previously selected course back to original background
                    parent.getChildAt(prevCourseCard).setBackgroundColor(getResources().getColor(R.color.activityBackground));
                }

                parent.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.mapboxWhite));
                prevCourseCard = position;
                prevCourseName = scheduleAdapter.getItem(position);

                showButtons();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBtns = false;
                hideButtons();
                schedule.removeCourse(prevCourseName);

                scheduleAdapter = new ArrayAdapter<>(
                        SchedulePage.this,
                        android.R.layout.simple_list_item_1,
                        schedule.getCourseNames());
                scheduleListView.setAdapter(scheduleAdapter);
            }
        });
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Loads the items in the toolbar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.schedule_menu, menu);

        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //onBackPressed();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void exitSearchBar()
    {
        searchView.setIconified(false);
        searchView.setFocusable(false);
        searchView.clearFocus();
    }

    private void closeSearchBar()
    {
        /*searchView.setIconified(false);
        searchView.setFocusable(false);
        searchView.clearFocus();*/
        exitSearchBar();

        // Re-enable buttons
        if (showBtns)
            showButtons();

        findViewById(R.id.schedule_search_lv).setVisibility(View.INVISIBLE);
    }

    private void hideButtons()
    {
        deleteBtn.setEnabled(false);
        deleteBtn.setVisibility(View.INVISIBLE);
        /*findBtn.setEnabled(false);
        findBtn.setVisibility(View.INVISIBLE);*/
    }

    private void showButtons()
    {
        deleteBtn.setEnabled(true);
        deleteBtn.setVisibility(View.VISIBLE);
        /*findBtn.setEnabled(true);
        findBtn.setVisibility(View.VISIBLE);*/
        showBtns = true;
    }

}