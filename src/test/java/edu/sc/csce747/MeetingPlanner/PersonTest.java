package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class PersonTest {
    private Person person;
    
    @Before
    public void setUp() {
        person = new Person("John Smith");
    }
    
    @Test(expected = TimeConflictException.class)
    public void testAddMeeting_InvalidMonth() throws TimeConflictException {
        Meeting meeting = new Meeting(13, 1, 9, 10);
        person.addMeeting(meeting);
    }
    
    @Test(expected = TimeConflictException.class)
    public void testAddMeeting_InvalidDay() throws TimeConflictException {
        Meeting meeting = new Meeting(4, 31, 9, 10);
        person.addMeeting(meeting);
    }
    
    @Test(expected = TimeConflictException.class)
    public void testAddMeeting_InvalidTimeStart() throws TimeConflictException {
        Meeting meeting = new Meeting(1, 1, -1, 10);
        person.addMeeting(meeting);
    }
    
    @Test(expected = TimeConflictException.class)
    public void testAddMeeting_InvalidTimeEnd() throws TimeConflictException {
        Meeting meeting = new Meeting(1, 1, 9, 24);
        person.addMeeting(meeting);
    }
    
    @Test
    public void testAddMeeting_Conflict() {
        try {
            Meeting meeting1 = new Meeting(1, 1, 9, 11);
            Meeting meeting2 = new Meeting(1, 1, 10, 12);
            person.addMeeting(meeting1);
            person.addMeeting(meeting2);
            fail("Should throw TimeConflictException for overlapping meetings");
        } catch (TimeConflictException e) {
            assertTrue("Exception message should mention conflict",
                      e.getMessage().contains("Conflict"));
        }
    }
    
    @Test
    public void testRemoveMeeting_InvalidIndex() {
        try {
            person.removeMeeting(1, 1, -1);
            fail("Should throw IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            assertTrue(true); // Expected exception
        }
    }
    
    @Test
    public void testGetMeeting_NonExistent() {
        Meeting meeting = person.getMeeting(1, 1, 0);
        assertNull("Should return null for non-existent meeting", meeting);
    }
    
    @Test
    public void testPrintAgenda_InvalidMonth() {
        String agenda = person.printAgenda(13);
        assertTrue("Should return empty agenda for invalid month", 
                  agenda.trim().isEmpty());
    }
    
    @Test
    public void testPrintAgenda_InvalidDay() {
        String agenda = person.printAgenda(2, 30);
        assertTrue("Should return empty agenda for invalid day", 
                  agenda.trim().isEmpty());
    }
    
    @Test
    public void testConstructor() {
        assertEquals("Name should be set", "John Smith", person.getName());
    }
    
    @Test
    public void testDefaultConstructor() {
        Person defaultPerson = new Person();
        assertEquals("Default name should be empty", "", defaultPerson.getName());
    }
    
    @Test
    public void testAddMeeting() {
        try {
            Meeting meeting = new Meeting(11, 1, 9, 10);
            meeting.setDescription("Team Meeting");
            person.addMeeting(meeting);
            assertTrue("Person should be busy during meeting time", 
                      person.isBusy(11, 1, 9, 10));
        } catch (TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testPrintAgenda() {
        try {
            Meeting meeting = new Meeting(11, 1, 9, 10);
            meeting.setDescription("Team Meeting");
            person.addMeeting(meeting);
            String agenda = person.printAgenda(11);
            assertTrue("Agenda should contain meeting description",
                      agenda.contains("Team Meeting"));
            
            agenda = person.printAgenda(11, 1);
            assertTrue("Daily agenda should contain meeting description",
                      agenda.contains("Team Meeting"));
        } catch (TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testRemoveMeeting() {
        try {
            Meeting meeting = new Meeting(11, 1, 9, 10);
            meeting.setDescription("Team Meeting");
            person.addMeeting(meeting);
            assertTrue(person.isBusy(11, 1, 9, 10));
            
            person.removeMeeting(11, 1, 0);
            assertFalse(person.isBusy(11, 1, 9, 10));
        } catch (TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
}
