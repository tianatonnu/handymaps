package com.tianatonnu.handymaps;

import org.junit.Test;
import static org.junit.Assert.*;

public class ClassroomUnitTest {

    @Test
    public void classroomTest01(){
        Classroom testClassroom = new Classroom("Math & Science","38","0218",new double[]{-120.66199690103531, 35.30118890411341});
        assertEquals("38-0218\nBuilding: Math & Science\nRoom: 0218",testClassroom.createCard());
        assertEquals("Math & Science",testClassroom.getBldgName());
        assertEquals(35.30118890411341,testClassroom.getLatitude(),0.1);
        assertEquals(-120.66199690103531,testClassroom.getLongitude(),0.1);
        assertEquals("0218",testClassroom.getRoomNumber());
    }

    @Test
    public void classroomTest02(){
        Classroom testClassroom = new Classroom("Math & Science","38","0221",new double[]{-120.66194862127304, 35.30112323350358});
        assertEquals("38-0221\nBuilding: Math & Science\nRoom: 0221",testClassroom.createCard());
        assertEquals("Math & Science",testClassroom.getBldgName());
        assertEquals(35.30112323350358,testClassroom.getLatitude(),0.1);
        assertEquals(-120.66194862127304,testClassroom.getLongitude(),0.1);
        assertEquals("0221",testClassroom.getRoomNumber());
    }

    @Test
    public void classroomTest03(){
        Classroom testClassroom = new Classroom("Const Innovations Center","186","C202",new double[]{-120.66233485937119, 35.2992297079978});
        assertEquals("186-C202\nBuilding: Const Innovations Center\nRoom: C202",testClassroom.createCard());
        assertEquals("Const Innovations Center",testClassroom.getBldgName());
        assertEquals(35.2992297079978,testClassroom.getLatitude(),0.1);
        assertEquals(-120.66233485937119,testClassroom.getLongitude(),0.1);
        assertEquals("C202",testClassroom.getRoomNumber());
    }

    @Test
    public void classroomTest04(){
        Classroom testClassroom = new Classroom("Davidson Music Center","45","0130",new double[]{-120.65813452005386, 35.29939388826418});
        assertEquals("45-0130\nBuilding: Davidson Music Center\nRoom: 0130",testClassroom.createCard());
        assertEquals("Davidson Music Center",testClassroom.getBldgName());
        assertEquals(35.29939388826418,testClassroom.getLatitude(),0.1);
        assertEquals(-120.65813452005386,testClassroom.getLongitude(),0.1);
        assertEquals("0130",testClassroom.getRoomNumber());
    }

    // Compare Different Building Numbers
    @Test
    public void classroomComparatorTest01(){
        Classroom testClassroom01 = new Classroom("Davidson Music Center","45","0130",new double[]{-120.65813452005386, 35.29939388826418});

        Classroom testClassroom02 = new Classroom("Math & Science","38","0218",new double[]{-120.66199690103531, 35.30118890411341});

        assertEquals(1,testClassroom01.compareTo(testClassroom02));
    }

    // Compare Different Building Numbers with Letters
    @Test
    public void classroomComparatorTest02(){
        Classroom testClassroom01 = new Classroom("Agricultural Engr Annex","08A","0002",new double[]{-120.65813452005386, 35.29939388826418});

        Classroom testClassroom02 = new Classroom("Agricultural Engineering","08","0122",new double[]{-120.66199690103531, 35.30118890411341});

        assertEquals(1,testClassroom01.compareTo(testClassroom02));
    }

    // Compare Different Building Numbers with Letters
    @Test
    public void classroomComparatorTest03(){
        Classroom testClassroom01 = new Classroom("Agricultural Engr Annex","08A","0002",new double[]{-120.65813452005386, 35.29939388826418});

        Classroom testClassroom02 = new Classroom("Alan A Erhart Agriculture","10","0227",new double[]{-120.66199690103531, 35.30118890411341});

        assertEquals(-1,testClassroom01.compareTo(testClassroom02));
    }

