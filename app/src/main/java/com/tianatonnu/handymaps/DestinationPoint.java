package com.tianatonnu.handymaps;

import java.io.Serializable;

public class DestinationPoint implements Serializable
{
    private double latitude;
    private double longitude;

    public DestinationPoint(double latitude, double longitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

}
