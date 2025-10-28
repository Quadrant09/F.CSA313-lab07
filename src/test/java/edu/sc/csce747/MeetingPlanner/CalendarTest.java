package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class CalendarTest {
    private Calendar calendar;
    
    @Before
    public void setUp() {
        calendar = new Calendar();
    }
    
    @Test(expected = TimeConflictException.class)
    public void testAddMeeting_InvalidFebruaryDate() throws TimeConflictException {
        Meeting meeting = new Meeting(2, 35, 9, 10);
        calendar.addMeeting(meeting);
    }
    
    @Test(expected = TimeConflictException.class)
    public void testAddMeeting_InvalidMonthZero() throws TimeConflictException {
        Meeting meeting = new Meeting(0, 1, 9, 10);
        calendar.addMeeting(meeting);
    }
    
    @Test(expected = TimeConflictException.class)
    public void testAddMeeting_InvalidMonthThirteen() throws TimeConflictException {
        Meeting meeting = new Meeting(13, 1, 9, 10);
        calendar.addMeeting(meeting);
    }
    
    @Test(expected = TimeConflictException.class)
    public void testAddMeeting_InvalidDayZero() throws TimeConflictException {
        Meeting meeting = new Meeting(1, 0, 9, 10);
        calendar.addMeeting(meeting);
    }
    
    @Test(expected = TimeConflictException.class)
    public void testAddMeeting_InvalidTimeNegative() throws TimeConflictException {
        Meeting meeting = new Meeting(1, 1, -1, 10);
        calendar.addMeeting(meeting);
    }
    
    @Test(expected = TimeConflictException.class)
    public void testAddMeeting_InvalidTime24Hour() throws TimeConflictException {
        Meeting meeting = new Meeting(1, 1, 24, 25);
        calendar.addMeeting(meeting);
    }
    
    @Test(expected = TimeConflictException.class)
    public void testAddMeeting_EndBeforeStart() throws TimeConflictException {
        Meeting meeting = new Meeting(1, 1, 10, 9);
        calendar.addMeeting(meeting);
    }
    
    @Test(expected = TimeConflictException.class)
    public void testAddMeeting_April31() throws TimeConflictException {
        Meeting meeting = new Meeting(4, 31, 9, 10);
        calendar.addMeeting(meeting);
    }
    
    @Test
    public void testAddMeeting_holiday() {
        try {
            Meeting midsommar = new Meeting(6, 26, "Midsommar");
            calendar.addMeeting(midsommar);
            Boolean added = calendar.isBusy(6, 26, 0, 23);
            assertTrue("Midsommar should be marked as busy on the calendar", added);
        } catch(TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testAddMeeting_regularMeeting() {
        try {
            Meeting meeting = new Meeting(10, 15, 9, 10);
            calendar.addMeeting(meeting);
            assertTrue("Meeting should be marked as busy", calendar.isBusy(10, 15, 9, 10));
            assertFalse("Time outside meeting should not be busy", calendar.isBusy(10, 15, 11, 12));
        } catch(TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test(expected = TimeConflictException.class)
    public void testAddMeeting_invalidMonth() throws TimeConflictException {
        Meeting meeting = new Meeting(13, 1, 9, 10);
        calendar.addMeeting(meeting);
    }
    
    @Test(expected = TimeConflictException.class)
    public void testAddMeeting_invalidDay() throws TimeConflictException {
        Meeting meeting = new Meeting(1, 32, 9, 10);
        calendar.addMeeting(meeting);
    }
    
    @Test(expected = TimeConflictException.class)
    public void testAddMeeting_invalidTime() throws TimeConflictException {
        Meeting meeting = new Meeting(1, 1, 24, 25);
        calendar.addMeeting(meeting);
    }
    
    @Test
    public void testTimeConflict() {
        try {
            Meeting meeting1 = new Meeting(11, 1, 9, 11);
            calendar.addMeeting(meeting1);
            
            Meeting meeting2 = new Meeting(11, 1, 10, 12);
            calendar.addMeeting(meeting2);
            fail("Should throw TimeConflictException for overlapping meetings");
        } catch(TimeConflictException e) {
            // Expected exception
            assertTrue(e.getMessage().contains("conflict"));
        }
    }
    
    @Test
    public void testClearSchedule() {
        try {
            Meeting meeting = new Meeting(12, 25, "Christmas");
            calendar.addMeeting(meeting);
            assertTrue(calendar.isBusy(12, 25, 0, 23));
            
            calendar.clearSchedule(12, 25);
            assertFalse(calendar.isBusy(12, 25, 0, 23));
        } catch(TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
}