package com.tianatonnu.handymaps;

import org.junit.Test;
import static org.junit.Assert.*;

public class BuildingUnitTest {

    private Building tBuild1 = new Building(new double[]{-120.653077, 35.297469},"Yosemite Hall Tower 8","1");
    private Building tBuild2 = new Building(new double[]{-120.653077, 35.297469},"Yosemite Hall Tower 8","1A");
    private Building tBuild3 = new Building(new double[]{-120.653077, 35.297469},"Yosemite Hall Tower 8","1B");
    private Building tBuild4 = new Building(new double[]{-120.653077, 35.297469},"Yosemite Hall Tower 8","2");
    private Building tBuild5 = new Building(new double[]{-120.653077, 35.297469},"Yosemite Hall Tower 8","2A");

    @Test
    public void buildingTest() {
        assertEquals(35.297469, tBuild1.getLatitude(), 0.1);
        assertEquals(-120.653077, tBuild1.getLongitude(), 0.1);
        assertEquals("Yosemite Hall Tower 8", tBuild1.getBuildingName());
        assertEquals("1", tBuild1.getBuildingNumber());
    }
    @Test
    public void compareBuildingTests(){
        assertEquals(0, tBuild1.compareTo(tBuild1));
        assertEquals(-1, tBuild1.compareTo(tBuild2));
        assertEquals(-1, tBuild1.compareTo(tBuild3));
        assertEquals(-1, tBuild1.compareTo(tBuild4));
        assertEquals(-1, tBuild1.compareTo(tBuild5));
        assertEquals(1, tBuild2.compareTo(tBuild1));
        assertEquals(0, tBuild2.compareTo(tBuild2));
        assertEquals(-1, tBuild2.compareTo(tBuild3));
        assertEquals(-1, tBuild2.compareTo(tBuild4));
        assertEquals(-1, tBuild2.compareTo(tBuild5));
        assertEquals(1, tBuild3.compareTo(tBuild1));
        assertEquals(1, tBuild3.compareTo(tBuild2));
        assertEquals(0, tBuild3.compareTo(tBuild3));
        assertEquals(-1, tBuild3.compareTo(tBuild4));
        assertEquals(-1, tBuild3.compareTo(tBuild5));
        assertEquals(1, tBuild4.compareTo(tBuild1));
        assertEquals(1, tBuild4.compareTo(tBuild2));
        assertEquals(1, tBuild4.compareTo(tBuild3));
        assertEquals(0, tBuild4.compareTo(tBuild4));
        assertEquals(-1, tBuild4.compareTo(tBuild5));
        assertEquals(1, tBuild5.compareTo(tBuild1));
        assertEquals(1, tBuild5.compareTo(tBuild2));
        assertEquals(1, tBuild5.compareTo(tBuild3));
        assertEquals(1, tBuild5.compareTo(tBuild4));
        assertEquals(0, tBuild5.compareTo(tBuild5));
    }
}
