package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class RoomTest {
    private Room room;
    private Meeting meeting;
    
    @Before
    public void setUp() {
        room = new Room("2A01");
        meeting = new Meeting(11, 1, 9, 10);
    }
    
    @Test(expected = TimeConflictException.class)
    public void testAddMeeting_InvalidFebruaryDate() throws TimeConflictException {
        Meeting invalidMeeting = new Meeting(2, 30, 9, 10);
        room.addMeeting(invalidMeeting);
    }
    
    @Test(expected = TimeConflictException.class)
    public void testAddMeeting_April31() throws TimeConflictException {
        Meeting invalidMeeting = new Meeting(4, 31, 9, 10);
        room.addMeeting(invalidMeeting);
    }
    
    @Test(expected = TimeConflictException.class)
    public void testAddMeeting_InvalidTimeRange() throws TimeConflictException {
        Meeting invalidMeeting = new Meeting(1, 1, 23, 1);
        room.addMeeting(invalidMeeting);
    }
    
    @Test
    public void testAddMeeting_NullMeeting() {
        try {
            room.addMeeting(null);
            fail("Should throw exception for null meeting");
        } catch (IllegalArgumentException | TimeConflictException e) {
            assertTrue(true); // Expected exception
        }
    }
    
    @Test
    public void testRemoveMeeting_InvalidIndex() {
        try {
            room.removeMeeting(1, 1, -1);
            fail("Should throw exception for negative index");
        } catch (IndexOutOfBoundsException e) {
            assertTrue(true); // Expected exception
        }
    }
    
    @Test
    public void testIsBusy_InvalidTimeRange() {
        try {
            room.isBusy(1, 1, 23, 1);
            fail("Should throw TimeConflictException for invalid time range");
        } catch (TimeConflictException e) {
            assertTrue(e.getMessage().contains("invalid"));
        }
    }
    
    @Test
    public void testGetMeeting_NonExistent() {
        Meeting result = room.getMeeting(1, 1, 0);
        assertNull("Should return null for non-existent meeting", result);
    }
    
    @Test
    public void testPrintAgenda_InvalidMonth() {
        String agenda = room.printAgenda(13);
        assertTrue("Should return empty agenda for invalid month", 
                  agenda.trim().isEmpty());
    }
    
    @Test
    public void testPrintAgenda_InvalidDay() {
        String agenda = room.printAgenda(2, 30);
        assertTrue("Should return empty agenda for invalid day", 
                  agenda.trim().isEmpty());
    }
    
    @Test
    public void testConstructor() {
        assertEquals("Room ID should be set", "2A01", room.getID());
    }
    
    @Test
    public void testDefaultConstructor() {
        Room defaultRoom = new Room();
        assertEquals("Default room ID should be empty", "", defaultRoom.getID());
    }
    
    @Test
    public void testAddMeeting() {
        try {
            room.addMeeting(meeting);
            assertTrue("Room should be busy during meeting time", 
                      room.isBusy(11, 1, 9, 10));
            assertFalse("Room should not be busy outside meeting time",
                       room.isBusy(11, 1, 11, 12));
        } catch (TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test(expected = TimeConflictException.class)
    public void testAddMeeting_conflict() throws TimeConflictException {
        room.addMeeting(meeting);
        Meeting conflictingMeeting = new Meeting(11, 1, 9, 11);
        room.addMeeting(conflictingMeeting);
    }
    
    @Test
    public void testPrintAgenda() {
        try {
            meeting.setDescription("Team Meeting");
            room.addMeeting(meeting);
            
            String monthlyAgenda = room.printAgenda(11);
            assertTrue("Monthly agenda should contain meeting description",
                      monthlyAgenda.contains("Team Meeting"));
            
            String dailyAgenda = room.printAgenda(11, 1);
            assertTrue("Daily agenda should contain meeting description",
                      dailyAgenda.contains("Team Meeting"));
        } catch (TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testRemoveMeeting() {
        try {
            room.addMeeting(meeting);
            assertTrue("Room should be busy during meeting time",
                      room.isBusy(11, 1, 9, 10));
            
            room.removeMeeting(11, 1, 0);
            assertFalse("Room should not be busy after meeting removal",
                       room.isBusy(11, 1, 9, 10));
        } catch (TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
}