    // Compare Same Building Different Rooms
    @Test
    public void classroomComparatorTest04(){
        Classroom testClassroom01 = new Classroom("Math & Science","38","0204",new double[]{-120.66199690103531, 35.30118890411341});

        Classroom testClassroom02 = new Classroom("Math & Science","38","0218",new double[]{-120.66199690103531, 35.30118890411341});

        assertEquals(-1,testClassroom01.compareTo(testClassroom02));
    }

    // Compare Same Building Different Rooms
    @Test
    public void classroomComparatorTest05(){
        Classroom testClassroom01 = new Classroom("Math & Science","38","0204",new double[]{-120.66199690103531, 35.30118890411341});

        Classroom testClassroom02 = new Classroom("Math & Science","38","0148",new double[]{-120.66199690103531, 35.30118890411341});

        assertEquals(1,testClassroom01.compareTo(testClassroom02));
    }

    // Compare Same Building Different Rooms with Letters
    @Test
    public void classroomComparatorTest06(){
        Classroom testClassroom01 = new Classroom("Agricultural Engr Annex","08A","0002",new double[]{-120.65813452005386, 35.29939388826418});

        Classroom testClassroom02 = new Classroom("Agricultural Engr Annex","08A","0003E",new double[]{-120.66199690103531, 35.30118890411341});

        assertEquals(-1,testClassroom01.compareTo(testClassroom02));
    }

    // Compare Same Building Different Rooms with Letters
    @Test
    public void classroomComparatorTest07(){
        Classroom testClassroom01 = new Classroom("Engineering West","21","0122A",new double[]{-120.65813452005386, 35.29939388826418});

        Classroom testClassroom02 = new Classroom("Engineering West","21","0235",new double[]{-120.66199690103531, 35.30118890411341});

        assertEquals(-1,testClassroom01.compareTo(testClassroom02));
    }

    // Compare Same Building Different Rooms with Letters
    @Test
    public void classroomComparatorTest08(){
        Classroom testClassroom01 = new Classroom("Engineering West","21","0122A",new double[]{-120.65813452005386, 35.29939388826418});

        Classroom testClassroom02 = new Classroom("Engineering West","21","0122D",new double[]{-120.66199690103531, 35.30118890411341});

        assertEquals(-1,testClassroom01.compareTo(testClassroom02));
    }

    // Compare Same Building & Same Rooms
    @Test
    public void classroomComparatorTest09(){
        Classroom testClassroom01 = new Classroom("Math & Science","38","0204",new double[]{-120.66199690103531, 35.30118890411341});

        Classroom testClassroom02 = new Classroom("Math & Science","38","0204",new double[]{-120.66199690103531, 35.30118890411341});

        assertEquals(0,testClassroom01.compareTo(testClassroom02));
    }

    @Test
    public void classroomComparatorTest10(){
        Classroom testClassroom01 = new Classroom("Math & Science","100","0204",new double[]{-120.66199690103531, 35.30118890411341});

        Classroom testClassroom02 = new Classroom("Math & Science","11","0204",new double[]{-120.66199690103531, 35.30118890411341});

        assertEquals(1,testClassroom01.compareTo(testClassroom02));

    }

    @Test
    public void classroomComparatorTest11(){
        Classroom testClassroom01 = new Classroom("Math & Science","186","B103",new double[]{-120.66199690103531, 35.30118890411341});

        Classroom testClassroom02 = new Classroom("Math & Science","186","C301",new double[]{-120.66199690103531, 35.30118890411341});

        assertEquals(-1,testClassroom01.compareTo(testClassroom02));

    }

    @Test
    public void classroomComparatorTest12(){
        Classroom testClassroom01 = new Classroom("Math & Science","100","0115",new double[]{-120.66199690103531, 35.30118890411341});

        Classroom testClassroom02 = new Classroom("Math & Science","26M","0204",new double[]{-120.66199690103531, 35.30118890411341});

        assertEquals(1,testClassroom01.compareTo(testClassroom02));

    }

    @Test
    public void classroomComparatorTest13(){
        Classroom testClassroom01 = new Classroom("Math & Science","38","0204",new double[]{-120.66199690103531, 35.30118890411341});

        Classroom testClassroom02 = new Classroom("Math & Science","38A","0204",new double[]{-120.66199690103531, 35.30118890411341});

        assertEquals(-1,testClassroom01.compareTo(testClassroom02));
    }
}
