package com.tianatonnu.handymaps;

import org.junit.Test;
import static org.junit.Assert.*;

public class ScheduleUnitTest {

    private Schedule schedule = new Schedule();

    // - - - - - - - - - - - - - - - - - - - -
    //         - addCourse() tests -
    // - - - - - - - - - - - - - - - - - - - -
    // 1. Adds classes correctly
    @Test
    public void addNewClassesShouldAdd(){

    }

    // 2. Does not add duplicate classes
    @Test
    public void addingDuplicateClassShouldNotAdd() {

    }

    // - - - - - - - - - - - - - - - - - - - -
    //       - removeCourse() tests -
    // - - - - - - - - - - - - - - - - - - - -
    // 1. Does not remove from empty schedule
    @Test
    public void removeFromEmptyScheduleShouldNotRemove() {

    }

    // 2. Removes correctly when present
    @Test
    public void removePresentClassShouldRemove() {

    }

    // 3. Does not remove if not present
    @Test
    public void removeClassNotPresentShouldNotRemove() {

    }


    // - - - - - - - - - - - - - - - - - - - -
    //       - deleteSchedule() tests -
    // - - - - - - - - - - - - - - - - - - - -
    // 1. Check all courses removed from schedule
    @Test
    public void deleteScheduleShouldEmptySchedule() {

    }


    // - - - - - - - - - - - - - - - - - - - -
    //         - contains() tests -
    // - - - - - - - - - - - - - - - - - - - -
    // 1. Returns FALSE if schedule is empty
    @Test
    public void containsEmptyScheduleShouldReturnFalse() {

    }

    // 2. Returns TRUE if it contains class
    @Test
    public void containsPresentClassShouldReturnTrue() {

    }

    // 3. Returns FALSE if it does not contain class
    @Test
    public void containsClassNotPresentShouldReturnFalse() {

    }


    // - - - - - - - - - - - - - - - - - - - -
    //     - Schedule comparator tests -
    // - - - - - - - - - - - - - - - - - - - -


}
