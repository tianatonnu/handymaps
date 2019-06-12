package com.tianatonnu.handymaps;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;

public class MapButtonsController {
    private boolean centerOnCampus, route, clear;
    private Button clearBtn;
    private Button routeBtn;
    private FloatingActionButton centerBtn;
    private MainActivity mainActivity;
    private MapController mapController;

    public MapButtonsController(MainActivity mainActivity, MapController mapController)
    {
        this.mainActivity = mainActivity;
        centerOnCampus = false;
        clear = false;
        clear = false;
        centerBtn = mainActivity.findViewById(R.id.center_toggle);
        routeBtn = mainActivity.findViewById(R.id.routeButton);
        clearBtn = mainActivity.findViewById(R.id.clearButton);
        this.mapController = mapController;
    }

    public boolean getCenterOnCampus()
    {
        return centerOnCampus;
    }

    public Button getRouteBtn()
    {
        return routeBtn;
    }

    public void setRouteBtnToStart()
    {
        routeBtn.setText(mainActivity.getResources().getString(R.string.start));
        routeBtn.setBackgroundColor(mainActivity.getResources().getColor(R.color.startGreen));
    }

    public void changeCenterStatus()
    {
        if (centerOnCampus)
        {
            centerOnCampus = false;
            centerBtn.setImageResource(R.drawable.ic_location_disabled_24dp);
        }
        else
        {
            centerOnCampus = true;
            centerBtn.setImageResource(R.drawable.ic_my_location_24dp);
        }
    }

    public void disableLocationButtons()
    {
        routeBtn.setText(mainActivity.getResources().getString(R.string.find));
        routeBtn.setBackgroundColor(mainActivity.getResources().getColor(R.color.mapboxGreen));
        routeBtn.setEnabled(false);
        routeBtn.setVisibility(View.INVISIBLE);
        route = false;
        clearBtn.setEnabled(false);
        clearBtn.setVisibility(View.INVISIBLE);
        clear = false;
    }

    public void hideAllButtons()
    {
        // Hide all buttons
        routeBtn.setEnabled(false);
        routeBtn.setVisibility(View.INVISIBLE);
        clearBtn.setEnabled(false);
        clearBtn.setVisibility(View.INVISIBLE);
        centerBtn.setVisibility(View.INVISIBLE);
    }

    public void enableAllButtons()
    {
        // Enable the route and clear buttons
        routeBtn.setEnabled(true);
        routeBtn.setVisibility(View.VISIBLE);
        routeBtn.setBackgroundColor(mainActivity.getResources().getColor(R.color.mapboxGreen));
        routeBtn.setText(mainActivity.getResources().getString(R.string.find));
        route = true;
        clearBtn.setEnabled(true);
        clearBtn.setVisibility(View.VISIBLE);
        clear = true;
        centerBtn.setVisibility(View.VISIBLE);
    }

    public void enableAllButtonConditionally()
    {
        // Re-enable the buttons
        centerBtn.setVisibility(View.VISIBLE);
        if (clear)
        {
            clearBtn.setEnabled(true);
            clearBtn.setVisibility(View.VISIBLE);
        }
        if (route)
        {
            routeBtn.setEnabled(true);
            routeBtn.setVisibility(View.VISIBLE);
        }
    }

    public void setButtonListeners()
    {
        setRouteBtnListener();
        setClearBtnListener();
        setCenterBtnListener();
    }

    private void setRouteBtnListener()
    {
        routeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Need to calculate route
                if (routeBtn.getText().equals(mainActivity.getResources().getString(R.string.find)))
                {
                    // Find route
                    mapController.makeRoute();
                    setRouteBtnToStart();
                }
                // Need to start route
                else
                {
                    // Start navigation
                    mapController.startNavigation();
                }
            }
        });
    }

    private void setClearBtnListener()
    {
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the map icons
                mapController.clearMap();

                // Disable the route and clear buttons
                disableLocationButtons();
            }
        });
    }

    private void setCenterBtnListener()
    {
        centerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Switch to center on user's current location
                if (!centerOnCampus)
                {
                    changeCenterStatus();
                    mapController.centerOnUser();
                }
                // Switch to centered on middle of campus
                else
                {
                    changeCenterStatus();
                    mapController.centerOnCampus();
                }
            }
        });
    }

}
