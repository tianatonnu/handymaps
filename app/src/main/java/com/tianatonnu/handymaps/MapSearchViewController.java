package com.tianatonnu.handymaps;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.ArrayList;

public class MapSearchViewController {
    private MainActivity mainActivity;

    private MapController mapController;

    private SearchView searchView;
    private ListView listView;

    private ArrayList<String> allData;

    public MapSearchViewController(MainActivity mainActivity, SearchView searchView, ListView listView,
                                   MapController mapController, ArrayList<String> allData)
    {
        this.mainActivity = mainActivity;
        this.searchView = searchView;
        this.listView = listView;
        this.mapController = mapController;
        this.allData = allData;
    }

    public void setSearchViewListeners(MapboxMap mapboxMap, ArrayList<Location> locations, MapButtonsController buttonsController)
    {
        setSearchViewCloseListener(buttonsController);
        setQueryListener(buttonsController);
        setListItemListener(mapboxMap, locations, buttonsController);
    }

    private void setSearchViewCloseListener(MapButtonsController buttonsController)
    {
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                // Hide results
                mainActivity.findViewById(R.id.ListView).setVisibility(View.INVISIBLE);

                // Re-enable the buttons
                buttonsController.enableAllButtonConditionally();
                return false;
            }
        });
    }

    private void setQueryListener(MapButtonsController buttonsController)
    {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Display results
                mainActivity.findViewById(R.id.ListView).setVisibility(View.VISIBLE);

                // Hide all buttons
                buttonsController.hideAllButtons();

                if (newText != null && !newText.isEmpty())
                {
                    // Filter search
                    ArrayList<String> lstFound = Search.filter(newText, allData);

                    // Return the filtered results
                    mainActivity.setSearchAdapter(lstFound, listView);
                }

                else
                {
                    // If no search, return all options
                    mainActivity.setSearchAdapter(allData, listView);
                }

                return false;
            }
        });
    }

    private void setListItemListener(MapboxMap mapboxMap, ArrayList<Location> locations, MapButtonsController buttonsController)
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchView.clearFocus();
                listView.setVisibility(View.INVISIBLE);
                String card = mainActivity.getSearchItemString(position);
                Log.d("Search", card);

                Point destinationPoint = Search.findCoordinates(locations, card);

                Style style = mapboxMap.getStyle();
                if (style != null)
                {
                    mapController.findLocation(buttonsController, destinationPoint);
                }
            }
        });
    }
}
