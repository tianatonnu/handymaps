package com.tianatonnu.handymaps;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class ScheduleSearchViewController {
    private ScheduleActivity scheduleActivity;
    private ScheduleButtonsController buttonsController;

    private SearchView searchView;
    private boolean isFocused;
    private ListView searchListView;

    private ArrayAdapter<String> searchAdapter;

    public ScheduleSearchViewController(ScheduleActivity scheduleActivity, ListView searchListView, SearchView searchView,
                                        ScheduleButtonsController buttonsController, ArrayAdapter<String> searchAdapter)
    {
        this.scheduleActivity = scheduleActivity;
        this.searchListView = searchListView;
        this.searchView = searchView;
        isFocused = false;
        this.buttonsController = buttonsController;
        this.searchAdapter = searchAdapter;
    }

    public boolean getFocus()
    {
        return isFocused;
    }

    public void setFocus(boolean state)
    {
        isFocused = state;
    }

    public void removeFocus()
    {
        searchView.clearFocus();
        isFocused = false;
    }

    public void setSearchViewListeners(ArrayList<String> listViewData, String[] courseStrings, ScheduleListViewController listViewController)
    {
        setSearchViewCloseListener();
        setFocusListener(listViewController);
        setSearchViewQueryListener(listViewData, courseStrings);
    }

    private void setSearchViewCloseListener()
    {
        // Set close listener for search bar
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                closeSearchBar();
                isFocused = false;
                return true;
            }
        });
    }

    private void setFocusListener(ScheduleListViewController listViewController)
    {
        // Hide the buttons when the search bar opens
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    buttonsController.disableCourseButtons();
                    if (scheduleActivity.getPrevView() != null) {
                        // Set previously selected course back to original background
                        scheduleActivity.setPrevViewBackgroundToNormal();
                    }
                    isFocused = true;
                }
            }
        });
    }

    private void setSearchViewQueryListener(ArrayList<String> listViewData, String[] courseStrings)
    {
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
                buttonsController.hideAllButtons();

                if (newText != null && !newText.isEmpty())
                {
                    // Filter search
                    ArrayList<String> lstFound = Search.filter(newText, listViewData);

                    // Return the filtered results
                    scheduleActivity.setSearchAdapter(lstFound);
                }

                else
                {
                    // If no search, return all options
                    scheduleActivity.setSearchAdapter(courseStrings);
                }

                return false;
            }
        });
    }


    // Keep search bar expanded, but remove the focus
    private void exitSearchBar()
    {
        searchView.setIconified(false);
        searchView.setFocusable(false);
        searchView.clearFocus();
    }

    // Hide search suggestions
    public void closeSearchBar()
    {
        exitSearchBar();

        // Re-enable buttons
        buttonsController.enableButtonsConditionally();

        scheduleActivity.findViewById(R.id.schedule_search_lv).setVisibility(View.INVISIBLE);
    }

}
