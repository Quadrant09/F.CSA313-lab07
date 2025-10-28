package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.ArrayList;

public class MeetingTest {
    private Meeting meeting;
    private Person person1;
    private Person person2;
    private Room room;
    
    @Before
    public void setUp() {
        meeting = new Meeting();
        person1 = new Person("John Doe");
        person2 = new Person("Jane Doe");
        room = new Room("R101");
    }
    
    @Test
    public void testInvalidMonthNegative() {
        meeting = new Meeting(-1, 1, 9, 10);
        try {
            meeting.getMonth();
            fail("Should throw exception for negative month");
        } catch (IllegalStateException e) {
            assertTrue(true); // Expected exception
        }
    }
    
    @Test
    public void testInvalidMonthZero() {
        meeting = new Meeting(0, 1, 9, 10);
        try {
            meeting.getMonth();
            fail("Should throw exception for zero month");
        } catch (IllegalStateException e) {
            assertTrue(true); // Expected exception
        }
    }
    
    @Test
    public void testInvalidDayNegative() {
        meeting = new Meeting(1, -1, 9, 10);
        try {
            meeting.getDay();
            fail("Should throw exception for negative day");
        } catch (IllegalStateException e) {
            assertTrue(true); // Expected exception
        }
    }
    
    @Test
    public void testInvalidTimeRange() {
        meeting = new Meeting(1, 1, 23, 1);
        try {
            meeting.getStartTime();
            fail("Should throw exception for invalid time range");
        } catch (IllegalStateException e) {
            assertTrue(true); // Expected exception
        }
    }
    
    @Test
    public void testFebruary29NonLeapYear() {
        meeting = new Meeting(2, 29, 9, 10);
        try {
            meeting.getDay();
            fail("Should throw exception for February 29 in non-leap year");
        } catch (IllegalStateException e) {
            assertTrue(true); // Expected exception
        }
    }
    
    @Test
    public void testAddNullAttendee() {
        try {
            meeting.addAttendee(null);
            fail("Should throw exception for null attendee");
        } catch (IllegalArgumentException e) {
            assertTrue(true); // Expected exception
        }
    }
    
    @Test
    public void testRemoveNonExistentAttendee() {
        Person nonExistent = new Person("Non Existent");
        try {
            meeting.removeAttendee(nonExistent);
            fail("Should throw exception for non-existent attendee");
        } catch (IllegalStateException e) {
            assertTrue(true); // Expected exception
        }
    }
    
    @Test
    public void testConstructor_default() {
        assertNotNull("Meeting should be created", meeting);
    }
    
    @Test
    public void testConstructor_holiday() {
        Meeting holiday = new Meeting(12, 25, "Christmas");
        assertEquals("Month should be December", 12, holiday.getMonth());
        assertEquals("Day should be 25th", 25, holiday.getDay());
        assertEquals("Should be all-day meeting", 0, holiday.getStartTime());
        assertEquals("Should be all-day meeting", 23, holiday.getEndTime());
    }
    
    @Test
    public void testConstructor_withTime() {
        Meeting timeSlot = new Meeting(10, 1, 9, 10);
        assertEquals(9, timeSlot.getStartTime());
        assertEquals(10, timeSlot.getEndTime());
    }
    
    @Test
    public void testAddRemoveAttendee() {
        meeting.addAttendee(person1);
        meeting.addAttendee(person2);
        String description = meeting.toString();
        assertTrue(description.contains("John Doe"));
        assertTrue(description.contains("Jane Doe"));
        
        meeting.removeAttendee(person1);
        description = meeting.toString();
        assertFalse(description.contains("John Doe"));
        assertTrue(description.contains("Jane Doe"));
    }
    
    @Test
    public void testFullMeetingConstructor() {
        ArrayList<Person> attendees = new ArrayList<>();
        attendees.add(person1);
        attendees.add(person2);
        
        Meeting fullMeeting = new Meeting(11, 15, 14, 15, attendees, room, "Team Meeting");
        String description = fullMeeting.toString();
        assertTrue(description.contains("Team Meeting"));
        assertTrue(description.contains("R101"));
        assertTrue(description.contains("John Doe"));
        assertTrue(description.contains("Jane Doe"));
    }
}
