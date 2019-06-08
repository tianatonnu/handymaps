package com.tianatonnu.handymaps;

import org.junit.Test;
import static org.junit.Assert.*;

public class BuildingUnitTest {

    private Building testBuilding = new Building(new double[]{-120.653077, 35.297469},"Yosemite Hall Tower 8","114J");

    @Test
    public void buildingTest(){
        assertEquals(35.297469,testBuilding.getLatitude(),0.1);
        assertEquals(-120.653077,testBuilding.getLongitude(),0.1);
        assertEquals("Yosemite Hall Tower 8",testBuilding.getBuildingName());
        assertEquals("114J",testBuilding.getBuildingNumber());
    }
}