package com.tianatonnu.handymaps;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    private Building testBuilding = new Building(new double[]{-120.653077, 35.297469},"Yosemite Hall Tower 8","114J");

    @Test
    public void buildingTest(){
        assertEquals(35.297469,testBuilding.getLatitude(),0.1);
        assertEquals(-120.653077,testBuilding.getLongitude(),0.1);
        assertEquals("Yosemite Hall Tower 8",testBuilding.getBuildingName());
        assertEquals("114J",testBuilding.getBuildingNumber());
    }
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}