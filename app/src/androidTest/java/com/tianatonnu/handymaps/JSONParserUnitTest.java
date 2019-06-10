package com.tianatonnu.handymaps;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

//Integration Test
@RunWith(AndroidJUnit4.class)
public class JSONParserUnitTest {
    // Test on sub-file (each function)
    ClassLoader classLoader = getClass().getClassLoader();

    @Test
    public void getCoursesTest01(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        Course[] courses = JSONParser.getCourses("testSections.json");
        for(Course c: courses){
            System.out.println(c.createCard());
        }
    }

    @Test
    public void getClassroomsTest01(){

    }

    @Test
    public void getBuildingsTest01(){

    }
}